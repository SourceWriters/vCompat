package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.version.Versions.*;
import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.util.Optional;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class EntityTools {

	public static void setCustomName(Object entity, String name) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0))
			return;

		Object value = minor(minor -> minor <= 12) ? (Object) MessageTools.color(name)
			: MessageTools.toComponents(name);

		optional0.get().execute(entity, "setCustomName", value);

	}

	public static void setGravity(Object entity, boolean gravity) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0))
			return;

		boolean value = minor(minor -> minor <= 9) ? gravity : !gravity;

		optional0.get().execute(entity, "setGravity", value);

	}

	public static void setCustomNameVisible(Object entity, boolean visible) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0))
			return;

		optional0.get().execute(entity, "setCustomNameVisible", visible);

	}

	public static void setInvisible(Object entity, boolean invisible) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0))
			return;

		optional0.get().execute(entity, "setInvisible", invisible);

	}

	public static void setInvulnerable(Object entity, boolean invulnerable) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");

		if (!checkPresence(optional0))
			return;

		optional0.get().execute(entity, "setInvulnerable", invulnerable);

	}

	public static void kill(Object entity) {
		
		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntity");
		
		if(!checkPresence(optional0))
			return;
		
		
		
	}

}
