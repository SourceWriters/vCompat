package net.sourcewriters.minecraft.vcompat.provider.lookup.handle.field;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public abstract class UnsafeFieldHandle<O> implements IFieldHandle<O> {

    protected static final Unsafe UNSAFE = getUnsafe();

    protected final IFieldHandle<O> setMemoryValue(Object base, long offset, Object value) {
        UNSAFE.putObject(base, offset, value);
        return this;
    }

    protected final Object getMemoryValue(Object base, long offset) {
        return UNSAFE.getObject(base, offset);
    }

    @Override
    public final boolean isUnsafe() {
        return true;
    }

    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception ignore) {
            return null;
        }
    }

}
