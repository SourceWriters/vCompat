package net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R1;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R1.tools.BlockTools1_9_R1;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R1.tools.ServerTools1_9_R1;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R1.tools.SkinTools1_9_R1;
import net.sourcewriters.minecraft.vcompat.reflection.ToolProvider;

public class ToolProvider1_9_R1 extends ToolProvider<VersionControl1_9_R1> {

    private final BlockTools1_9_R1 blockTools = new BlockTools1_9_R1();
    private final SkinTools1_9_R1 skinTools = new SkinTools1_9_R1();
    private final ServerTools1_9_R1 serverTools = new ServerTools1_9_R1();

    protected ToolProvider1_9_R1(VersionControl1_9_R1 versionControl) {
        super(versionControl);
    }

    @Override
    public SkinTools1_9_R1 getSkinTools() {
        return skinTools;
    }

    @Override
    public ServerTools1_9_R1 getServerTools() {
        return serverTools;
    }

    @Override
    public BlockTools1_9_R1 getBlockTools() {
        return blockTools;
    }

}