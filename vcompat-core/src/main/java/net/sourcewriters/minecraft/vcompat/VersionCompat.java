package net.sourcewriters.minecraft.vcompat;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;
import net.sourcewriters.minecraft.vcompat.version.Versions;

final class VersionCompat extends VersionCompatProvider {

    private final VersionControl control;

    public VersionCompat() {
        this.control = initControl();
        Runtime.getRuntime().addShutdownHook(new Thread(control::shutdown));
    }

    private final VersionControl initControl() {
        Object object = ClassLookup
            .of(VersionCompat.class.getPackageName() + ".provider.impl.v" + Versions.getServerAsString() + ".VersionControlImpl").init();
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
