package net.sourcewriters.minecraft.vcompat;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

public abstract class VersionCompatProvider {

    protected static final Container<VersionCompatProvider> PROVIDER = Container.of();

    public static VersionCompatProvider get() {
        if (PROVIDER.isPresent()) {
            return PROVIDER.get();
        }
        Object object = ClassLookup.of(VersionCompatProvider.class.getPackageName() + ".VersionCompat").init();
        if (object == null || !(object instanceof VersionCompatProvider)) {
            throw new IllegalStateException("Can't initialize VersionCompatProvider!");
        }
        return PROVIDER.replace((VersionCompatProvider) object).lock().get();
    }

    /*
     * Impl
     */

    public abstract VersionControl getControl();

}
