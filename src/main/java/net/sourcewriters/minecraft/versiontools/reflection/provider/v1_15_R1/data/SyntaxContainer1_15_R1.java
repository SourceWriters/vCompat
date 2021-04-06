package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_15_R1.data;

import java.util.Collections;
import java.util.Set;

import org.bukkit.craftbukkit.v1_15_R1.persistence.CraftPersistentDataContainer;
import org.bukkit.persistence.PersistentDataContainer;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.utils.key.IKey;
import com.syntaxphoenix.syntaxapi.utils.key.NamespacedKey;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedKey;
import net.sourcewriters.minecraft.versiontools.reflection.data.wrap.SyntaxKey;

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

    /*
    * 
    */

    @Override
    public boolean has(IKey key) {
        throw new UnsupportedOperationException("Can't be used with PersistentDataContainer of Bukkit");
    }

    @Override
    public boolean has(String key, DataType<?, ?> type) {
        return has(NamespacedKey.fromString(key), type);
    }

    @Override
    public boolean has(IKey key, DataType<?, ?> type) {
        return has(new SyntaxKey(key), WrappedType1_15_R1.wrap(type));
    }

    @Override
    public <C> C get(String key, DataType<?, C> type) {
        return get(NamespacedKey.fromString(key), type);
    }

    @Override
    public <C> C get(IKey key, DataType<?, C> type) {
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
    public <E, V> void set(String key, E value, DataType<V, E> type) {
        set(wrappedKey(key), value, WrappedType1_15_R1.wrap(type));
    }

    @Override
    public <E, V> void set(IKey key, E value, DataType<V, E> type) {
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
        return getKeyspaces().stream().map(string -> NamespacedKey.fromString(string)).toArray(IKey[]::new);
    }

    @Override
    public Set<String> getKeyspaces() {
        if (container instanceof CraftPersistentDataContainer) {
            return ((CraftPersistentDataContainer) container).getRaw().keySet();
        }
        return Collections.emptySet();
    }

    @Override
    public DataAdapterContext getAdapterContext() {
        return getContext();
    }

    /*
    * 
    */

    @Override
    public SyntaxContext1_15_R1 getContext() {
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
