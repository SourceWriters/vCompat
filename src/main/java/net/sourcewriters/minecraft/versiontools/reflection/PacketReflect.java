package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.setup.ReflectionProvider.DEFAULT;

import java.util.Collection;

import org.bukkit.entity.Player;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public class PacketReflect {

	public static void sendPacket(Object packet, Player player) {

		Object entityPlayer = DEFAULT.getOptionalReflect("cbCraftPlayer").get().run(player, "handle");
		Object connection = DEFAULT
			.getOptionalReflect("nmsEntityPlayer")
			.get()
			.getFieldValue("connection", entityPlayer);

		DEFAULT.getOptionalReflect("nmsPlayerConnection").get().execute(connection, "sendPacket", packet);

	}

	public static void sendPacket(Object packet, Player... players) {

		if (players.length == 0)
			return;

		if (players.length == 1) {
			sendPacket(packet, players[0]);
			return;
		}

		Reflect refCraftPlayer = DEFAULT.getOptionalReflect("cbCraftPlayer").get();
		Reflect refEntityPlayer = DEFAULT.getOptionalReflect("nmsEntityPlayer").get();
		Reflect refPlayerConnection = DEFAULT.getOptionalReflect("nmsPlayerConnection").get();

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

		Reflect refCraftPlayer = DEFAULT.getOptionalReflect("cbCraftPlayer").get();
		Reflect refEntityPlayer = DEFAULT.getOptionalReflect("nmsEntityPlayer").get();
		Reflect refPlayerConnection = DEFAULT.getOptionalReflect("nmsPlayerConnection").get();

		for (Player player : players) {
			Object entityPlayer = refCraftPlayer.run(player, "handle");
			Object connection = refEntityPlayer.getFieldValue("connection", entityPlayer);
			refPlayerConnection.execute(connection, "sendPacket", packet);
		}

	}

}
