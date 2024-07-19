package net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R3;

import net.sourcewriters.minecraft.vcompat.provider.ToolProvider;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R3.tools.BlockTools1_20_R3;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R3.tools.ServerTools1_20_R3;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R3.tools.SkinTools1_20_R3;

public class ToolProvider1_20_R3 extends ToolProvider<VersionControl1_20_R3> {

    private final BlockTools1_20_R3 blockTools = new BlockTools1_20_R3();
    private final SkinTools1_20_R3 skinTools = new SkinTools1_20_R3();
    private final ServerTools1_20_R3 serverTools = new ServerTools1_20_R3();

    protected ToolProvider1_20_R3(VersionControl1_20_R3 versionControl) {
        super(versionControl);
    }

    @Override
    public SkinTools1_20_R3 getSkinTools() {
        return skinTools;
    }

    @Override
    public ServerTools1_20_R3 getServerTools() {
        return serverTools;
    }

    @Override
    public BlockTools1_20_R3 getBlockTools() {
        return blockTools;
    }

}