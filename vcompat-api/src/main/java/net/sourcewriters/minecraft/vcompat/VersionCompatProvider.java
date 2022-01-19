package net.sourcewriters.minecraft.vcompat;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;

public abstract class VersionCompatProvider {
    
    protected static final Container<VersionCompatProvider> PROVIDER = Container.of();
    
    public static VersionCompatProvider get() {
        return PROVIDER.get();
    }
    
    /*
     * Impl
     */
    
    public abstract VersionControl getControl();

}
