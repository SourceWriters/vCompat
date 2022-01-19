package net.sourcewriters.minecraft.vcompat.provider.impl.v1_13_R2.entity;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EnumItemSlot;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.MathHelper;
import net.minecraft.server.v1_13_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_13_R2.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_13_R2.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_13_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_13_R2.PacketPlayOutPosition;
import net.minecraft.server.v1_13_R2.PacketPlayOutRespawn;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle.EnumTitleAction;
import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.persistence.PersistentContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.type.SkinDataType;
import net.sourcewriters.minecraft.vcompat.provider.data.wrap.SimpleSyntaxContainer;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.util.bukkit.Players;
import net.sourcewriters.minecraft.vcompat.util.minecraft.MojangProfileServer;
import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;
import net.sourcewriters.minecraft.vcompat.util.thread.PostAsync;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import net.minecraft.server.v1_13_R2.WorldServer;

public class Player1_13_R2 extends EntityLiving1_13_R2<EntityPlayer> implements NmsPlayer {

    private String realName;
    private Skin realSkin;

    private final WrappedContainer dataAdapter;

    public Player1_13_R2(Player player, PersistentContainer<?> container) {
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
        dataAdapter.set("skin", skin, SkinDataType.WRAPPED_INSTANCE);
    }

    @Override
    public Skin getSkin() {
        return dataAdapter.getOrDefault("skin", SkinDataType.WRAPPED_INSTANCE, realSkin);
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

        IChatBaseComponent headerComponent = header.isEmpty() ? null : CraftChatMessage.fromString(header, true)[0];
        IChatBaseComponent footerComponent = footer.isEmpty() ? null : CraftChatMessage.fromString(footer, true)[0];

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

        ArrayList<PacketPlayOutEntityEquipment> equipmentPackets = new ArrayList<>();
        for (EnumItemSlot slot : EnumItemSlot.values()) {
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
            Collections.emptySet(), 0);
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
            if (skin != null) {
                PropertyMap properties = profile.getProperties();
                properties.removeAll("textures");
                properties.put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
            }

            String name = getName();
            if (name != null) {
                VersionCompatProvider.get().getLookupProvider().getLookup("mjGameProfile").setFieldValue(profile, "name", name);
            }

            if (!(name == null && skin == null)) {
                fakeRespawn();
            }
        }
    }

}