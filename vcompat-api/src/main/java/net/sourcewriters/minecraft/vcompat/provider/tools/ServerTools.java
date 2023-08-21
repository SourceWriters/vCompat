package net.sourcewriters.minecraft.vcompat.provider.tools;

import java.io.File;

import org.bukkit.Bukkit;

import net.sourcewriters.minecraft.vcompat.provider.wrapper.ConsoleReaderWrapper;

public abstract class ServerTools {
    
    private final File worldFolder;
    
    public ServerTools() {
        this.worldFolder = new File(Bukkit.getServer().getWorldContainer(), getLevelName());
    }

    public abstract void setMotd(String text);

    public abstract String getMotd();
    
    public abstract String getLevelName();
    
    public final File getWorldFolder() {
        return worldFolder;
    }
    
    public abstract ConsoleReaderWrapper getConsole();

}