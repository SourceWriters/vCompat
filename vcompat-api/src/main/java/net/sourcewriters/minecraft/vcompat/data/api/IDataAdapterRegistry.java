package net.sourcewriters.minecraft.vcompat.data.api;

public interface IDataAdapterRegistry<B> {
    
    Class<B> getBase();
    
    boolean has(Class<?> clazz); // == isRegistered
    
    Object extract(B base);
    
    B wrap(Object value);

}
