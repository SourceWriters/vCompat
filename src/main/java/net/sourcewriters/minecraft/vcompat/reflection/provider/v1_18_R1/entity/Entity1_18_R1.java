package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntity;
import net.sourcewriters.minecraft.vcompat.reflection.utils.NmsBoundingBox;

public abstract class Entity1_18_R1<E extends Entity> implements NmsEntity {

    protected final E handle;

    protected final List<UUID> visible = Collections.synchronizedList(new ArrayList<>());

    public Entity1_18_R1(E handle) {
        this.handle = handle;
    }

    @Override
    public final E getHandle() {
        return handle;
    }

    @Override
    public int getId() {
        return handle.ae();
    }

    @Override
    public UUID getUniqueId() {
        return handle.cm();
    }

    @Override
    public NmsBoundingBox getBoundingBox() {
        AxisAlignedBB box = handle.cw();
        return new NmsBoundingBox(box.a, box.b, box.c, box.d, box.e, box.f);
    }

    @Override
    public void setCustomName(String name) {
        handle.a(CraftChatMessage.fromStringOrNull(name));
        updateVisibility();
    }

    @Override
    public String getCustomName() {
        return CraftChatMessage.fromComponent(handle.Z());
    }

    @Override
    public void setGravity(boolean gravity) {
        handle.e(!gravity);
    }

    @Override
    public boolean hasGravity() {
        return !handle.aM();
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        handle.n(visible);
    }

    @Override
    public boolean isCustomNameVisible() {
        return handle.cr();
    }

    @Override
    public void setInvisible(boolean invisible) {
        handle.j(invisible);
    }

    @Override
    public boolean isInvisible() {
        return handle.bU();
    }

    @Override
    public boolean isInteractable() {
        return handle.cf();
    }

    @Override
    public boolean isCollidable() {
        return handle.bn();
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {
        handle.m(invulnerable);
    }

    @Override
    public boolean isInvulnerable() {
        return handle.cg();
    }

    @Override
    public void setLocation(Location location) {
        handle.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        if (location.getWorld() == null || handle.W().getWorld() == location.getWorld()) {
            updateVisibility();
            return;
        }
        handle.t = ((CraftWorld) location.getWorld()).getHandle();
        updateVisibility();
    }

    @Override
    public Location getLocation() {
        Vec3D vector = handle.cV();
        return new Location(handle.W().getWorld(), vector.b, vector.c, vector.d);
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
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(handle.ae());
        for (Player player : players) {
            if (!isShown(player)) {
                continue;
            }
            ((CraftPlayer) player).getHandle().b.a(packet);
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
        PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(handle);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(handle.ae(), handle.ai(), true);
        PlayerConnection connection;
        for (Player player : players) {
            if (isShown(player)) {
                continue;
            }
            connection = ((CraftPlayer) player).getHandle().b;
            connection.a(packet);
            connection.a(metadataPacket);
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
        handle.ag();
    }

}