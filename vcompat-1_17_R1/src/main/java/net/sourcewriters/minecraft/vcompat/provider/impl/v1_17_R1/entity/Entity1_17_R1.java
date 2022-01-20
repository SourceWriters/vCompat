package net.sourcewriters.minecraft.vcompat.provider.impl.v1_17_R1.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntity;
import net.sourcewriters.minecraft.vcompat.provider.utils.NmsBoundingBox;

public abstract class Entity1_17_R1<E extends Entity> implements NmsEntity {

    protected final E handle;

    protected final List<UUID> visible = Collections.synchronizedList(new ArrayList<>());

    public Entity1_17_R1(E handle) {
        this.handle = handle;
    }

    @Override
    public final E getHandle() {
        return handle;
    }

    @Override
    public int getId() {
        return handle.getId();
    }

    @Override
    public UUID getUniqueId() {
        return handle.getUUID();
    }

    @Override
    public NmsBoundingBox getBoundingBox() {
        AABB box = handle.getBoundingBox();
        return new NmsBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    @Override
    public void setCustomName(String name) {
        handle.setCustomName(CraftChatMessage.fromStringOrNull(name));
        updateVisibility();
    }

    @Override
    public String getCustomName() {
        return CraftChatMessage.fromComponent(handle.getCustomName());
    }

    @Override
    public void setGravity(boolean gravity) {
        handle.setNoGravity(!gravity);
    }

    @Override
    public boolean hasGravity() {
        return !handle.isNoGravity();
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        handle.setCustomNameVisible(visible);
    }

    @Override
    public boolean isCustomNameVisible() {
        return handle.isCustomNameVisible();
    }

    @Override
    public void setInvisible(boolean invisible) {
        handle.setInvisible(invisible);
    }

    @Override
    public boolean isInvisible() {
        return handle.isInvisible();
    }

    @Override
    public boolean isInteractable() {
        return handle.isPushable();
    }

    @Override
    public boolean isCollidable() {
        return handle.canBeCollidedWith();
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {
        handle.setInvulnerable(invulnerable);
    }

    @Override
    public boolean isInvulnerable() {
        return handle.isInvulnerable();
    }

    @Override
    public void setLocation(Location location) {
        handle.moveTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        if (location.getWorld() == null || handle.getCommandSenderWorld().getWorld() == location.getWorld()) {
            updateVisibility();
            return;
        }
        handle.level = ((CraftWorld) location.getWorld()).getHandle();
        updateVisibility();
    }

    @Override
    public Location getLocation() {
        Vec3 vector = handle.position();
        return new Location(handle.getCommandSenderWorld().getWorld(), vector.x, vector.y, vector.z);
    }

    @Override
    public void updateVisibility() {
        if (visible.isEmpty()) {
            return;
        }
        Player[] players;
        synchronized (visible) {
            players = visible.stream().map(Bukkit::getOfflinePlayer).filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer)
                .toArray(Player[]::new);
        }
        hide(players);
        show(players);
    }

    @Override
    public boolean isShown(Player player) {
        synchronized (visible) {
            return visible.contains(player.getUniqueId());
        }
    }

    @Override
    public void hide(Player... players) {
        if (players.length == 0) {
            return;
        }
        ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(handle.getId());
        for (Player player : players) {
            if (!isShown(player)) {
                continue;
            }
            ((CraftPlayer) player).getHandle().connection.send(packet);
            synchronized (visible) {
                visible.remove(player.getUniqueId());
            }
        }
    }

    @Override
    public void show(Player... players) {
        if (players.length == 0) {
            return;
        }
        ClientboundAddEntityPacket packet = new ClientboundAddEntityPacket(handle);
        ClientboundSetEntityDataPacket metadataPacket = new ClientboundSetEntityDataPacket(handle.getId(), handle.getEntityData(), true);
        ServerGamePacketListenerImpl connection;
        for (Player player : players) {
            if (isShown(player)) {
                continue;
            }
            connection = ((CraftPlayer) player).getHandle().connection;
            connection.send(packet);
            connection.send(metadataPacket);
            synchronized (visible) {
                visible.add(player.getUniqueId());
            }
        }
    }

    @Override
    public UUID[] getVisible() {
        synchronized (visible) {
            return visible.toArray(new UUID[0]);
        }
    }

    @Override
    public Player[] getVisibleAsPlayer() {
        synchronized (visible) {
            return visible.stream().map(Bukkit::getOfflinePlayer).filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer)
                .toArray(Player[]::new);
        }
    }

    @Override
    public void kill() {
        hide(getVisibleAsPlayer());
        handle.kill();
    }

}