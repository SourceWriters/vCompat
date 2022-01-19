package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.data.hook;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_16_R3.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.persistence.PersistentDataContainer;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.data.nbt.NbtContainer;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtCompound;

import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.data.BukkitContainer1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.data.SyntaxContainer1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContainer;

@SuppressWarnings({
    "rawtypes",
    "unchecked"
})
public final class BukkitContainerAdapterHook1_16_R3 {

    private static final BukkitContainerAdapterHook1_16_R3 HOOK = new BukkitContainerAdapterHook1_16_R3();

    private final ClassLookup registryRef = ClassLookup.of(CraftPersistentDataTypeRegistry.class)
        .searchMethod("create", "createAdapter", Class.class, Class.class, Function.class, Function.class)
        .searchField("adapters", "adapters")
        .searchField("function", "CREATE_ADAPTER");
    private final ClassLookup entityRef = ClassLookup.of(CraftEntity.class).searchField("registry", "DATA_TYPE_REGISTRY");

    private BukkitContainerAdapterHook1_16_R3() {}

    private final HashMap<CraftPersistentDataTypeRegistry, Function> map = new HashMap<>();

    private CraftPersistentDataTypeRegistry getEntityRegistry() {
        return (CraftPersistentDataTypeRegistry) entityRef.getFieldValue("registry");
    }

    private void uninjectAll() {
        for (CraftPersistentDataTypeRegistry registry : map.keySet()) {
            Map adapters = (Map) registryRef.getFieldValue(registry, "adapters");
            adapters.remove(BukkitContainer1_16_R3.class);
            adapters.remove(SyntaxContainer1_16_R3.class);
            registryRef.setFieldValue(registry, "function", map.get(registry));
        }
        map.clear();
    }

    private void inject(CraftPersistentDataTypeRegistry registry) {
        if (map.containsKey(registry)) {
            return;
        }
        map.put(registry, (Function) registryRef.getFieldValue(registry, "function"));
        Function function = clazz -> createAdapter(registry, registryRef.getMethod("create").type().returnType(), (Class) clazz);
        registryRef.setFieldValue(registry, "function", function);
    }

    private <E> E createAdapter(CraftPersistentDataTypeRegistry registry, Class<E> adapterType, Class type) {
        if (Objects.equals(BukkitContainer1_16_R3.class, type)) {
            return (E) buildAdapter(registry, BukkitContainer1_16_R3.class, tag -> fromPrimitiveSyntax(tag));
        }
        if (Objects.equals(SyntaxContainer1_16_R3.class, type)) {
            return (E) buildAdapter(registry, SyntaxContainer1_16_R3.class, tag -> fromPrimitiveBukkit(registry, tag));
        }
        return (E) map.get(registry).apply(type);
    }

    private <C extends WrappedContainer> Object buildAdapter(Object handle, Class<C> type, Function<NBTTagCompound, C> function) {
        return registryRef.run(handle, "create", type, NBTTagCompound.class, (Function<C, NBTTagCompound>) input -> toPrimitive(input),
            function);
    }

    private NBTTagCompound toPrimitive(WrappedContainer input) {
        Object handle = findFinalContainer(input).getHandle();
        if (handle instanceof PersistentDataContainer) {
            if (handle instanceof CraftPersistentDataContainer) {
                return ((CraftPersistentDataContainer) handle).toTagCompound();
            }
            throw new IllegalArgumentException(
                "Expected 'CraftPersistentDataContainer' got '" + handle.getClass().getSimpleName() + " instead'!");
        }
        if (handle instanceof IDataContainer) {
            if (handle instanceof NbtContainer) {
                return (NBTTagCompound) VersionCompatProvider.get().getControl().getBukkitConversion().toMinecraftCompound(((NbtContainer) handle).asNbt());
            }
            throw new IllegalArgumentException(
                "Expected 'CraftPersistentDataContainer' got '" + handle.getClass().getSimpleName() + " instead'!");
        }
        throw new IllegalArgumentException("Unknown WrappedContainer implementation!");
    }

    private BukkitContainer1_16_R3 fromPrimitiveSyntax(NBTTagCompound data) {
        VersionControl control = VersionCompatProvider.get().getControl();
        NbtContainer container = new NbtContainer(control.getDataProvider().getRegistry());
        NbtCompound compound = control.getBukkitConversion().fromMinecraftCompound(data);
        container.fromNbt(compound);
        return new BukkitContainer1_16_R3(container);
    }

    private SyntaxContainer1_16_R3 fromPrimitiveBukkit(CraftPersistentDataTypeRegistry registry, NBTTagCompound data) {
        CraftPersistentDataContainer container = new CraftPersistentDataContainer(registry);
        container.putAll(data);
        return new SyntaxContainer1_16_R3(container);
    }

    private WrappedContainer findFinalContainer(WrappedContainer container) {
        WrappedContainer output = container;
        while (output.getHandle() instanceof WrappedContainer) {
            output = (WrappedContainer) output.getHandle();
        }
        return output;
    }

    public static void unhookAll() {
        HOOK.uninjectAll();
    }

    public static void hookEntity() {
        HOOK.inject(HOOK.getEntityRegistry());
    }

    public static void hook(CraftPersistentDataTypeRegistry registry) {
        HOOK.inject(registry);
    }

}
