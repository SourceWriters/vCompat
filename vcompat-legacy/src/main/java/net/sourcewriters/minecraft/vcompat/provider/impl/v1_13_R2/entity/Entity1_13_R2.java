package net.sourcewriters.minecraft.vcompat.provider.impl.v1_13_R2.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_13_R2.AxisAlignedBB;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_13_R2.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import net.minecraft.server.v1_13_R2.Vec3D;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntity;
import net.sourcewriters.minecraft.vcompat.reflection.utils.NmsBoundingBox;

public abstract class Entity1_13_R2<E extends Entity> implements NmsEntity {

    protected final E handle;

    protected final List<UUID> visible = Collections.synchronizedList(new ArrayList<>());

    public Entity1_13_R2(E handle) {
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
        return handle.getUniqueID();
    }

    @Override
    public NmsBoundingBox getBoundingBox() {
        AxisAlignedBB box = handle.getBoundingBox();
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
        return handle.getCustomNameVisible();
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
        return handle.isInteractable();
    }

    @Override
    public boolean isCollidable() {
        return handle.isCollidable();
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {
        handle.setInvulnerable(invulnerable);
    }

    @Override
    public boolean isInvulnerable() {
        return handle.bl();
    }

    @Override
    public void setLocation(Location location) {
        handle.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        if (location.getWorld() == null || handle.getWorld().getWorld() == location.getWorld()) {
            updateVisibility();
            return;
        }
        handle.world = ((CraftWorld) location.getWorld()).getHandle();
        updateVisibility();
    }

    @Override
    public Location getLocation() {
        Vec3D vector = handle.bI();
        return new Location(handle.getWorld().getWorld(), vector.x, vector.y, vector.z);
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
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(handle.getId());
        for (Player player : players) {
            if (!isShown(player)) {
                continue;
            }
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
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
        PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(handle, IRegistry.ENTITY_TYPE.a(handle.P()));
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(handle.getId(), handle.getDataWatcher(), true);
        PlayerConnection connection;
        for (Player player : players) {
            if (isShown(player)) {
                continue;
            }
            connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(packet);
            connection.sendPacket(metadataPacket);
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
        handle.die();
    }

}