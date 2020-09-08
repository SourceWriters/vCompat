package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.setup.MinecraftReflect.CACHE;

import java.util.Collection;

import org.bukkit.entity.Player;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public class PacketReflect {

	public static void sendPacket(Object packet, Player player) {

		Object entityPlayer = CACHE.get("cbCraftPlayer").get().run(player, "handle");
		Object connection = CACHE.get("nmsEntityPlayer").get().getFieldValue("connection", entityPlayer);

		CACHE.get("nmsPlayerConnection").get().execute(connection, "sendPacket", packet);

	}

	public static void sendPacket(Object packet, Player... players) {

		if (players.length == 0)
			return;

		if (players.length == 1) {
			sendPacket(packet, players[0]);
			return;
		}

		Reflect refCraftPlayer = CACHE.get("cbCraftPlayer").get();
		Reflect refEntityPlayer = CACHE.get("nmsEntityPlayer").get();
		Reflect refPlayerConnection = CACHE.get("nmsPlayerConnection").get();

		for (Player player : players) {
			Object entityPlayer = refCraftPlayer.run(player, "handle");
			Object connection = refEntityPlayer.getFieldValue("connection", entityPlayer);
			refPlayerConnection.execute(connection, "sendPacket", packet);
		}

	}

	public static void sendPacket(Object packet, Collection<Player> players) {

		if (players.size() == 0)
			return;

		if (players.size() == 1) {
			sendPacket(packet, players.iterator().next());
			return;
		}

		Reflect refCraftPlayer = CACHE.get("cbCraftPlayer").get();
		Reflect refEntityPlayer = CACHE.get("nmsEntityPlayer").get();
		Reflect refPlayerConnection = CACHE.get("nmsPlayerConnection").get();

		for (Player player : players) {
			Object entityPlayer = refCraftPlayer.run(player, "handle");
			Object connection = refEntityPlayer.getFieldValue("connection", entityPlayer);
			refPlayerConnection.execute(connection, "sendPacket", packet);
		}

	}

}
