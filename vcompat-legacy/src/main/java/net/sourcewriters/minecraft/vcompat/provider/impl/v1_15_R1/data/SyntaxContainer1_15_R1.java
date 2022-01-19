package net.sourcewriters.minecraft.vcompat.provider.impl.v1_15_R1.data;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.persistence.PersistentDataContainer;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterRegistry;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.utils.key.IKey;

import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedKey;
import net.sourcewriters.minecraft.vcompat.provider.data.wrap.SyntaxKey;

public final class SyntaxContainer1_15_R1 extends WrappedContainer implements IDataContainer {

    private final PersistentDataContainer container;

    public SyntaxContainer1_15_R1(PersistentDataContainer container) {
        this.container = container;
    }

    @Override
    public PersistentDataContainer getHandle() {
        return container;
    }

    @Override
    public IDataContainer getAsSyntaxContainer() {
        return new SyntaxContainer1_15_R1(container);
    }
    
    @Override
    public IDataAdapterRegistry<?> getRegistry() {
        return VersionCompatProvider.get().getControl().getDataProvider().getRegistry();
    }

    /*
    * 
    */

    @Override
    public boolean has(IKey key) {
        throw new UnsupportedOperationException("Can't be used with PersistentDataContainer of Bukkit");
    }

    @Override
    public boolean has(String key, IDataType<?, ?> type) {
        return has(syntaxKey(key), type);
    }

    @Override
    public boolean has(IKey key, IDataType<?, ?> type) {
        return has(new SyntaxKey(key), WrappedType1_15_R1.wrap(type));
    }

    @Override
    public <C> C get(String key, IDataType<?, C> type) {
        return get(syntaxKey(key), type);
    }

    @Override
    public <C> C get(IKey key, IDataType<?, C> type) {
        return get(new SyntaxKey(key), WrappedType1_15_R1.wrap(type));
    }

    @Override
    public Object get(String key) {
        throw new UnsupportedOperationException("Can't be used with PersistentDataContainer of Bukkit");
    }

    @Override
    public Object get(IKey key) {
        throw new UnsupportedOperationException("Can't be used with PersistentDataContainer of Bukkit");
    }

    @Override
    public <V, E> void set(String key, E value, IDataType<V, E> type) {
        set(wrappedKey(key), value, WrappedType1_15_R1.wrap(type));
    }

    @Override
    public <V, E> void set(IKey key, E value, IDataType<V, E> type) {
        set(new SyntaxKey(key), value, WrappedType1_15_R1.wrap(type));
    }

    @Override
    public boolean remove(String key) {
        return remove(wrappedKey(key));
    }

    @Override
    public boolean remove(IKey key) {
        return remove(new SyntaxKey(key));
    }

    @Override
    public IKey[] getKeys() {
        return container.getKeys().stream().map(BukkitKey1_15_R1::new).map(WrappedKey::getNamespacedKey).toArray(IKey[]::new);
    }

    @Override
    public Set<String> getKeyspaces() {
        return container.getKeys().stream().map(org.bukkit.NamespacedKey::toString).collect(Collectors.toSet());
    }

    @Override
    public IDataAdapterContext getContext() {
        return getWrapContext();
    }

    /*
    * 
    */

    @Override
    public SyntaxContext1_15_R1 getWrapContext() {
        return new SyntaxContext1_15_R1(container.getAdapterContext());
    }

    @Override
    public boolean has(String key) {
        throw new UnsupportedOperationException("Can't be used with PersistentDataContainer of Bukkit");
    }

    @Override
    public boolean has(WrappedKey<?> key) {
        throw new UnsupportedOperationException("Can't be used with PersistentDataContainer of Bukkit");
    }

    @Override
    public <P, C> boolean has(String key, WrapType<P, C> type) {
        return has(wrappedKey(key), type);
    }

    @Override
    public <P, C> boolean has(WrappedKey<?> key, WrapType<P, C> type) {
        return container.has(BukkitKey1_15_R1.asBukkit(key), new SimpleBukkitType1_15_R1<>(type));
    }

    @Override
    public Object get(WrappedKey<?> key) {
        throw new UnsupportedOperationException("Can't be used with PersistentDataContainer of Bukkit");
    }

    @Override
    public <P, C> C get(String key, WrapType<P, C> type) {
        return get(wrappedKey(key), type);
    }

    @Override
    public <P, C> C get(WrappedKey<?> key, WrapType<P, C> type) {
        return container.get(BukkitKey1_15_R1.asBukkit(key), new SimpleBukkitType1_15_R1<>(type));
    }

    @Override
    public <B> void set(String key, B value, WrapType<?, B> type) {
        set(wrappedKey(key), value, type);
    }

    @Override
    public <B> void set(WrappedKey<?> key, B value, WrapType<?, B> type) {
        container.set(BukkitKey1_15_R1.asBukkit(key), new SimpleBukkitType1_15_R1<>(type), value);
    }

    @Override
    public boolean remove(WrappedKey<?> key) {
        container.remove(BukkitKey1_15_R1.asBukkit(key));
        return true; // Will always return true as we don't know if it contained it
    }

    @Override
    public Set<String> keySet() {
        return getKeyspaces();
    }

    @Override
    public boolean isEmpty() {
        return container.isEmpty();
    }

    @Override
    public int size() {
        return 0;
    }

}