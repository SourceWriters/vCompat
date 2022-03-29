package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2;

import net.sourcewriters.minecraft.vcompat.provider.ToolProvider;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.tools.BlockTools1_18_R2;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.tools.ServerTools1_18_R2;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.tools.SkinTools1_18_R2;

public class ToolProvider1_18_R2 extends ToolProvider<VersionControl1_18_R2> {

    private final BlockTools1_18_R2 blockTools = new BlockTools1_18_R2();
    private final SkinTools1_18_R2 skinTools = new SkinTools1_18_R2();
    private final ServerTools1_18_R2 serverTools = new ServerTools1_18_R2();

    protected ToolProvider1_18_R2(VersionControl1_18_R2 versionControl) {
        super(versionControl);
    }

    @Override
    public SkinTools1_18_R2 getSkinTools() {
        return skinTools;
    }

    @Override
    public ServerTools1_18_R2 getServerTools() {
        return serverTools;
    }

    @Override
    public BlockTools1_18_R2 getBlockTools() {
        return blockTools;
    }

}