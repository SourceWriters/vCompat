package net.sourcewriters.minecraft.versiontools.reflection.reflect;

import java.util.function.Consumer;

import net.sourcewriters.minecraft.versiontools.reflection.reflect.provider.*;

public abstract class Reflections {

	public static void globalSetup(ReflectionProvider provider) {
		new GeneralReflections().setup(provider);
	}

	public abstract void setup(ReflectionProvider provider);

	public static <T> T predicate(boolean condition, T value, Consumer<T> action) {
		if (condition) {
			action.accept(value);
		}
		return value;
	}

	public int priority() {
		return 0;
	}

}
