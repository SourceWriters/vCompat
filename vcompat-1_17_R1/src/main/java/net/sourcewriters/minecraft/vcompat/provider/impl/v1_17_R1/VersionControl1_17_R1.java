package net.sourcewriters.minecraft.vcompat.provider.impl.v1_17_R1;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_17_R1.data.hook.BukkitContainerAdapterHook1_17_R1;

public class VersionControl1_17_R1 extends VersionControl {

    public static VersionControl1_17_R1 INSTANCE;

    public static VersionControl1_17_R1 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_17_R1());
    }

    private final ToolProvider1_17_R1 toolProvider = new ToolProvider1_17_R1(this);
    private final TextureProvider1_17_R1 textureProvider = new TextureProvider1_17_R1(this);
    private final EntityProvider1_17_R1 entityProvider = new EntityProvider1_17_R1(this);
    private final PlayerProvider1_17_R1 playerProvider = new PlayerProvider1_17_R1(this);
    private final BukkitConversion1_17_R1 bukkitConversion = new BukkitConversion1_17_R1(this);

    private VersionControl1_17_R1() {
        BukkitContainerAdapterHook1_17_R1.hookEntity();
    }

    @Override
    public ToolProvider1_17_R1 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_17_R1 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_17_R1 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_17_R1 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public BukkitConversion1_17_R1 getBukkitConversion() {
        return bukkitConversion;
    }

    @Override
    public void shutdown() {
        dataProvider.getDefaultDistributor().shutdown();
        BukkitContainerAdapterHook1_17_R1.unhookAll();
    }

}