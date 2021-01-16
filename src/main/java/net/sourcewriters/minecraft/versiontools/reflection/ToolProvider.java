package net.sourcewriters.minecraft.versiontools.reflection;

import net.sourcewriters.minecraft.versiontools.reflection.tools.ServerTools;
import net.sourcewriters.minecraft.versiontools.reflection.tools.SkinTools;

public abstract class ToolProvider<V extends VersionControl> extends VersionHandler<V> {

	protected ToolProvider(V versionControl) {
		super(versionControl);
	}
	
	public abstract SkinTools getSkinTools();

	public abstract ServerTools getServerTools();
	
}