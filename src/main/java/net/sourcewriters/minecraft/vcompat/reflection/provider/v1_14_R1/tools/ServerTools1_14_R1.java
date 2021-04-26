package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_14_R1.tools;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;

import net.sourcewriters.minecraft.vcompat.reflection.tools.ServerTools;

public class ServerTools1_14_R1 extends ServerTools {

    @Override
    public void setMotd(String text) {
        ((CraftServer) Bukkit.getServer()).getServer().setMotd(text);
    }

    @Override
    public String getMotd() {
        return ((CraftServer) Bukkit.getServer()).getServer().getMotd();
    }
    
    @SuppressWarnings("resource")
    @Override
    public ConsoleReader getConsole() {
        return ((CraftServer) Bukkit.getServer()).getServer().reader;
    }

}