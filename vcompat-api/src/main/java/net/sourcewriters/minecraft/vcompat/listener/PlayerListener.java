package net.sourcewriters.minecraft.vcompat.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.listener.handler.IPlayerHandler;
import net.sourcewriters.minecraft.vcompat.reflection.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsPlayer;

public final class PlayerListener implements Listener {

    public static final PlayerListener INSTANCE = new PlayerListener();

    public static void register(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(INSTANCE, plugin);
    }

    public static void registerHandler(IPlayerHandler handler) {
        INSTANCE.register(handler);
    }

    public static void unregisterHandler(IPlayerHandler handler) {
        INSTANCE.unregister(handler);
    }

    private final PlayerProvider<?> provider = VersionCompatProvider.get().getControl().getPlayerProvider();

    private final List<IPlayerHandler> handlers = Collections.synchronizedList(new ArrayList<>());
    private final Set<UUID> set = Collections.synchronizedSet(new HashSet<>());

    public void register(IPlayerHandler handler) {
        if (handlers.contains(handler)) {
            return;
        }
        handlers.add(handler);
    }

    public void unregister(IPlayerHandler handler) {
        if (!handlers.contains(handler)) {
            return;
        }
        handlers.remove(handler);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (set.contains(player.getUniqueId())) {
            return;
        }
        set.add(player.getUniqueId());
        NmsPlayer nmsPlayer = provider.getPlayer(player);
        for (IPlayerHandler handler : handlers) {
            handler.onJoin(nmsPlayer);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!set.contains(player.getUniqueId())) {
            return;
        }
        set.remove(player.getUniqueId());
        NmsPlayer nmsPlayer = provider.getPlayer(player);
        for (IPlayerHandler handler : handlers) {
            handler.onLeave(nmsPlayer);
        }
    }

}
