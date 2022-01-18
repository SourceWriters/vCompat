package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1.data.hook;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_17_R1.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_17_R1.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.persistence.PersistentDataContainer;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtContainer;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.minecraft.nbt.NBTTagCompound;
import net.sourcewriters.minecraft.vcompat.reflection.VersionControl;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1.data.BukkitContainer1_17_R1;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1.data.SyntaxContainer1_17_R1;

@SuppressWarnings({
    "rawtypes",
    "unchecked"
})
public final class BukkitContainerAdapterHook1_17_R1 {

    private static final BukkitContainerAdapterHook1_17_R1 HOOK = new BukkitContainerAdapterHook1_17_R1();

    private final AbstractReflect registryRef = new Reflect(CraftPersistentDataTypeRegistry.class)
        .searchMethod("create", "createAdapter", Class.class, Class.class, Function.class, Function.class)
        .searchField("adapters", "adapters")
        .searchField("function", "CREATE_ADAPTER");
    private final AbstractReflect entityRef = new Reflect(CraftEntity.class).searchField("registry", "DATA_TYPE_REGISTRY");

    private BukkitContainerAdapterHook1_17_R1() {}

    private final HashMap<CraftPersistentDataTypeRegistry, Function> map = new HashMap<>();

    private CraftPersistentDataTypeRegistry getEntityRegistry() {
        return (CraftPersistentDataTypeRegistry) entityRef.getFieldValue("registry");
    }

    private void uninjectAll() {
        for (CraftPersistentDataTypeRegistry registry : map.keySet()) {
            Map adapters = (Map) registryRef.getFieldValue("adapters", registry);
            adapters.remove(BukkitContainer1_17_R1.class);
            adapters.remove(SyntaxContainer1_17_R1.class);
            registryRef.setFieldValue(registry, "function", map.get(registry));
        }
        map.clear();
    }

    private void inject(CraftPersistentDataTypeRegistry registry) {
        if (map.containsKey(registry)) {
            return;
        }
        map.put(registry, (Function) registryRef.getFieldValue("function", registry));
        Function function = clazz -> createAdapter(registry, registryRef.getMethod("create").getReturnType(), (Class) clazz);
        registryRef.setFieldValue(registry, "function", function);
    }

    private <E> E createAdapter(CraftPersistentDataTypeRegistry registry, Class<E> adapterType, Class type) {
        if (Objects.equals(BukkitContainer1_17_R1.class, type)) {
            return (E) buildAdapter(registry, BukkitContainer1_17_R1.class, tag -> fromPrimitiveSyntax(tag));
        }
        if (Objects.equals(SyntaxContainer1_17_R1.class, type)) {
            return (E) buildAdapter(registry, SyntaxContainer1_17_R1.class, tag -> fromPrimitiveBukkit(registry, tag));
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
                return (NBTTagCompound) VersionControl.get().getBukkitConversion().toMinecraftCompound(((NbtContainer) handle).asNbt());
            }
            throw new IllegalArgumentException(
                "Expected 'CraftPersistentDataContainer' got '" + handle.getClass().getSimpleName() + " instead'!");
        }
        throw new IllegalArgumentException("Unknown WrappedContainer implementation!");
    }

    private BukkitContainer1_17_R1 fromPrimitiveSyntax(NBTTagCompound data) {
        VersionControl control = VersionControl.get();
        NbtContainer container = new NbtContainer(control.getDataProvider().getRegistry());
        NbtCompound compound = control.getBukkitConversion().fromMinecraftCompound(data);
        container.fromNbt(compound);
        return new BukkitContainer1_17_R1(container);
    }

    private SyntaxContainer1_17_R1 fromPrimitiveBukkit(CraftPersistentDataTypeRegistry registry, NBTTagCompound data) {
        CraftPersistentDataContainer container = new CraftPersistentDataContainer(registry);
        container.putAll(data);
        return new SyntaxContainer1_17_R1(container);
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
