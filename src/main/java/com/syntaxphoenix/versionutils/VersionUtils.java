package com.syntaxphoenix.versionutils;

import org.bukkit.Bukkit;

public class VersionUtils {
	
	public static String getServerVersion() {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		return version;
	}
	
	public static String getMinecraftVersion() {
		String versionString = Bukkit.getServer().getClass().getPackage().getName();
		String version = versionString.substring(versionString.lastIndexOf('.') + 1);

		return version;
	}
}
