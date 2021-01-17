package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R3.tools;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import net.sourcewriters.minecraft.versiontools.reflection.tools.ServerTools;

public class ServerTools1_8_R3 extends ServerTools {

	@Override
	public void setMotd(String text) {
		((CraftServer) Bukkit.getServer()).getServer().setMotd(text);
	}

	@Override
	public String getMotd() {
		return ((CraftServer) Bukkit.getServer()).getServer().getMotd();
	}

}