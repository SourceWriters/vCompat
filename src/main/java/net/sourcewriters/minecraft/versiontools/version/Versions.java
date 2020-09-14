package net.sourcewriters.minecraft.versiontools.version;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.bukkit.Bukkit;

import com.syntaxphoenix.syntaxapi.version.DefaultVersion;
import com.syntaxphoenix.syntaxapi.version.Version;

import net.sourcewriters.minecraft.versiontools.utils.java.function.TriPredicate;

public abstract class Versions {

	private static Version MINECRAFT_VERSION;
	private static String MINECRAFT_VERSION_STRING;

	private static ServerVersion SERVER_VERSION;
	private static String SERVER_VERSION_STRING;

	/*
	 * Minecraft
	 */

	public static Version getMinecraft() {
		return MINECRAFT_VERSION != null ? MINECRAFT_VERSION
			: (MINECRAFT_VERSION = new DefaultVersion()
				.getAnalyzer()
				.analyze(Bukkit.getVersion().split(" ")[2].replace(")", "")));
	}

	public static String getMinecraftAsString() {
		return MINECRAFT_VERSION_STRING != null ? MINECRAFT_VERSION_STRING
			: (MINECRAFT_VERSION_STRING = getMinecraft().toString());
	}

	public static String patchMinecraft(String input) {
		return input + getServerAsString();
	}

	/*
	 * Server Version
	 */

	public static ServerVersion getServer() {
		return SERVER_VERSION != null ? SERVER_VERSION
			: (SERVER_VERSION = ServerVersion.ANALYZER
				.analyze(Bukkit.getServer().getClass().getPackage().getName().split("\\.", 4)[3]));
	}

	public static String getServerAsString() {
		return SERVER_VERSION_STRING != null ? SERVER_VERSION_STRING : (SERVER_VERSION_STRING = getServer().toString());
	}

	public static String patchServer(String input) {
		return input + getServerAsString();
	}

	//
	// Predicates

	public static boolean version(int major, int minor) {
		return major(major) && minor(minor);
	}

	public static boolean version(int major, int minor, int refaction) {
		return major(major) && minor(minor) && refaction(refaction);
	}

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

	public static boolean refaction(int... refaction) {
		int server = getServer().getRefaction();
		for (int current : refaction)
			if (server == current)
				return true;
		return false;
	}

	public static boolean version(TriPredicate<Integer, Integer, Integer> predicate) {
		ServerVersion version = getServer();
		return predicate.test(version.getMajor(), version.getMinor(), version.getRefaction());
	}

	public static boolean version(BiPredicate<Integer, Integer> predicate) {
		ServerVersion version = getServer();
		return predicate.test(version.getMajor(), version.getMinor());
	}

	public static boolean major(Predicate<Integer> predicate) {
		return predicate.test(getServer().getMajor());
	}

	public static boolean minor(Predicate<Integer> predicate) {
		return predicate.test(getServer().getMinor());
	}

	public boolean refaction(Predicate<Integer> predicate) {
		return predicate.test(getServer().getRefaction());
	}

}
