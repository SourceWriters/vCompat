package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.util.Optional;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class MessageTools {

	public Object[] toComponents(String text) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");

		if (checkPresence(optional0))
			return new Object[0];
		
		return (Object[]) optional0.get().run("fromString0", new Object[] { text });

	}

	public Object[] toComponents(String text, boolean keepNewLines) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");

		if (checkPresence(optional0))
			return new Object[0];

		return (Object[]) optional0.get().run("fromString1", new Object[] { text, keepNewLines });

	}

	public String fromComponent(Object... components) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");

		if (checkPresence(optional0))
			return "";

		return (String) optional0.get().run("toString", new Object[] { components });

	}

}
