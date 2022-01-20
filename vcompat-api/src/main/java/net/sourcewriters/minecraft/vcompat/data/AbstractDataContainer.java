package net.sourcewriters.minecraft.vcompat.data;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;
import com.syntaxphoenix.syntaxapi.utils.key.NamespacedKey;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterRegistry;
import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.data.util.NumberConversion;

public abstract class AbstractDataContainer<B> implements IDataContainer {

    protected final IDataAdapterRegistry<B> registry;

    public AbstractDataContainer(IDataAdapterRegistry<B> registry) {
        this.registry = registry;
    }

    @Override
    public IDataAdapterRegistry<B> getRegistry() {
        return registry;
    }

    @Override
    public Object get(String key) {
        B raw = getRaw(key);
        if (raw == null) {
            return raw;
        }
        return registry.extract(raw);
    }

    @Override
    public <E> E get(String key, IDataType<?, E> type) {
        Object value = registry.getBase().isAssignableFrom(type.getPrimitive()) ? getRaw(key) : get(key);
        if (value == null || !type.isPrimitive(value)) {
            if (Number.class.isAssignableFrom(type.getComplex())) {
                return NumberConversion.convert(0, type.getComplex());
            }
            return null;
        }
        E output = type.fromPrimitiveObj(getContext(), value);
        if (output == null && Number.class.isAssignableFrom(type.getComplex())) {
            return NumberConversion.convert(0, type.getComplex());
        }
        return output;
    }

    @Override
    public boolean has(String key, IDataType<?, ?> type) {
        if (!has(key)) {
            return false;
        }
        Object value = registry.getBase().isAssignableFrom(type.getPrimitive()) ? getRaw(key) : get(key);
        return value != null && type.isPrimitive(value);
    }

    @Override
    public <V, E> void set(String key, E value, IDataType<V, E> type) {
        set(key, registry.wrap(type.toPrimitive(getContext(), value)));
    }

    /*
     * Key conversion
     */

    @Override
    public Object get(IKey key) {
        return get(key.asString());
    }

    @Override
    public <E> E get(IKey key, IDataType<?, E> type) {
        return get(key.asString(), type);
    }

    @Override
    public <V, E> void set(IKey key, E value, IDataType<V, E> type) {
        set(key.asString(), value, type);
    }

    @Override
    public boolean has(IKey key) {
        return has(key.asString());
    }

    @Override
    public boolean has(IKey key, IDataType<?, ?> type) {
        return has(key.asString(), type);
    }

    @Override
    public boolean remove(IKey key) {
        return remove(key.asString());
    }

    @Override
    public IKey[] getKeys() {
        return getKeyspaces().stream().map(NamespacedKey::fromString).toArray(IKey[]::new);
    }

    /*
     * Abstract
     */

    public abstract B getRaw(String key);

    public B getRaw(IKey key) {
        return getRaw(key.asString());
    }

    public abstract void set(String key, B value);

    public void set(IKey key, B value) {
        set(key.asString(), value);
    }

}
