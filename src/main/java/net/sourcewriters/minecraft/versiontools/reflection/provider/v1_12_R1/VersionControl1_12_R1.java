package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_12_R1;

import net.sourcewriters.minecraft.versiontools.reflection.VersionControl;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_12_R1.reflection.NmsReflection1_12_R1;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.ReflectionProvider;

public class VersionControl1_12_R1 extends VersionControl {

     public static VersionControl1_12_R1 INSTANCE;

     public static VersionControl1_12_R1 init() {
          return INSTANCE != null ? INSTANCE : (INSTANCE = new VersionControl1_12_R1());
     }

     private final ToolProvider1_12_R1 toolProvider = new ToolProvider1_12_R1(this);
     private final TextureProvider1_12_R1 textureProvider = new TextureProvider1_12_R1(this);
     private final PacketHandler1_12_R1 packetHandler = new PacketHandler1_12_R1(this);
     private final EntityProvider1_12_R1 entityProvider = new EntityProvider1_12_R1(this);
     private final PlayerProvider1_12_R1 playerProvider = new PlayerProvider1_12_R1(this);
     private final BukkitConversion1_12_R1 bukkitConversion = new BukkitConversion1_12_R1(this);

     private VersionControl1_12_R1() {
          NmsReflection1_12_R1.INSTANCE.setup(ReflectionProvider.DEFAULT);
     }

     @Override
     public ToolProvider1_12_R1 getToolProvider() {
          return toolProvider;
     }

     @Override
     public EntityProvider1_12_R1 getEntityProvider() {
          return entityProvider;
     }

     @Override
     public PlayerProvider1_12_R1 getPlayerProvider() {
          return playerProvider;
     }

     @Override
     public TextureProvider1_12_R1 getTextureProvider() {
          return textureProvider;
     }

     @Override
     public PacketHandler1_12_R1 getPacketHandler() {
          return packetHandler;
     }

     @Override
     public BukkitConversion1_12_R1 getBukkitConversion() {
          return bukkitConversion;
     }

}