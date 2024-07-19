package net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.data.hook.BukkitContainerAdapterHook1_20_R4;

public class VersionControl1_20_R4 extends VersionControl {

    public static VersionControl1_20_R4 INSTANCE;

    public static VersionControl1_20_R4 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_20_R4());
    }

    private final ToolProvider1_20_R4 toolProvider = new ToolProvider1_20_R4(this);
    private final TextureProvider1_20_R4 textureProvider = new TextureProvider1_20_R4(this);
    private final EntityProvider1_20_R4 entityProvider = new EntityProvider1_20_R4(this);
    private final PlayerProvider1_20_R4 playerProvider = new PlayerProvider1_20_R4(this);
    private final BukkitConversion1_20_R4 bukkitConversion = new BukkitConversion1_20_R4(this);

    private VersionControl1_20_R4() {
        BukkitContainerAdapterHook1_20_R4.hookEntity();
    }

    @Override
    public ToolProvider1_20_R4 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_20_R4 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_20_R4 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_20_R4 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public BukkitConversion1_20_R4 getBukkitConversion() {
        return bukkitConversion;
    }

    @Override
    public void shutdown() {
        dataProvider.getDefaultDistributor().shutdown();
        BukkitContainerAdapterHook1_20_R4.unhookAll();
    }

}