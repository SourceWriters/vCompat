package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_11_R1.tools;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;

import net.sourcewriters.minecraft.versiontools.reflection.tools.ServerTools;

public class ServerTools1_11_R1 extends ServerTools {

    @Override
    public void setMotd(String text) {
        ((CraftServer) Bukkit.getServer()).getServer().setMotd(text);
    }

    @Override
    public String getMotd() {
        return ((CraftServer) Bukkit.getServer()).getServer().getMotd();
    }

}