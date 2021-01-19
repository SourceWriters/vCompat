package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3;

import net.sourcewriters.minecraft.versiontools.reflection.ToolProvider;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3.tools.*;

public class ToolProvider1_16_R3 extends ToolProvider<VersionControl1_16_R3> {

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

}