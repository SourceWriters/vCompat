package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1;

import net.sourcewriters.minecraft.versiontools.reflection.ToolProvider;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.tools.BlockTools1_14_R1;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.tools.ServerTools1_14_R1;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.tools.SkinTools1_14_R1;

public class ToolProvider1_14_R1 extends ToolProvider<VersionControl1_14_R1> {

    private final BlockTools1_14_R1 blockTools = new BlockTools1_14_R1();
    private final SkinTools1_14_R1 skinTools = new SkinTools1_14_R1();
    private final ServerTools1_14_R1 serverTools = new ServerTools1_14_R1();

    protected ToolProvider1_14_R1(VersionControl1_14_R1 versionControl) {
        super(versionControl);
    }

    @Override
    public SkinTools1_14_R1 getSkinTools() {
        return skinTools;
    }

    @Override
    public ServerTools1_14_R1 getServerTools() {
        return serverTools;
    }

    @Override
    public BlockTools1_14_R1 getBlockTools() {
        return blockTools;
    }

}