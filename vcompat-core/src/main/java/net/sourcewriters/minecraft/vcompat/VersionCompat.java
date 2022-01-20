package net.sourcewriters.minecraft.vcompat;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.version.Versions;
import net.sourcewriters.minecraft.vcompat.util.java.tools.ReflectionTools;

public final class VersionCompat extends VersionCompatProvider {
    
    private static final String VERSION_PATH = String.format("%s.provider.impl.v%s.VersionControl%s", VersionCompat.class.getPackageName(), Versions.getServerAsString(), Versions.getServerAsString());

    private final VersionControl control;

    public VersionCompat() {
        this.control = initControl();
        Runtime.getRuntime().addShutdownHook(new Thread(control::shutdown));
    }

    private final VersionControl initControl() {
        Object object = ReflectionTools.createInstance(ReflectionTools.getClass(VERSION_PATH));
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
