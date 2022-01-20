package net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R2;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R2.reflection.NmsReflection1_8_R2;
import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.provider.VersionControl;

public class VersionControl1_8_R2 extends VersionControl {

    public static VersionControl1_8_R2 INSTANCE;

    public static VersionControl1_8_R2 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_8_R2());
    }

    private final ToolProvider1_8_R2 toolProvider = new ToolProvider1_8_R2(this);
    private final TextureProvider1_8_R2 textureProvider = new TextureProvider1_8_R2(this);
    private final EntityProvider1_8_R2 entityProvider = new EntityProvider1_8_R2(this);
    private final PlayerProvider1_8_R2 playerProvider = new PlayerProvider1_8_R2(this);
    private final BukkitConversion1_8_R2 bukkitConversion = new BukkitConversion1_8_R2(this);

    private VersionControl1_8_R2() {
        NmsReflection1_8_R2.INSTANCE.setup(VersionCompatProvider.get().getLookupProvider());
    }

    @Override
    public ToolProvider1_8_R2 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_8_R2 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_8_R2 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_8_R2 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public BukkitConversion1_8_R2 getBukkitConversion() {
        return bukkitConversion;
    }

}