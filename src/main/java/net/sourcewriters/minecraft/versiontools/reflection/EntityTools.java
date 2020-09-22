package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.version.Versions.*;
import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.util.Optional;

import org.bukkit.Location;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class EntityTools {

	public static void setCustomName(Object entity, String name) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return;
		}

		Object value = minor(minor -> minor <= 12) ? (Object) MessageTools.color(name)
			: MessageTools.toComponents(name);

		optional0.get().execute(entity, "setCustomName", value);

	}

	public static void setGravity(Object entity, boolean gravity) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return;
		}

		boolean value = minor(minor -> minor <= 9) ? gravity : !gravity;

		optional0.get().execute(entity, "setGravity", value);

	}

	public static void setCustomNameVisible(Object entity, boolean visible) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return;
		}

		optional0.get().execute(entity, "setCustomNameVisible", visible);

	}

	public static void setInvisible(Object entity, boolean invisible) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return;
		}

		optional0.get().execute(entity, "setInvisible", invisible);

	}

	public static void setInvulnerable(Object entity, boolean invulnerable) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return;
		}

		optional0.get().execute(entity, "setInvulnerable", invulnerable);

	}

	public static void setPosition(Object entity, Location location) {
		setPosition(entity, location.getX(), location.getY(), location.getZ());
	}

	public static void setPosition(Object entity, double x, double y, double z) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return;
		}

		optional0.get().execute(entity, "setPosition", x, y, z);

	}

	public static int getId(Object entity) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return -1;
		}

		int id = (int) optional0.get().run(entity, "getId");

		return id;

	}

	public static Object teleport(Object entity, Location location) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return null;
		}

		Object teleported = optional0.get().run(entity, "teleportTo", location, false);

		return teleported;

	}

	public static void kill(Object entity) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0)) {
			return;
		}

		// TODO: Set entity to dead

	}

}
