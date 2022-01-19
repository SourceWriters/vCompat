package net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R3.wrapper;

import java.io.IOException;
import java.io.Writer;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

import net.sourcewriters.minecraft.vcompat.provider.wrapper.ConsoleReaderWrapper;

public final class ConsoleReaderWrapper1_8_R3 extends ConsoleReaderWrapper {

    public static final ConsoleReaderWrapper1_8_R3 INSTANCE = new ConsoleReaderWrapper1_8_R3();
    
    private final ConsoleReader reader;
    
    private ConsoleReaderWrapper1_8_R3() {
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
