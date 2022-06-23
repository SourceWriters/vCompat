package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.entity;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Pair;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.BiomeManager;
import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.type.SkinDataType;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.data.SyntaxContainer1_18_R2;
import net.sourcewriters.minecraft.vcompat.util.bukkit.Players;
import net.sourcewriters.minecraft.vcompat.util.minecraft.MojangProfileServer;
import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;
import net.sourcewriters.minecraft.vcompat.util.thread.PostAsync;

public class Player1_18_R2 extends EntityLiving1_18_R2<ServerPlayer> implements NmsPlayer {

    private String realName;
    private Skin realSkin;

    private final WrappedContainer dataAdapter;

    public Player1_18_R2(Player player) {
        super(((CraftPlayer) player).getHandle());
        dataAdapter = new SyntaxContainer1_18_R2(getBukkitPlayer().getPersistentDataContainer());
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
        return handle.latency;
    }

    @Override
    public void setPlayerListHeaderAndFooter(String header, String footer) {
        dataAdapter.set("header", header, WrapType.STRING);
        dataAdapter.set("footer", footer, WrapType.STRING);
        sendPlayerListInfo(header, footer);
    }

    private final void sendPlayerListInfo(String header, String footer) {
        if (handle.hasDisconnected()) {
            return;
        }

        Component headerComponent = header.isEmpty() ? null : CraftChatMessage.fromStringOrNull(header, true);
        Component footerComponent = footer.isEmpty() ? null : CraftChatMessage.fromStringOrNull(footer, true);

        handle.connection.send(new ClientboundTabListPacket(headerComponent, footerComponent));
    }

    @Override
    public void setTitleTimes(int fadeIn, int stay, int fadeOut) {
        if (handle.hasDisconnected()) {
            return;
        }
        handle.connection.send(new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut));
    }

    @Override
    public void sendSubtitle(String text) {
        if (handle.hasDisconnected()) {
            return;
        }
        handle.connection.send(new ClientboundSetSubtitleTextPacket(CraftChatMessage.fromStringOrNull(text)));
    }

    @Override
    public void sendTitle(String text) {
        if (handle.hasDisconnected()) {
            return;
        }
        handle.connection.send(new ClientboundSetTitleTextPacket(CraftChatMessage.fromStringOrNull(text)));
    }

    @Override
    public void sendActionBar(String text) {
        if (handle.hasDisconnected()) {
            return;
        }
        handle.connection.send(new ClientboundSetActionBarTextPacket(CraftChatMessage.fromStringOrNull(text)));
    }

    @Override
    public void fakeRespawn() {
        if (handle.hasDisconnected()) {
            return;
        }
        ClientboundPlayerInfoPacket remInfoPacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER,
            handle);
        ClientboundPlayerInfoPacket addInfoPacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, handle);

        ClientboundRemoveEntitiesPacket destroyPacket = new ClientboundRemoveEntitiesPacket(handle.getId());
        ClientboundAddPlayerPacket spawnPacket = new ClientboundAddPlayerPacket(handle);
        ClientboundRotateHeadPacket rotationPacket = new ClientboundRotateHeadPacket(handle,
            (byte) Mth.floor(handle.getYHeadRot() * 256F / 360F));

        ArrayList<Pair<EquipmentSlot, ItemStack>> list = new ArrayList<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            list.add(Pair.of(slot, handle.getItemBySlot(slot)));
        }
        ClientboundSetEquipmentPacket equipmentPacket = new ClientboundSetEquipmentPacket(handle.getId(), list);

        Player self = getBukkitPlayer();
        Player[] players = Players.getOnlineWithout(getUniqueId());
        for (Player player : players) {
            if (!player.canSee(self)) {
                continue;
            }
            ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
            connection.send(remInfoPacket);
            connection.send(addInfoPacket);
            connection.send(destroyPacket);
            connection.send(spawnPacket);
            connection.send(rotationPacket);
            connection.send(equipmentPacket);
        }

        ServerLevel world = (ServerLevel) handle.level;

        ClientboundRespawnPacket respawnPacket = new ClientboundRespawnPacket(world.dimensionTypeRegistration(), world.dimension(),
            BiomeManager.obfuscateSeed(world.getSeed()), handle.gameMode.getGameModeForPlayer(),
            handle.gameMode.getPreviousGameModeForPlayer(), world.isDebug(), world.isFlat(), true);
        ClientboundPlayerPositionPacket positionPacket = new ClientboundPlayerPositionPacket(handle.getX(), handle.getY(), handle.getZ(),
            handle.xRotO, handle.yRotO, Collections.emptySet(), 0, false);
        ClientboundSetCarriedItemPacket itemPacket = new ClientboundSetCarriedItemPacket(handle.getInventory().selected);
        ClientboundEntityEventPacket statusPacket = new ClientboundEntityEventPacket(handle, (byte) 28);
        ClientboundSetEntityDataPacket metadataPacket = new ClientboundSetEntityDataPacket(handle.getId(), handle.getEntityData(), true);

        ServerGamePacketListenerImpl connection = handle.connection;
        connection.send(remInfoPacket);
        connection.send(addInfoPacket);
        connection.send(respawnPacket);
        connection.send(positionPacket);
        connection.send(itemPacket);
        connection.send(statusPacket);
        connection.send(metadataPacket);

        handle.onUpdateAbilities();
        handle.resetSentInfo();
        handle.inventoryMenu.broadcastChanges();
        handle.inventoryMenu.sendAllDataToRemote();
        if (handle.containerMenu != handle.inventoryMenu) {
            handle.containerMenu.broadcastChanges();
            handle.containerMenu.sendAllDataToRemote();
        }
        self.recalculatePermissions();
    }

    @Override
    public void respawn() {
        if (handle.connection.isDisconnected()) {
            return;
        }
        handle.connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
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
            GameProfile profile = handle.getGameProfile();

            Skin skin = getSkin();
            if (skin != null) {
                PropertyMap properties = profile.getProperties();
                properties.removeAll("textures");
                properties.put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
            }

<<<<<<< HEAD
            String name = dataAdapter.get("name", WrapType.STRING);
            if (name != null && !name.isBlank()) {
=======
            String name = getName();
            if (name != null) {
>>>>>>> ef8c634 (Rebase (#41))
                VersionCompatProvider.get().getLookupProvider().getLookup("mjGameProfile").setFieldValue(profile, "name", name);
            }

            if (!(name == null && skin == null)) {
                fakeRespawn();
            }
        }
    }

}