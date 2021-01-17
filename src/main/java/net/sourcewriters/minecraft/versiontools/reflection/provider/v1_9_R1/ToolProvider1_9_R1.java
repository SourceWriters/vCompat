package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_9_R1;

import net.sourcewriters.minecraft.versiontools.reflection.ToolProvider;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_9_R1.tools.*;

public class ToolProvider1_9_R1 extends ToolProvider<VersionControl1_9_R1> {

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

}