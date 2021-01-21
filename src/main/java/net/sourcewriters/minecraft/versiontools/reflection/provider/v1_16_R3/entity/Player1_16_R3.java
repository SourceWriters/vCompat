package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3.entity;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Pair;

import net.minecraft.server.v1_16_R3.BiomeManager;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.MathHelper;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_16_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.WorldServer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.type.SkinDataType;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3.data.SyntaxContainer1_16_R3;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.skin.Skin;
import net.sourcewriters.minecraft.versiontools.utils.bukkit.MojangProfileServer;
import net.sourcewriters.minecraft.versiontools.utils.bukkit.Players;
import net.sourcewriters.minecraft.versiontools.utils.thread.PostAsync;

public class Player1_16_R3 extends EntityLiving1_16_R3<EntityPlayer> implements NmsPlayer {

     private String realName;
     private final WrappedContainer dataAdapter;

     public Player1_16_R3(Player player) {
          super(((CraftPlayer) player).getHandle());
          dataAdapter = new SyntaxContainer1_16_R3(getBukkitPlayer().getPersistentDataContainer());
          update(false);
     }

     @Override
     public CraftPlayer getBukkitPlayer() {
          return handle.getBukkitEntity();
     }

     @Override
     public WrappedContainer getDataAdapter() {
          return dataAdapter;
     }

     @Override
     public void setSkin(Skin skin) {
          if (skin == null || getSkin().equals(skin)) {
               return;
          }
          dataAdapter.set("skin", skin, SkinDataType.INSTANCE);
     }

     @Override
     public Skin getSkin() {
          return dataAdapter.getOrDefault("skin", SkinDataType.INSTANCE, Skin.NONE);
     }

     @Override
     public void setName(String name) {
          if (getName().equals(name)) {
               return;
          }
          if (name == null) {
               dataAdapter.remove("name");
               return;
          }
          dataAdapter.set("name", name, WrapType.STRING);
     }

     @Override
     public String getName() {
          return dataAdapter.getOrDefault("name", WrapType.STRING, realName);
     }

     @Override
     public String getRealName() {
          return realName;
     }

     @Override
     public void setPlayerListHeader(String text) {
          setPlayerListHeaderAndFooter(text, getPlayerListFooter());
     }

     @Override
     public String getPlayerListHeader() {
          return dataAdapter.getOrDefault("header", WrapType.STRING, "");
     }

     @Override
     public void setPlayerListFooter(String text) {
          setPlayerListHeaderAndFooter(getPlayerListHeader(), text);
     }

     @Override
     public String getPlayerListFooter() {
          return dataAdapter.getOrDefault("footer", WrapType.STRING, "");
     }

     @Override
     public int getPing() {
          return handle.ping;
     }

     @Override
     public void setPlayerListHeaderAndFooter(String header, String footer) {
          dataAdapter.set("header", header, WrapType.STRING);
          dataAdapter.set("footer", footer, WrapType.STRING);
          sendPlayerListInfo(header, footer);
     }

     private final void sendPlayerListInfo(String header, String footer) {
          if (handle.playerConnection.isDisconnected()) {
               return;
          }

          IChatBaseComponent headerComponent = header.isEmpty() ? null : CraftChatMessage.fromStringOrNull(header, true);
          IChatBaseComponent footerComponent = footer.isEmpty() ? null : CraftChatMessage.fromStringOrNull(footer, true);

          PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

          packet.header = headerComponent;
          packet.footer = footerComponent;

          handle.playerConnection.sendPacket(packet);
     }

