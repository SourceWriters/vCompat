package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1.entity;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Pair;

import net.minecraft.SystemUtils;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.network.protocol.game.PacketPlayOutPosition;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.BiomeManager;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrapType;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.reflection.data.type.SkinDataType;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1.data.SyntaxContainer1_17_R1;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.vcompat.utils.bukkit.Players;
import net.sourcewriters.minecraft.vcompat.utils.minecraft.MojangProfileServer;
import net.sourcewriters.minecraft.vcompat.utils.minecraft.Skin;
import net.sourcewriters.minecraft.vcompat.utils.thread.PostAsync;

public class Player1_17_R1 extends EntityLiving1_17_R1<EntityPlayer> implements NmsPlayer {

    private String realName;
    private Skin realSkin;

    private final WrappedContainer dataAdapter;

    public Player1_17_R1(Player player) {
        super(((CraftPlayer) player).getHandle());
        dataAdapter = new SyntaxContainer1_17_R1(getBukkitPlayer().getPersistentDataContainer());
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
        if (skin == null) {
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
        return handle.e;
    }

    @Override
    public void setPlayerListHeaderAndFooter(String header, String footer) {
        dataAdapter.set("header", header, WrapType.STRING);
        dataAdapter.set("footer", footer, WrapType.STRING);
        sendPlayerListInfo(header, footer);
    }

    private final void sendPlayerListInfo(String header, String footer) {
        if (handle.b.isDisconnected()) {
            return;
        }

        IChatBaseComponent headerComponent = header.isEmpty() ? null : CraftChatMessage.fromStringOrNull(header, true);
        IChatBaseComponent footerComponent = footer.isEmpty() ? null : CraftChatMessage.fromStringOrNull(footer, true);

        handle.b.sendPacket(new PacketPlayOutPlayerListHeaderFooter(headerComponent, footerComponent));
    }

    @Override
    public void setTitleTimes(int fadeIn, int stay, int fadeOut) {
        if (handle.b.isDisconnected()) {
            return;
        }
        handle.b.sendPacket(new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut));
    }

    @Override
    public void sendSubtitle(String text) {
        if (handle.b.isDisconnected()) {
            return;
        }
        handle.b.sendPacket(new ClientboundSetSubtitleTextPacket(CraftChatMessage.fromStringOrNull(text)));
    }

    @Override
    public void sendTitle(String text) {
        if (handle.b.isDisconnected()) {
            return;
        }
        handle.b.sendPacket(new ClientboundSetTitleTextPacket(CraftChatMessage.fromStringOrNull(text)));
    }

    @Override
    public void sendActionBar(String text) {
        if (handle.b.isDisconnected()) {
            return;
        }
        handle.b.sendPacket(new PacketPlayOutChat(CraftChatMessage.fromStringOrNull(text), ChatMessageType.c, SystemUtils.b));
    }

    @Override
    public void fakeRespawn() {
        if (handle.b.isDisconnected()) {
            return;
        }
        PacketPlayOutPlayerInfo remInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.e, handle);
        PacketPlayOutPlayerInfo addInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.a, handle);

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
            PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
            connection.sendPacket(remInfoPacket);
            connection.sendPacket(addInfoPacket);
            connection.sendPacket(destroyPacket);
            connection.sendPacket(spawnPacket);
            connection.sendPacket(rotationPacket);
            connection.sendPacket(equipmentPacket);
        }

        WorldServer world = (WorldServer) handle.t;

        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(world.getDimensionManager(), world.getDimensionKey(),
            BiomeManager.a(world.getSeed()), handle.d.getGameMode(), handle.d.c(),
            world.isDebugWorld(), world.isFlatWorld(), true);
        PacketPlayOutPosition positionPacket = new PacketPlayOutPosition(handle.locX(), handle.locY(), handle.locZ(), handle.getHeadRotation(),
            handle.getXRot(), Collections.emptySet(), 0, false);
        PacketPlayOutHeldItemSlot itemPacket = new PacketPlayOutHeldItemSlot(handle.getInventory().k);
        PacketPlayOutEntityStatus statusPacket = new PacketPlayOutEntityStatus(handle, (byte) 28);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(handle.getId(), handle.getDataWatcher(), true);

        PlayerConnection connection = handle.b;
        connection.sendPacket(remInfoPacket);
        connection.sendPacket(addInfoPacket);
        connection.sendPacket(respawnPacket);
        connection.sendPacket(positionPacket);
        connection.sendPacket(itemPacket);
        connection.sendPacket(statusPacket);
        connection.sendPacket(metadataPacket);

        handle.updateAbilities();
        handle.triggerHealthUpdate();
        handle.bU.updateInventory();
        if (handle.bV != handle.bU) {
            handle.bV.updateInventory();
        }
        self.recalculatePermissions();
    }

    @Override
    public void respawn() {
        if (handle.b.isDisconnected()) {
            return;
        }
        handle.b.sendPacket(new PacketPlayInClientCommand(EnumClientCommand.a));
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
                ReflectionProvider.DEFAULT.getReflect("mjGameProfile").setFieldValue(profile, "name", name);
            }

            if (!(name == null && skin == null)) {
                fakeRespawn();
            }
        }
    }

}