package net.sourcewriters.minecraft.vcompat;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

public abstract class VersionCompatProvider {

    protected static final Container<VersionCompatProvider> PROVIDER = Container.of();
    private static final String IMPLEMENTATION_PATH = VersionCompatProvider.class.getPackageName() + ".VersionCompat";

    public static VersionCompatProvider get() {
        if (PROVIDER.isPresent()) {
            return PROVIDER.get();
        }
            Object object = ClassLookup.of(IMPLEMENTATION_PATH).init();
            if (object == null || !(object instanceof VersionCompatProvider)) {
                throw new IllegalStateException("Can't initialize VersionCompatProvider!");
            }
            return PROVIDER.replace((VersionCompatProvider) object).lock().get();
    }

    protected final ClassLookupProvider lookupProvider = new ClassLookupProvider();

    /*
     * Impl
     */

    public abstract VersionControl getControl();

    public final ClassLookupProvider getLookupProvider() {
        return lookupProvider;
    }

}
