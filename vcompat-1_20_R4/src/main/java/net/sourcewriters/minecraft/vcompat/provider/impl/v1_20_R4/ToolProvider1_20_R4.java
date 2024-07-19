package net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4;

import net.sourcewriters.minecraft.vcompat.provider.ToolProvider;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.tools.BlockTools1_20_R4;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.tools.ServerTools1_20_R4;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.tools.SkinTools1_20_R4;

public class ToolProvider1_20_R4 extends ToolProvider<VersionControl1_20_R4> {

    private final BlockTools1_20_R4 blockTools = new BlockTools1_20_R4();
    private final SkinTools1_20_R4 skinTools = new SkinTools1_20_R4();
    private final ServerTools1_20_R4 serverTools = new ServerTools1_20_R4();

    protected ToolProvider1_20_R4(VersionControl1_20_R4 versionControl) {
        super(versionControl);
    }

    @Override
    public SkinTools1_20_R4 getSkinTools() {
        return skinTools;
    }

    @Override
    public ServerTools1_20_R4 getServerTools() {
        return serverTools;
    }

    @Override
    public BlockTools1_20_R4 getBlockTools() {
        return blockTools;
    }

}