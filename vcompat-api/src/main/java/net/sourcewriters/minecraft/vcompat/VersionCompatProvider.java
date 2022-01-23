package net.sourcewriters.minecraft.vcompat;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;
import net.sourcewriters.minecraft.vcompat.version.Versions;

public abstract class VersionCompatProvider {

    protected static final Container<VersionCompatProvider> PROVIDER = Container.of();
    private static final String IMPLEMENTATION_PATH = VersionCompatProvider.class.getPackageName() + ".VersionCompat";

    public static VersionCompatProvider get() {
        if (PROVIDER.isPresent()) {
            return PROVIDER.get();
        }
        System.out.println(
            "Initializing vCompat on with '" + Versions.getServerAsString() + "' core on '" + Versions.getMinecraftAsString() + "'!");
        Object object = ClassLookup.of(IMPLEMENTATION_PATH).init();
        if (object == null || !(object instanceof VersionCompatProvider)) {
            throw new IllegalStateException("Can't initialize VersionCompatProvider!");
        }
        VersionCompatProvider provider = PROVIDER.replace((VersionCompatProvider) object).lock().get();
        try {
            provider.init();
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to initialize vCompat implementation", exception);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(provider::shutdown));
        System.out.println("Initialization of vCompat complete!");
        return provider;
    }

    protected final ClassLookupProvider lookupProvider = new ClassLookupProvider();

    /*
     * Impl
     */

    protected abstract void init() throws Exception;
    
    protected abstract void shutdown();

    public abstract VersionControl getControl();

    public final ClassLookupProvider getLookupProvider() {
        return lookupProvider;
    }

}
