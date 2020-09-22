package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.lang.reflect.Array;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class MessageTools {

	public static final char REPLACEMENT_CHAR = '\u0026';
	public static final char COLOR_CHAR = '\u00A7';

	public static Object toComponents(String text) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");

		if (checkPresence(optional0))
			return null;

		return Array.get(optional0.get().run("fromString0", (Object) color(text)), 0);

	}

	public static Object toComponents(String text, boolean keepNewLines) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");

		if (checkPresence(optional0))
			return null;

		return Array.get(optional0.get().run("fromString1", (Object) color(text), (Object) keepNewLines), 0);

	}

	public static String fromComponent(Object... components) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");

		if (checkPresence(optional0))
			return null;

		return uncolor((String) optional0.get().run("toString", (Object) components));

	}

	public static String color(String text) {
		return text.replace(REPLACEMENT_CHAR, COLOR_CHAR);
	}

	public static String uncolor(String text) {
		return text.replace(COLOR_CHAR, REPLACEMENT_CHAR);
	}

}
