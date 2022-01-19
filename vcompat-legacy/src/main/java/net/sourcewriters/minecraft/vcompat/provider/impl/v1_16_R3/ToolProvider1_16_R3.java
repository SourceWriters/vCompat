package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.tools.BlockTools1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.tools.ServerTools1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.tools.SkinTools1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.ToolProvider;

public class ToolProvider1_16_R3 extends ToolProvider<VersionControl1_16_R3> {

    private final BlockTools1_16_R3 blockTools = new BlockTools1_16_R3();
    private final SkinTools1_16_R3 skinTools = new SkinTools1_16_R3();
    private final ServerTools1_16_R3 serverTools = new ServerTools1_16_R3();

    protected ToolProvider1_16_R3(VersionControl1_16_R3 versionControl) {
        super(versionControl);
    }

    @Override
    public SkinTools1_16_R3 getSkinTools() {
        return skinTools;
    }

    @Override
    public ServerTools1_16_R3 getServerTools() {
        return serverTools;
    }

    @Override
    public BlockTools1_16_R3 getBlockTools() {
        return blockTools;
    }

}