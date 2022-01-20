package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R1.wrapper;

import java.io.IOException;
import java.io.Writer;

import org.bukkit.Bukkit;

import net.sourcewriters.minecraft.vcompat.provider.wrapper.ConsoleReaderWrapper;

import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;

import jline.console.ConsoleReader;

public final class ConsoleReaderWrapper1_18_R1 extends ConsoleReaderWrapper {

    public static final ConsoleReaderWrapper1_18_R1 INSTANCE = new ConsoleReaderWrapper1_18_R1();
    
    private final ConsoleReader reader;
    
    @SuppressWarnings("resource")
    private ConsoleReaderWrapper1_18_R1() {
        this.reader = ((CraftServer) Bukkit.getServer()).getServer().reader;
    }

    @Override
    public Writer getOutput() {
        return reader.getOutput();
    }

    @Override
    public boolean isAnsiSupported() {
        return reader.getTerminal().isAnsiSupported();
    }

    @Override
    public void flush() throws IOException {
        reader.flush();
    }

    @Override
    public void drawLine() throws IOException {
        reader.drawLine();
    }

    @Override
    public boolean isJLineSupported() {
        return Main.useJline;
    }

}
