package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.util.Collection;
import java.util.Optional;

import org.bukkit.entity.Player;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class PacketTools {

	public static void sendPacket(Object packet, Player player) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntityPlayer");
		Optional<Reflect> optional2 = DEFAULT.getOptionalReflect("nmsPlayerConnection");

		if (checkPresence(optional0, optional1, optional2))
			return;

		Object entityPlayer = optional0.get().run(player, "handle");
		Object connection = optional1.get().getFieldValue("connection", entityPlayer);

		optional2.get().execute(connection, "sendPacket", packet);
		
	}

	public static void sendPacket(Object packet, Player... players) {

		if (players.length == 0)
			return;

		if (players.length == 1) {
			sendPacket(packet, players[0]);
			return;
		}

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntityPlayer");
		Optional<Reflect> optional2 = DEFAULT.getOptionalReflect("nmsPlayerConnection");

		if (checkPresence(optional0, optional1, optional2))
			return;

		Reflect refCraftPlayer = optional0.get();
		Reflect refEntityPlayer = optional1.get();
		Reflect refPlayerConnection = optional2.get();

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

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntityPlayer");
		Optional<Reflect> optional2 = DEFAULT.getOptionalReflect("nmsPlayerConnection");

		if (checkPresence(optional0, optional1, optional2))
			return;

		Reflect refCraftPlayer = optional0.get();
		Reflect refEntityPlayer = optional1.get();
		Reflect refPlayerConnection = optional2.get();

		for (Player player : players) {
			Object entityPlayer = refCraftPlayer.run(player, "handle");
			Object connection = refEntityPlayer.getFieldValue("connection", entityPlayer);
			refPlayerConnection.execute(connection, "sendPacket", packet);
		}

	}

}
