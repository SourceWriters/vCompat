package net.sourcewriters.minecraft.vcompat.reflection.tools;

import net.sourcewriters.minecraft.vcompat.reflection.wrapper.ConsoleReaderWrapper;

public abstract class ServerTools {

    public abstract void setMotd(String text);

    public abstract String getMotd();
    
    public abstract ConsoleReaderWrapper getConsole();

}