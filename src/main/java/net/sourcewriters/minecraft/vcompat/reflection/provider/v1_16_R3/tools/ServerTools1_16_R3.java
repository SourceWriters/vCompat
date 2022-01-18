package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_16_R3.tools;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_16_R3.wrapper.ConsoleReaderWrapper1_16_R3;
import net.sourcewriters.minecraft.vcompat.reflection.tools.ServerTools;

public class ServerTools1_16_R3 extends ServerTools {

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
    public ConsoleReaderWrapper1_16_R3 getConsole() {
        return ConsoleReaderWrapper1_16_R3.INSTANCE;
    }

}