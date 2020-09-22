package net.sourcewriters.minecraft.versiontools.reflection.entity;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.util.Optional;

import org.bukkit.World;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class ArmorStandTools {

	public static void setSmall(Object entity, boolean small) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntityArmorStand");

		if (!checkPresence(optional0)) {
			return;
		}

		optional0.get().execute(entity, "setSmall", small);

	}

	public static Object createArmorStand(World world) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEntityArmorStand");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("cbCraftWorld");

		if (!checkPresence(optional0, optional1)) {
			return null;
		}

		Object nmsWorld = optional1.get().run(world, "handle");

		Object entity = optional0.get().init("construct", nmsWorld);

		return entity;

	}

}