     @Override
     public void setTitleTimes(int fadeIn, int stay, int fadeOut) {
          if (handle.playerConnection.isDisconnected()) {
               return;
          }
          handle.playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut));
     }

     @Override
     public void sendSubtitle(String text) {
          if (handle.playerConnection.isDisconnected()) {
               return;
          }
          handle.playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, CraftChatMessage.fromStringOrNull(text)));
     }

     @Override
     public void sendTitle(String text) {
          if (handle.playerConnection.isDisconnected()) {
               return;
          }
          handle.playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, CraftChatMessage.fromStringOrNull(text)));
     }

     @Override
     public void sendActionBar(String text) {
          if (handle.playerConnection.isDisconnected()) {
               return;
          }
          handle.playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, CraftChatMessage.fromStringOrNull(text)));
     }

     @Override
     public void fakeRespawn() {
          if (handle.playerConnection.isDisconnected()) {
               return;
          }
          PacketPlayOutPlayerInfo remInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, handle);
          PacketPlayOutPlayerInfo addInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, handle);

          PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(handle.getId());
          PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn(handle);
          PacketPlayOutEntityHeadRotation rotationPacket = new PacketPlayOutEntityHeadRotation(handle,
               (byte) MathHelper.d(handle.getHeadRotation() * 256F / 360F));

          ArrayList<Pair<EnumItemSlot, ItemStack>> list = new ArrayList<>();
          for (EnumItemSlot slot : EnumItemSlot.values()) {
               list.add(Pair.of(slot, handle.getEquipment(slot)));
          }
          PacketPlayOutEntityEquipment equipmentPacket = new PacketPlayOutEntityEquipment(handle.getId(), list);

          Player self = getBukkitPlayer();
          Player[] players = Players.getOnlineWithout(getUniqueId());
          for (Player player : players) {
               if (!player.canSee(self)) {
                    continue;
               }
               PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
               connection.sendPacket(remInfoPacket);
               connection.sendPacket(addInfoPacket);
               connection.sendPacket(destroyPacket);
               connection.sendPacket(spawnPacket);
               connection.sendPacket(rotationPacket);
               connection.sendPacket(equipmentPacket);
          }

          WorldServer world = (WorldServer) handle.world;

          PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(world.getDimensionManager(), world.getDimensionKey(),
               BiomeManager.a(world.getSeed()), handle.playerInteractManager.getGameMode(), handle.playerInteractManager.c(),
               world.isDebugWorld(), world.isFlatWorld(), true);
          PacketPlayOutPosition positionPacket = new PacketPlayOutPosition(handle.locX(), handle.locY(), handle.locZ(), handle.yaw,
               handle.pitch, Collections.emptySet(), 0);
          PacketPlayOutHeldItemSlot itemPacket = new PacketPlayOutHeldItemSlot(handle.inventory.itemInHandIndex);
          PacketPlayOutEntityStatus statusPacket = new PacketPlayOutEntityStatus(handle, (byte) 28);
          PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(handle.getId(), handle.getDataWatcher(), true);

          PlayerConnection connection = handle.playerConnection;
          connection.sendPacket(remInfoPacket);
          connection.sendPacket(addInfoPacket);
          connection.sendPacket(respawnPacket);
          connection.sendPacket(positionPacket);
          connection.sendPacket(itemPacket);
          connection.sendPacket(statusPacket);
          connection.sendPacket(metadataPacket);

          handle.updateAbilities();
          handle.triggerHealthUpdate();
          handle.updateInventory(handle.defaultContainer);
          if (handle.activeContainer != handle.defaultContainer) {
               handle.updateInventory(handle.activeContainer);
          }
          self.recalculatePermissions();
     }

     @Override
     public void respawn() {
          if (handle.playerConnection.isDisconnected()) {
               return;
          }
          handle.playerConnection.sendPacket(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
     }

     @Override
     public void update() {
          update(true);
     }

     private final void update(boolean flag) {
          PostAsync.forcePost(() -> realName = MojangProfileServer.getName(getUniqueId()));
          if (flag) {
               GameProfile profile = handle.getProfile();

               Skin skin = getSkin();
               PropertyMap properties = profile.getProperties();
               properties.removeAll("textures");
               properties.put("textures", new Property("textures", skin.getValue(), skin.getSignature()));

               ReflectionProvider.DEFAULT.getReflect("mjGameProfile").setFieldValue(profile, "name", getName());

               fakeRespawn();
          }
     }

}