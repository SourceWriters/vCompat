package net.sourcewriters.minecraft.vcompat;

import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;
import net.sourcewriters.minecraft.vcompat.util.java.tools.ReflectionTools;

public abstract class VersionCompatProvider {

    protected static final Container<VersionCompatProvider> PROVIDER = Container.of();
    private static final String IMPLEMENTATION_PATH = VersionCompatProvider.class.getPackageName() + ".VersionCompat";

    public static VersionCompatProvider get() {
        if (PROVIDER.isPresent()) {
            return PROVIDER.get();
        }
        try {
            Class<?> clazz = ReflectionTools.getClass(IMPLEMENTATION_PATH);
            System.out.println("Class: " + (clazz == null));
            System.out.println("Class: " + IMPLEMENTATION_PATH);
            Object object = clazz.getConstructor().newInstance();
            if (object == null || !(object instanceof VersionCompatProvider)) {
                throw new IllegalStateException("Can't initialize VersionCompatProvider!");
            }
            return PROVIDER.replace((VersionCompatProvider) object).lock().get();
        } catch (Exception exp) {
            System.out.println(Exceptions.stackTraceToString(exp));
            throw new IllegalStateException("Can't initialize VersionCompatProvider!");
        }
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
