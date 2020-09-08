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
	
	public static boolean isBlockDataUsed(String version) {	
		String[] splittedVersion = version.replace("v", "").split("_");
		if (splittedVersion[0].equalsIgnoreCase("1")) {
			int subVersion = Integer.valueOf(splittedVersion[1]);
			if (subVersion <= 12) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNewSound(String version) {	
		String[] splittedVersion = version.replace("v", "").split("_");
		if (splittedVersion[0].equalsIgnoreCase("1")) {
			int subVersion = Integer.valueOf(splittedVersion[1]);
			if (subVersion <= 8) {
				return false;
			}
		}
		return true;
	}
}
