package net.sourcewriters.minecraft.vcompat.provider.lookup.handle.field;

public interface IFieldHandle<O> {
    
    Object getValue();
    
    Object getValue(Object source);
    
    IFieldHandle<O> setValue(Object value);
    
    IFieldHandle<O> setValue(Object source, Object value);
    
    O getHandle();
    
    boolean isUnsafe();

}
