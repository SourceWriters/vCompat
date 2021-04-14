package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R3;

import net.sourcewriters.minecraft.vcompat.reflection.ToolProvider;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R3.tools.BlockTools1_8_R3;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R3.tools.ServerTools1_8_R3;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R3.tools.SkinTools1_8_R3;

public class ToolProvider1_8_R3 extends ToolProvider<VersionControl1_8_R3> {

    private final BlockTools1_8_R3 blockTools = new BlockTools1_8_R3();
    private final SkinTools1_8_R3 skinTools = new SkinTools1_8_R3();
    private final ServerTools1_8_R3 serverTools = new ServerTools1_8_R3();

    protected ToolProvider1_8_R3(VersionControl1_8_R3 versionControl) {
        super(versionControl);
    }

    @Override
    public SkinTools1_8_R3 getSkinTools() {
        return skinTools;
    }

    @Override
    public ServerTools1_8_R3 getServerTools() {
        return serverTools;
    }

    @Override
    public BlockTools1_8_R3 getBlockTools() {
        return blockTools;
    }

}