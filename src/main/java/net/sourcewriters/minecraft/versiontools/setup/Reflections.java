package net.sourcewriters.minecraft.versiontools.setup;

import java.util.function.Consumer;

import net.sourcewriters.minecraft.versiontools.setup.reflections.CraftbukkitReflections;
import net.sourcewriters.minecraft.versiontools.setup.reflections.MinecraftReflections;

public abstract class Reflections {

	public static void globalSetup(ReflectionProvider provider) {
		// TODO: Switch to reflections
		new MinecraftReflections().setup(provider);
		new CraftbukkitReflections().setup(provider);
	}

	public abstract void setup(ReflectionProvider provider);

	public static <T> T predicate(boolean condition, T value, Consumer<T> action) {
		if (condition)
			action.accept(value);
		return value;
	}
	
	public int priority() {
		return 0;
	}

}
