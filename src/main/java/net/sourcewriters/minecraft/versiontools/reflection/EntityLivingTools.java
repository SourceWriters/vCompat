package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;
import static net.sourcewriters.minecraft.versiontools.reflection.PacketTools.sendPacket;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.entity.Player;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class EntityLivingTools {

	public static void hideFor(Object entity, Player... players) {
		hideAllFor(new int[] { EntityTools.getId(entity) }, players);
	}

	public static void hideAllFor(Object[] entities, Player... players) {
		hideAllFor(Arrays.stream(entities).mapToInt(EntityTools::getId).toArray(), players);
	}

	private static void hideAllFor(int[] ids, Player[] players) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsPacketPlayOutEntityDestroy");

		if (!checkPresence(optional0)) {
			return;
		}

		Object packet = optional0.get().init("construct", (Object) ids);

		sendPacket(packet, players);

	}

	public static void showFor(Object entity, Player... players) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsPacketPlayOutSpawnEntityLiving");

		if (!checkPresence(optional0)) {
			return;
		}

		Object packet = optional0.get().init("construct", entity);

		sendPacket(packet, players);

	}

	public static void showAllFor(Object[] entities, Player... players) {
		for (Object entity : entities) {
			showFor(entity, players);
		}
	}

}
