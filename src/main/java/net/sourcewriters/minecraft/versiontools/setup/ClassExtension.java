package net.sourcewriters.minecraft.versiontools.setup;

import static net.sourcewriters.minecraft.versiontools.version.Versions.*;

import com.syntaxphoenix.syntaxapi.reflection.ClassCache;

public class ClassExtension {

	public static Class<?> getMinecraftClass(String name) {
		return getClass("net.minecraft.server." + getServerAsString() + "." + name);
	}

	public static Class<?> getCraftbukkitClass(String name) {
		return getClass("org.bukkit.craftbukkit." + getServerAsString() + "." + name);
	}

	public static Class<?> getBukkitClass(String name) {
		return getClass("org.bukkit." + name);
	}

	public static Class<?> getClass(String path) {
		return ClassCache.getClass(path);
	}

	/*
	 * 
	 */

	public static String patch(String name) {
		return name + getServer().getMinor();
	}

	/*
	 * 
	 */

	public static boolean major(int major) {
		return getServer().getMajor() == major;
	}

	public static boolean minor(int minor) {
		return getServer().getMinor() == minor;
	}

	public static boolean patch(int patch) {
		return getServer().getPatch() == patch;
	}

	public static boolean refaction(int refaction) {
		return getServer().getRefaction() == refaction;
	}

	/*
	 * 
	 */

	public static boolean major(int... major) {
		int server = getServer().getMajor();
		for (int current : major)
			if (server == current)
				return true;
		return false;
	}

	public static boolean minor(int... minor) {
		int server = getServer().getMinor();
		for (int current : minor)
			if (server == current)
				return true;
		return false;
	}

	public static boolean patch(int... patch) {
		int server = getServer().getPatch();
		for (int current : patch)
			if (server == current)
				return true;
		return false;
	}

	public static boolean refaction(int... refaction) {
		int server = getServer().getRefaction();
		for (int current : refaction)
			if (server == current)
				return true;
		return false;
	}

}
