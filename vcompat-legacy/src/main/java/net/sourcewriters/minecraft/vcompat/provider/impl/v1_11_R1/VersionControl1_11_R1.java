package net.sourcewriters.minecraft.vcompat.provider.impl.v1_11_R1;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_11_R1.reflection.NmsReflection1_11_R1;
import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.provider.VersionControl;

public class VersionControl1_11_R1 extends VersionControl {

    public static VersionControl1_11_R1 INSTANCE;

    public static VersionControl1_11_R1 init() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_11_R1());
    }

    private final ToolProvider1_11_R1 toolProvider = new ToolProvider1_11_R1(this);
    private final TextureProvider1_11_R1 textureProvider = new TextureProvider1_11_R1(this);
    private final EntityProvider1_11_R1 entityProvider = new EntityProvider1_11_R1(this);
    private final PlayerProvider1_11_R1 playerProvider = new PlayerProvider1_11_R1(this);
    private final BukkitConversion1_11_R1 bukkitConversion = new BukkitConversion1_11_R1(this);

    private VersionControl1_11_R1() {
        NmsReflection1_11_R1.INSTANCE.setup(VersionCompatProvider.get().getLookupProvider());
    }

    @Override
    public ToolProvider1_11_R1 getToolProvider() {
        return toolProvider;
    }

    @Override
    public EntityProvider1_11_R1 getEntityProvider() {
        return entityProvider;
    }

    @Override
    public PlayerProvider1_11_R1 getPlayerProvider() {
        return playerProvider;
    }

    @Override
    public TextureProvider1_11_R1 getTextureProvider() {
        return textureProvider;
    }

    @Override
    public BukkitConversion1_11_R1 getBukkitConversion() {
        return bukkitConversion;
    }

}