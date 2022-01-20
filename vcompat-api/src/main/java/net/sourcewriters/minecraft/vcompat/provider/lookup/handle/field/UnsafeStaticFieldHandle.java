package net.sourcewriters.minecraft.vcompat.provider.lookup.handle.field;

import java.lang.reflect.Field;

public final class UnsafeStaticFieldHandle extends UnsafeFieldHandle<Field> {

    private final Field handle;
    private final Object base;
    private final long offset;

    public UnsafeStaticFieldHandle(Field handle) {
        this.handle = handle;
        this.base = UNSAFE.staticFieldBase(handle);
        this.offset = UNSAFE.staticFieldOffset(handle);
    }

    @Override
    public Object getValue() {
        return getMemoryValue(base, offset);
    }

    @Override
    public Object getValue(Object source) {
        return null; // Stay null because its static
    }

    @Override
    public IFieldHandle<Field> setValue(Object value) {
        return setMemoryValue(base, offset, value);
    }

    @Override
    public IFieldHandle<Field> setValue(Object source, Object value) {
        return this; // Do nothing because its static
    }

    @Override
    public Field getHandle() {
        return handle;
    }

}
