package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.data.hook.BukkitContainerAdapterHook1_18_R2;

public class VersionControl1_18_R2 extends VersionControl {

    public static VersionControl1_18_R2 INSTANCE;

    public static VersionControl1_18_R2 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_18_R2());
    }

    private final ToolProvider1_18_R2 toolProvider = new ToolProvider1_18_R2(this);
    private final TextureProvider1_18_R2 textureProvider = new TextureProvider1_18_R2(this);
    private final EntityProvider1_18_R2 entityProvider = new EntityProvider1_18_R2(this);
    private final PlayerProvider1_18_R2 playerProvider = new PlayerProvider1_18_R2(this);
    private final BukkitConversion1_18_R2 bukkitConversion = new BukkitConversion1_18_R2(this);

    private VersionControl1_18_R2() {
        BukkitContainerAdapterHook1_18_R2.hookEntity();
    }

    @Override
    public ToolProvider1_18_R2 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_18_R2 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_18_R2 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_18_R2 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public BukkitConversion1_18_R2 getBukkitConversion() {
        return bukkitConversion;
    }

    @Override
    public void shutdown() {
        dataProvider.getDefaultDistributor().shutdown();
        BukkitContainerAdapterHook1_18_R2.unhookAll();
    }

}