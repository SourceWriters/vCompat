package net.sourcewriters.minecraft.vcompat.provider.lookup.handle.field;

import java.lang.reflect.Field;

public final class UnsafeDeclaredFieldHandle extends UnsafeFieldHandle<Field> {
    
    private final Field handle;
    private final long offset;
    
    public UnsafeDeclaredFieldHandle(Field handle) {
        this.handle = handle;
        this.offset = UNSAFE.objectFieldOffset(handle);
    }

    @Override
    public Object getValue() {
        return null; // Stay null because its not static
    }

    @Override
    public Object getValue(Object source) {
        return getMemoryValue(source, offset);
    }

    @Override
    public IFieldHandle<Field> setValue(Object value) {
        return this; // Do nothing because its not static
    }

    @Override
    public IFieldHandle<Field> setValue(Object source, Object value) {
        return setMemoryValue(source, offset, value);
    }

    @Override
    public Field getHandle() {
        return handle;
    }

}
