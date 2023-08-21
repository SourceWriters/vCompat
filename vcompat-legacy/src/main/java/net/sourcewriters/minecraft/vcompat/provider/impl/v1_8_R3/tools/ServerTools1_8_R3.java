package net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R3.tools;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R3.wrapper.ConsoleReaderWrapper1_8_R3;
import net.sourcewriters.minecraft.vcompat.provider.tools.ServerTools;

public class ServerTools1_8_R3 extends ServerTools {

    @Override
    public void setMotd(String text) {
        ((CraftServer) Bukkit.getServer()).getServer().setMotd(text);
    }

    @Override
    public String getMotd() {
        return ((CraftServer) Bukkit.getServer()).getServer().getMotd();
    }

    @Override
    public String getLevelName() {
        return ((CraftServer) Bukkit.getServer()).getServer().getPropertyManager().getString("level-name", "world");
    }

    @Override
    public ConsoleReaderWrapper1_8_R3 getConsole() {
        return ConsoleReaderWrapper1_8_R3.INSTANCE;
    }

}