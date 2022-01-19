package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.data.hook.BukkitContainerAdapterHook1_16_R3;
import net.sourcewriters.minecraft.vcompat.reflection.VersionControl;

public class VersionControl1_16_R3 extends VersionControl {

    public static VersionControl1_16_R3 INSTANCE;

    public static VersionControl1_16_R3 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_16_R3());
    }

    private final ToolProvider1_16_R3 toolProvider = new ToolProvider1_16_R3(this);
    private final TextureProvider1_16_R3 textureProvider = new TextureProvider1_16_R3(this);
    private final PacketHandler1_16_R3 packetHandler = new PacketHandler1_16_R3(this);
    private final EntityProvider1_16_R3 entityProvider = new EntityProvider1_16_R3(this);
    private final PlayerProvider1_16_R3 playerProvider = new PlayerProvider1_16_R3(this);
    private final BukkitConversion1_16_R3 bukkitConversion = new BukkitConversion1_16_R3(this);

    private VersionControl1_16_R3() {
        BukkitContainerAdapterHook1_16_R3.hookEntity();
    }

    @Override
    public ToolProvider1_16_R3 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_16_R3 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_16_R3 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_16_R3 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public PacketHandler1_16_R3 getPacketHandler() {
        return packetHandler;
    }

    @Override
    public BukkitConversion1_16_R3 getBukkitConversion() {
        return bukkitConversion;
    }

    @Override
    public void shutdown() {
        dataProvider.getDefaultDistributor().shutdown();
        BukkitContainerAdapterHook1_16_R3.unhookAll();
    }

}