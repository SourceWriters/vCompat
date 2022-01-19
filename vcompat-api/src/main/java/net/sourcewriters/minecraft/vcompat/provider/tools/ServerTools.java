package net.sourcewriters.minecraft.vcompat.provider.tools;

import net.sourcewriters.minecraft.vcompat.provider.wrapper.ConsoleReaderWrapper;

public abstract class ServerTools {

    public abstract void setMotd(String text);

    public abstract String getMotd();
    
    public abstract ConsoleReaderWrapper getConsole();

}