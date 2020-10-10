package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_13_R2;

import net.sourcewriters.minecraft.versiontools.reflection.VersionControl;

public class VersionControl1_13_R2 extends VersionControl {

	public static VersionControl1_13_R2 INSTANCE;

	public static VersionControl1_13_R2 init() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_13_R2());
	}

	private final ToolProvider1_13_R2 toolProvider = new ToolProvider1_13_R2(this);
	private final TextureProvider1_13_R2 textureProvider = new TextureProvider1_13_R2(this);
	private final PacketHandler1_13_R2 packetHandler = new PacketHandler1_13_R2(this);
	private final EntityProvider1_13_R2 entityProvider = new EntityProvider1_13_R2(this);
	private final PlayerProvider1_13_R2 playerProvider = new PlayerProvider1_13_R2(this);
	private final BukkitConversion1_13_R2 bukkitConversion = new BukkitConversion1_13_R2(this);

	private VersionControl1_13_R2() {

	}

	@Override
	public ToolProvider1_13_R2 getToolProvider() {
		return toolProvider;
	}

	@Override
	public EntityProvider1_13_R2 getEntityProvider() {
		return entityProvider;
	}

	@Override
	public PlayerProvider1_13_R2 getPlayerProvider() {
		return playerProvider;
	}

	@Override
	public TextureProvider1_13_R2 getTextureProvider() {
		return textureProvider;
	}

	@Override
	public PacketHandler1_13_R2 getPacketHandler() {
		return packetHandler;
	}

	@Override
	public BukkitConversion1_13_R2 getBukkitConversion() {
		return bukkitConversion;
	}

}
