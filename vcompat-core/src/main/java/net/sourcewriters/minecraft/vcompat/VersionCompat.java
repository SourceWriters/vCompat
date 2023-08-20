package net.sourcewriters.minecraft.vcompat;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;
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
        if(!init) {
            return;
        }
        control.ifPresent(VersionControl::shutdown);
    }

    private final VersionControl initControl() {
        Object object = ClassLookup.of(VERSION_PATH).searchMethod("init", "init").run("init");
        if (object == null || !(object instanceof VersionControl)) {
            throw new IllegalStateException("Can't initialize VersionControl");
        }
        return (VersionControl) object;
    }

    @Override
    public final VersionControl getControl() {
        return control.get();
    }

}
