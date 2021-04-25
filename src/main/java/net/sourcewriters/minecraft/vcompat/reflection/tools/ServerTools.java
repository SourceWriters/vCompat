package net.sourcewriters.minecraft.vcompat.reflection.tools;

import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

public abstract class ServerTools {

    public abstract void setMotd(String text);

    public abstract String getMotd();
    
    public abstract ConsoleReader getConsole();

}