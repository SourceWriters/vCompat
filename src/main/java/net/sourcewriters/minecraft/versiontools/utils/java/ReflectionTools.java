package net.sourcewriters.minecraft.versiontools.utils.java;

import java.lang.reflect.Array;

public abstract class ReflectionTools {

	public static Class<?> subclass(Class<?> clazz, String name) {
		for (Class<?> search : clazz.getClasses()) {
			if (search.getSimpleName().split("\\.")[0].equals(name)) {
				return search;
			}
		}
		return null;
	}

	public static Class<?> arrayclass(Class<?> clazz) {
		return Array.newInstance(clazz, 1).getClass();
	}

}
