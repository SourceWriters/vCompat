package net.sourcewriters.minecraft.versiontools.version;

import org.bukkit.Bukkit;

import com.syntaxphoenix.syntaxapi.version.DefaultVersion;
import com.syntaxphoenix.syntaxapi.version.Version;

public abstract class Versions {

	private static ServerVersion SERVER_VERSION;
	private static String SERVER_VERSION_STRING;

	private static Version MINECRAFT_VERSION;
	private static String MINECRAFT_VERSION_STRING;

	public static ServerVersion getServer() {
		return SERVER_VERSION != null ? SERVER_VERSION
			: (SERVER_VERSION = new ServerVersion()
				.getAnalyzer()
				.analyze(Bukkit.getServer().getClass().getPackage().getName().split("\\.", 4)[3]));
	}

	public static String getServerAsString() {
		return SERVER_VERSION_STRING != null ? SERVER_VERSION_STRING : (SERVER_VERSION_STRING = getServer().toString());
	}

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

}
