package net.sourcewriters.minecraft.versiontools.reflection.utils;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.reflection.ClassCache;
import com.syntaxphoenix.syntaxapi.utils.java.Arrays;

public abstract class Tracker {

	public static Optional<Class<?>> getClassFromStack(int offset) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2 + offset];
		return element == null ? Optional.empty() : ClassCache.getOptionalClass(element.getClassName());
	}

	public static Optional<Class<?>> getCallerClass() {
		return getClassFromStack(1);
	}

	public static Optional<Plugin> getCallerPlugin() {
		StackTraceElement[] elements = Arrays.subArray(StackTraceElement[]::new, Thread.currentThread().getStackTrace(), 2);
		for (StackTraceElement element : elements) {
			Optional<Plugin> plugin = getPlugin(ClassCache.getOptionalClass(element.getClassName()));
			if (plugin.isPresent()) {
				return plugin;
			}
		}
		return Optional.empty();
	}

	public static Optional<Plugin> getPlugin(Optional<Class<?>> option) {
		if (!option.isPresent()) {
			return Optional.empty();
		}
		Class<?> clazz = option.get();
		return java.util.Arrays.stream(Bukkit.getPluginManager().getPlugins()).filter(plugin -> plugin.getClass().getClassLoader() == clazz.getClassLoader())
			.findFirst();
	}

}
