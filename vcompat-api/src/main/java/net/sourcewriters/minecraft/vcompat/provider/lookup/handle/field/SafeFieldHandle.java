package net.sourcewriters.minecraft.vcompat.provider.lookup.handle.field;

import java.lang.invoke.VarHandle;

public class SafeFieldHandle implements IFieldHandle<VarHandle> {

    private final VarHandle handle;

    public SafeFieldHandle(VarHandle handle) {
        this.handle = handle;
    }

    @Override
    public Object getValue() {
        return handle.get();
    }

    @Override
    public Object getValue(Object source) {
        return handle.get(source);
    }

    @Override
    public IFieldHandle<VarHandle> setValue(Object value) {
        handle.set(value);
        return this;
    }

    @Override
    public IFieldHandle<VarHandle> setValue(Object source, Object value) {
        handle.set(source, value);
        return this;
    }

    @Override
    public VarHandle getHandle() {
        return handle;
    }

    @Override
    public boolean isUnsafe() {
        return false;
    }

}
