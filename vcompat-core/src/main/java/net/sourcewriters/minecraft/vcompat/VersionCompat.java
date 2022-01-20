package net.sourcewriters.minecraft.vcompat;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;
import net.sourcewriters.minecraft.vcompat.version.Versions;

public final class VersionCompat extends VersionCompatProvider {
    
    private static final String VERSION_PATH = String.format("%s.provider.impl.%s.VersionControl%s", VersionCompat.class.getPackageName(), Versions.getServerAsString(), Versions.getServerAsString().substring(1));

    private final VersionControl control;

    public VersionCompat() {
        this.control = initControl();
        Runtime.getRuntime().addShutdownHook(new Thread(control::shutdown));
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
        return control;
    }

}
