package net.sourcewriters.minecraft.vcompat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.utils.java.tools.Container;
import net.sourcewriters.minecraft.vcompat.version.Versions;

public final class VersionCompat extends VersionCompatProvider {

    private static final String VERSION_PATH = String.format("%s.provider.impl.%s.VersionControl%s", VersionCompat.class.getPackageName(),
        Versions.getServerAsString(), Versions.getServerAsString().substring(1));

    private final Container<VersionControl> control = Container.of();
    private volatile boolean init = false;

    @Override
    protected final void init() throws Exception {
        if (init) {
            return;
        }
        init = true;
        control.replace(initControl()).lock();
    }

    @Override
    protected final void shutdown() {
        if (!init) {
            return;
        }
        control.ifPresent(VersionControl::shutdown);
    }

    private final VersionControl initControl() {
        try {
            Class<?> clazz = Class.forName(VERSION_PATH, true, getClass().getClassLoader());
            Object object = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup())
                .findStatic(clazz, "init", MethodType.methodType(clazz)).invoke();
            return (VersionControl) object;
        } catch (Throwable e) {
            throw new IllegalStateException("Couldn't initialize VersionControl", e);
        }
    }

    @Override
    public final VersionControl getControl() {
        return control.get();
    }

}
