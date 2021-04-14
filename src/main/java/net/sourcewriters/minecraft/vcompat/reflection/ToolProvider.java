package net.sourcewriters.minecraft.vcompat.reflection;

import net.sourcewriters.minecraft.vcompat.reflection.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.reflection.tools.ServerTools;
import net.sourcewriters.minecraft.vcompat.reflection.tools.SkinTools;

public abstract class ToolProvider<V extends VersionControl> extends VersionHandler<V> {

    protected ToolProvider(V versionControl) {
        super(versionControl);
    }

    public abstract SkinTools getSkinTools();

    public abstract ServerTools getServerTools();

    public abstract BlockTools getBlockTools();

}