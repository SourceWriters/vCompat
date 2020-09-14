package net.sourcewriters.minecraft.versiontools.setup;

import java.util.function.Consumer;

public abstract class Reflections {

	public static void globalSetup(ReflectionProvider provider) {

	}

	public abstract void setup(ReflectionProvider provider);

	public static <T> T predicate(boolean condition, T value, Consumer<T> action) {
		if (condition)
			action.accept(value);
		return value;
	}

}
