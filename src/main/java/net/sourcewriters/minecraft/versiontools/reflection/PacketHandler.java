package net.sourcewriters.minecraft.versiontools.reflection;

public abstract class PacketHandler<V extends VersionControl> extends VersionHandler<V> {

	protected PacketHandler(V versionControl) {
		super(versionControl);
	}
	
}
