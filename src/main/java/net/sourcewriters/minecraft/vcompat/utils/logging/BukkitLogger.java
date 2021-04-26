package net.sourcewriters.minecraft.vcompat.utils.logging;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.logging.LoggerState;
import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;
import com.syntaxphoenix.syntaxapi.logging.color.LogType;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Times;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.vcompat.reflection.VersionControl;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.plugin.Plugin;
import org.fusesource.jansi.Ansi;

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.function.BiConsumer;

public class BukkitLogger implements ILogger {

    private static final String ANSI_RESET = Ansi.ansi().reset().toString();

    private static final Container<ConsoleReader> READER = Container.of();

    private static ConsoleReader getReader() {
        return READER.isPresent() ? READER.get()
            : READER.replace(VersionControl.get().getToolProvider().getServerTools().getConsole()).get();
    }

    private final boolean ansiSupported, jLine;

    private final HashSet<String> colorMessage = new HashSet<>();
    private final LogTypeMap typeMap = new LogTypeMap();
    private final Writer writer;
    private final ConsoleReader reader;
    private final String plugin;

    private BiConsumer<Boolean, String> custom;
    private boolean colored = false;

    private LoggerState state;
    private String format = "[%date%/%time%] [Server thread/%plugin% | %type%]: %message%";

    public BukkitLogger(Plugin plugin) {
        this(plugin.getName());
    }

    public BukkitLogger(String plugin) {
        this.plugin = plugin;
        this.reader = getReader();
        this.writer = reader.getOutput();
        this.colored = this.ansiSupported = reader.getTerminal().isAnsiSupported();
        this.jLine = Main.useJline;
        setDefaults();
    }

    @Override
    public BukkitLogger close() {
        return this;
    }

    @Override
    public LogTypeMap getTypeMap() {
        return typeMap;
    }

    @Override
    public BukkitLogger setCustom(BiConsumer<Boolean, String> custom) {
        this.custom = custom;
        return this;
    }

    @Override
    public BiConsumer<Boolean, String> getCustom() {
        return custom;
    }

    public BukkitLogger setType(String id, int rgb) {
        return setType(id, new Color(rgb));
    }

    public BukkitLogger setType(String id, String hex) {
        return setType(id, ColorTools.hex2rgb(hex));
    }

    public BukkitLogger setType(String id, int red, int green, int blue) {
        return setType(id, new Color(red, green, blue));
    }

    public BukkitLogger setType(String id, ChatColor color) {
        return setType(id, LoggingColors.byChatColor(color));
    }

    public BukkitLogger setType(String id, Color color) {
        return setType(new BukkitLogType(id, color));
    }

    public BukkitLogger setType(String id, String name, int rgb) {
        return setType(id, name, new Color(rgb));
    }

    public BukkitLogger setType(String id, String name, String hex) {
        return setType(id, name, ColorTools.hex2rgb(hex));
    }

    public BukkitLogger setType(String id, String name, int red, int green, int blue) {
        return setType(id, name, new Color(red, green, blue));
    }

    public BukkitLogger setType(String id, String name, ChatColor color) {
        return setType(id, name, LoggingColors.byChatColor(color));
    }

    public BukkitLogger setType(String id, String name, Color color) {
        return setType(new BukkitLogType(id, name, color));
    }

    @Override
    public BukkitLogger setType(LogType logType) {
        typeMap.override(logType);
        return this;
    }

    @Override
    public LogType getType(String id) {
        return typeMap.tryGet(type -> type.getId().equals(id)).orElse(BukkitLogType.DEFAULT);
    }

    private void setDefaults() {
        setType("debug", ChatColor.GRAY);
        setType("info", ChatColor.AQUA);
        setType("warning", ChatColor.GOLD);
        setType("error", ChatColor.RED);
    }

    @Override
    public BukkitLogger setState(LoggerState state) {
        this.state = state;
        return this;
    }

    @Override
    public LoggerState getState() {
        return state;
    }

    @Override
    public BukkitLogger setThreadName(String thread) {
        return this;
    }

    @Override
    public String getThreadName() {
        return Thread.currentThread().getName();
    }

    @Override
    public BukkitLogger setColored(boolean colored) {
        if (ansiSupported) {
            this.colored = colored;
        }
        return this;
    }

    @Override
    public boolean isColored() {
        return colored;
    }

    @Override
    public BukkitLogger log(String message) {
        return log(LogTypeId.INFO, message);
    }

    @Override
    public BukkitLogger log(LogTypeId logTypeId, String message) {
        return log(logTypeId.id(), message);
    }

    @Override
    public BukkitLogger log(String typeId, String message) {
        return log(getType(typeId), message);
    }

    @Override
    public BukkitLogger log(String... messages) {
        return log(LogTypeId.INFO, messages);
    }

    @Override
    public BukkitLogger log(LogTypeId logTypeId, String... messages) {
        return log(logTypeId.id(), messages);
    }

    @Override
    public BukkitLogger log(String typeId, String... messages) {
        return log(getType(typeId), messages);
    }

    @Override
    public BukkitLogger log(Throwable throwable) {
        return log(LogTypeId.ERROR, throwable);
    }

    @Override
    public BukkitLogger log(LogTypeId logTypeId, Throwable throwable) {
        return log(logTypeId.id(), throwable);
    }

    @Override
    public BukkitLogger log(String typeID, Throwable throwable) {
        return log(getType(typeID), Exceptions.stackTraceToStringArray(throwable));
    }

    public BukkitLogger setFormat(String format) {
        this.format = format;
        return this;
    }

    public BukkitLogger log(LogType type, String... messages) {
        if (messages == null || messages.length == 0) {
            return this;
        }
        for (String message : messages) {
            log(type, message);
        }
        return this;
    }

    public BukkitLogger log(LogType type, String message) {
        String format = this.format.replace("%date%", Times.getDate(".").substring(0, 5)).replace("%time%", Times.getTime(":"))
            .replace("%thread%", getThreadName()).replace("%plugin%", plugin);
        try {
            return colored ? sendColored(type, format, message) : sendUncolored(type, format, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private BukkitLogger sendColored(LogType type, String format, String message) throws IOException {
        boolean colorMessage = this.colorMessage.contains(type.getId());
        String ansi;
        if (custom != null) {
            boolean color = (ansi = type.asColorString(false)).length() != 0;
            custom.accept(true,
                format.replace("%type%", ansi).replace("%message%", (colorMessage ? ansi : "") + LoggingColors.format(message, color))
                    + (color ? ANSI_RESET : ""));
        }
        return sendStream(format.replace("%type%", ansi = type.asColorString(true)).replace("%message%",
            (colorMessage ? ansi : "") + LoggingColors.format(message, true)) + ANSI_RESET);
    }

    private BukkitLogger sendUncolored(LogType type, String format, String message) throws IOException {
        message = format.replace("%type%", type.getName().toUpperCase()).replace("%message%", LoggingColors.format(message, false));
        if (custom != null) {
            custom.accept(true, message);
        }
        return sendStream(message);
    }

    private BukkitLogger sendStream(String message) throws IOException {
        message += System.getProperty("line.separator");
        if (jLine) {
            reader.flush();
            writer.write("\b\b" + message);
            writer.flush();
            reader.drawLine();
            return this;
        }
        writer.write(message);
        writer.flush();
        return this;
    }

}