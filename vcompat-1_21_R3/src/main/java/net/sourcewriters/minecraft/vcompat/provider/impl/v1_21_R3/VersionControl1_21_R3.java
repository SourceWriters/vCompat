package net.sourcewriters.minecraft.vcompat.provider.impl.v1_21_R3;

import net.sourcewriters.minecraft.vcompat.provider.VersionControl;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_21_R3.data.hook.BukkitContainerAdapterHook1_21_R3;

public class VersionControl1_21_R3 extends VersionControl {

    public static VersionControl1_21_R3 INSTANCE;

    public static VersionControl1_21_R3 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_21_R3());
    }

    private final ToolProvider1_21_R3 toolProvider = new ToolProvider1_21_R3(this);
    private final TextureProvider1_21_R3 textureProvider = new TextureProvider1_21_R3(this);
    private final EntityProvider1_21_R3 entityProvider = new EntityProvider1_21_R3(this);
    private final PlayerProvider1_21_R3 playerProvider = new PlayerProvider1_21_R3(this);
    private final BukkitConversion1_21_R3 bukkitConversion = new BukkitConversion1_21_R3(this);

    private VersionControl1_21_R3() {
        BukkitContainerAdapterHook1_21_R3.hookEntity();
    }

    @Override
    public ToolProvider1_21_R3 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_21_R3 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_21_R3 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_21_R3 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public BukkitConversion1_21_R3 getBukkitConversion() {
        return bukkitConversion;
    }

    @Override
    public void shutdown() {
        dataProvider.getDefaultDistributor().shutdown();
        BukkitContainerAdapterHook1_21_R3.unhookAll();
    }

}