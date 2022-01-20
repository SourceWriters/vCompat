package net.sourcewriters.minecraft.vcompat.provider;

import net.sourcewriters.minecraft.vcompat.provider.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.provider.tools.ServerTools;
import net.sourcewriters.minecraft.vcompat.provider.tools.SkinTools;

public abstract class ToolProvider<V extends VersionControl> extends VersionHandler<V> {

    protected ToolProvider(V versionControl) {
        super(versionControl);
    }

    public abstract SkinTools getSkinTools();

    public abstract ServerTools getServerTools();

    public abstract BlockTools getBlockTools();

}