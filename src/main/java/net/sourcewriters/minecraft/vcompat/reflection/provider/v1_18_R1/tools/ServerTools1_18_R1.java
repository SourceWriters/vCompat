package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.tools;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;

import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.wrapper.ConsoleReaderWrapper1_18_R1;
import net.sourcewriters.minecraft.vcompat.reflection.tools.ServerTools;

public class ServerTools1_18_R1 extends ServerTools {

    @Override
    public void setMotd(String text) {
        ((CraftServer) Bukkit.getServer()).getServer().e(text);
    }

    @Override
    public String getMotd() {
        return ((CraftServer) Bukkit.getServer()).getServer().aa();
    }
    
    @SuppressWarnings("resource")
    @Override
    public ConsoleReaderWrapper1_18_R1 getConsole() {
        return ConsoleReaderWrapper1_18_R1.INSTANCE;
    }

}