package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R2;

import net.sourcewriters.minecraft.versiontools.reflection.ToolProvider;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R2.tools.*;

public class ToolProvider1_8_R2 extends ToolProvider<VersionControl1_8_R2> {

	private final SkinTools1_8_R2 skinTools = new SkinTools1_8_R2();
	private final ServerTools1_8_R2 serverTools = new ServerTools1_8_R2();

	protected ToolProvider1_8_R2(VersionControl1_8_R2 versionControl) {
		super(versionControl);
	}

	@Override
	public SkinTools1_8_R2 getSkinTools() {
		return skinTools;
	}

	@Override
	public ServerTools1_8_R2 getServerTools() {
		return serverTools;
	}

}
