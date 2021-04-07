package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R1.entity;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.MathHelper;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;
import net.minecraft.server.v1_8_R1.WorldServer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.persistence.PersistentContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.type.SkinDataType;
import net.sourcewriters.minecraft.versiontools.reflection.data.wrap.SimpleSyntaxContainer;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.utils.bukkit.Players;
import net.sourcewriters.minecraft.versiontools.utils.minecraft.MojangProfileServer;
import net.sourcewriters.minecraft.versiontools.utils.minecraft.Skin;
import net.sourcewriters.minecraft.versiontools.utils.thread.PostAsync;

public class Player1_8_R1 extends EntityLiving1_8_R1<EntityPlayer> implements NmsPlayer {

    private String realName;
    private Skin realSkin;

    private final WrappedContainer dataAdapter;

    public Player1_8_R1(Player player, PersistentContainer<?> container) {
        super(((CraftPlayer) player).getHandle());
        this.dataAdapter = new SimpleSyntaxContainer<>(container);
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
        getDataAdapter().set("skin", skin, SkinDataType.INSTANCE);
    }

    @Override
    public Skin getSkin() {
        return dataAdapter.getOrDefault("skin", SkinDataType.INSTANCE, realSkin);
    }

    @Override
    public Skin getRealSkin() {
        return realSkin;
    }

    @Override
    public void setName(String name) {
        if (getName().equals(name)) {
            return;
        }
        if (name == null) {
            getDataAdapter().remove("name");
            return;
        }
        getDataAdapter().set("name", name, WrapType.STRING);
    }

    @Override
    public String getName() {
        return getDataAdapter().getOrDefault("name", WrapType.STRING, realName);
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
        return getDataAdapter().getOrDefault("header", WrapType.STRING, "");
    }

    @Override
    public void setPlayerListFooter(String text) {
        setPlayerListHeaderAndFooter(getPlayerListHeader(), text);
    }

    @Override
    public String getPlayerListFooter() {
        return getDataAdapter().getOrDefault("footer", WrapType.STRING, "");
    }

    @Override
    public int getPing() {
        return handle.ping;
    }

    @Override
    public void setPlayerListHeaderAndFooter(String header, String footer) {
        getDataAdapter().set("header", header, WrapType.STRING);
        getDataAdapter().set("footer", footer, WrapType.STRING);
        sendPlayerListInfo(header, footer);
    }

    private final void sendPlayerListInfo(String header, String footer) {
        if (handle.playerConnection.isDisconnected()) {
            return;
        }

        IChatBaseComponent headerComponent = header.isEmpty() ? null : CraftChatMessage.fromString(header, true)[0];
        IChatBaseComponent footerComponent = footer.isEmpty() ? null : CraftChatMessage.fromString(footer, true)[0];

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        Reflect reflect = ReflectionProvider.DEFAULT.getReflect("nmsPacketPlayOutPlayerListHeaderFooter");
        reflect.setFieldValue(packet, "header", headerComponent);
        reflect.setFieldValue(packet, "footer", footerComponent);

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
        handle.playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, CraftChatMessage.fromString(text)[0]));
    }

    @Override
    public void sendTitle(String text) {
        if (handle.playerConnection.isDisconnected()) {
            return;
        }
        handle.playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, CraftChatMessage.fromString(text)[0]));
    }

    @Override
    public void sendActionBar(String text) {
        if (handle.playerConnection.isDisconnected()) {
            return;
        }
        handle.playerConnection.sendPacket(new PacketPlayOutChat(CraftChatMessage.fromString(text)[0], (byte) 2));
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

        ArrayList<PacketPlayOutEntityEquipment> equipmentPackets = new ArrayList<>();
        for (int slot = 0; slot < 5; slot++) {
            equipmentPackets.add(new PacketPlayOutEntityEquipment(handle.getId(), slot, handle.getEquipment(slot)));
        }

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
            for (PacketPlayOutEntityEquipment equipmentPacket : equipmentPackets) {
                connection.sendPacket(equipmentPacket);
            }
        }

        WorldServer world = (WorldServer) handle.world;

        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(handle.dimension, world.getDifficulty(),
            handle.world.worldData.getType(), handle.playerInteractManager.getGameMode());
        PacketPlayOutPosition positionPacket = new PacketPlayOutPosition(handle.locX, handle.locY, handle.locZ, handle.yaw, handle.pitch,
            Collections.emptySet());
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
        PostAsync.forcePost(() -> {
            realName = MojangProfileServer.getName(getUniqueId());
            realSkin = MojangProfileServer.getSkin(realName, getUniqueId());
        });
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