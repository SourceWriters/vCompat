package net.sourcewriters.minecraft.vcompat;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.vcompat.reflection.VersionControl;

public abstract class VersionCompatProvider {
    
    protected static final Container<VersionCompatProvider> PROVIDER = Container.of();
    
    public static VersionCompatProvider get() {
        return PROVIDER.get();
    }
    
    public static boolean isAvailable() {
        return PROVIDER.isPresent();
    }
    
    public abstract VersionControl getControl();

}
