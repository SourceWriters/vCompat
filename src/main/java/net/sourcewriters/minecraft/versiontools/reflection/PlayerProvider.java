package net.sourcewriters.minecraft.versiontools.reflection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsPlayer;

public abstract class PlayerProvider<V extends VersionControl> extends VersionHandler<V> {

	protected final Map<UUID, NmsPlayer> players = Collections.synchronizedMap(new HashMap<>());

	protected PlayerProvider(V versionControl) {
		super(versionControl);
	}

	public NmsPlayer getPlayer(UUID uniqueId) {
		Player player = Bukkit.getPlayer(uniqueId);
		if (player == null) {
			return players.get(uniqueId);
		}
		return getPlayer(player);
	}

	public NmsPlayer getPlayer(Player player) {
		if (players.containsKey(player.getUniqueId())) {
			return players.get(player.getUniqueId());
		}
		NmsPlayer nmsPlayer = createPlayer(player);
		players.put(player.getUniqueId(), nmsPlayer);
		return nmsPlayer;
	}

	protected abstract NmsPlayer createPlayer(Player player);

}