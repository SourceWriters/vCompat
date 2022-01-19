package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R1;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R1.data.hook.BukkitContainerAdapterHook1_16_R1;
import net.sourcewriters.minecraft.vcompat.reflection.VersionControl;

public class VersionControl1_16_R1 extends VersionControl {

    public static VersionControl1_16_R1 INSTANCE;

    public static VersionControl1_16_R1 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_16_R1());
    }

    private final ToolProvider1_16_R1 toolProvider = new ToolProvider1_16_R1(this);
    private final TextureProvider1_16_R1 textureProvider = new TextureProvider1_16_R1(this);
    private final PacketHandler1_16_R1 packetHandler = new PacketHandler1_16_R1(this);
    private final EntityProvider1_16_R1 entityProvider = new EntityProvider1_16_R1(this);
    private final PlayerProvider1_16_R1 playerProvider = new PlayerProvider1_16_R1(this);
    private final BukkitConversion1_16_R1 bukkitConversion = new BukkitConversion1_16_R1(this);

    private VersionControl1_16_R1() {
        BukkitContainerAdapterHook1_16_R1.hookEntity();
    }

    @Override
    public ToolProvider1_16_R1 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_16_R1 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_16_R1 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_16_R1 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public PacketHandler1_16_R1 getPacketHandler() {
        return packetHandler;
    }

    @Override
    public BukkitConversion1_16_R1 getBukkitConversion() {
        return bukkitConversion;
    }
    
    @Override
    public void shutdown() {
        dataProvider.getDefaultDistributor().shutdown();
        BukkitContainerAdapterHook1_16_R1.unhookAll();
    }

}