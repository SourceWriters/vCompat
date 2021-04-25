package net.sourcewriters.minecraft.vcompat.logging;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.logging.LoggerState;
import com.syntaxphoenix.syntaxapi.logging.color.LogType;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Times;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.ReflectionProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.plugin.Plugin;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.io.Writer;
import java.util.function.BiConsumer;

public class BukkitLogger implements ILogger {

    private static final String ANSI_RESET = Ansi.ansi().reset().toString();

    private final boolean ansiSupported, jLine;
    private final LogTypeMap typeMap = new LogTypeMap();
    private final Writer writer;
    private final ConsoleReader reader;
    private final String plugin;

    private LoggerState state;
    private String format = "[%date%/%time%] [Server thread/%plugin% | %type%]: %message%";

    public BukkitLogger(Plugin plugin) {
        this(plugin.getName());
    }

    public BukkitLogger(String plugin) {
        this.plugin = plugin;
        // TODO: Don't use reflections
        Object mcServer = ReflectionProvider.DEFAULT.createCBReflect("CraftServer", "CraftServer")
                .searchField("server", "console")
                .getFieldValue("server", Bukkit.getServer());
        this.reader = (ConsoleReader) ReflectionProvider.DEFAULT.createNMSReflect("MinecraftServer", "MinecraftServer")
                .searchField("console", "reader")
                .getFieldValue("console", mcServer);
        this.writer = reader.getOutput();
        this.ansiSupported = reader.getTerminal().isAnsiSupported();
        this.jLine = Main.useJline;

        setDefaults();
    }

    @Override
    public ILogger close() {
        throw new UnsupportedOperationException("BukkitLogger cannot be closed");
    }

    @Override
    public LogTypeMap getTypeMap() {
        return typeMap;
    }

    @Override
    public ILogger setCustom(BiConsumer<Boolean, String> biConsumer) {
        throw new UnsupportedOperationException("BukkitLogger doesn't support setCustom");
    }

    @Override
    public BiConsumer<Boolean, String> getCustom() {
        throw new UnsupportedOperationException("BukkitLogger doesn't support getCustom");
    }

    @Override
    public ILogger setType(LogType logType) {
        typeMap.override(logType);
        return this;
    }

    @Override
    public LogType getType(String s) {
        return typeMap.tryGet(type -> type.getId().equals(s)).orElse(BukkitLogType.DEFAULT);
    }

    private void setDefaults() {
        setType(new BukkitLogType("debug", ChatColor.GRAY));
        setType(new BukkitLogType("info", ChatColor.AQUA));
        setType(new BukkitLogType("warning", ChatColor.GOLD));
        setType(new BukkitLogType("error", ChatColor.RED));
    }

    public String rgb(int red, int green, int blue) {
        return "\033[38;2;" + red + ";" + green + ";" + blue + "m";
    }

    @Override
    public ILogger setState(LoggerState state) {
        this.state = state;
        return this;
    }

    @Override
    public LoggerState getState() {
        return state;
    }

    @Override
    public ILogger setThreadName(String s) {
        throw new UnsupportedOperationException("BukkitLogger doesn't support setThreadName");
    }

    @Override
    public String getThreadName() {
        return Thread.currentThread().getName();
    }

    @Override
    public ILogger setColored(boolean b) {
        throw new UnsupportedOperationException("Can't invoke setColored in BukkitLogger");
    }

    @Override
    public boolean isColored() {
        return ansiSupported;
    }

    @Override
    public ILogger log(String message) {
        return log(LogTypeId.INFO, message);
    }

    @Override
    public ILogger log(LogTypeId logTypeId, String message) {
        return log(logTypeId.id(), message);
    }

    @Override
    public ILogger log(String typeId, String message) {
        return log(getType(typeId), message);
    }

    @Override
    public ILogger log(String... messages) {
        return log(LogTypeId.INFO, messages);
    }

    @Override
    public ILogger log(LogTypeId logTypeId, String... messages) {
        return log(logTypeId.id(), messages);
    }

    @Override
    public ILogger log(String typeId, String... messages) {
        return log(getType(typeId), messages);
    }

    @Override
    public ILogger log(Throwable throwable) {
        return log(LogTypeId.ERROR, throwable);
    }

    @Override
    public ILogger log(LogTypeId logTypeId, Throwable throwable) {
        return log(logTypeId.id(), throwable);
    }

    @Override
    public ILogger log(String typeID, Throwable throwable) {
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
        try {
            LogTypeId id = getId(type);
            boolean colorizedMessage = ansiSupported && (id == LogTypeId.WARNING || id == LogTypeId.ERROR);
            message = format
                    .replace("%date%", Times.getDate(".").substring(0, 5))
                    .replace("%time%", Times.getTime(":"))
                    .replace("%thread%", getThreadName())
                    .replace("%plugin%", plugin)
                    .replace("%type%", (type.asColorString(ansiSupported)) + type.getName().toUpperCase() + (ansiSupported ? ANSI_RESET : ""))
                    .replace("%message%", (colorizedMessage ? type.asColorString() : "") + colorize(message));
            message += (ansiSupported ? Ansi.ansi().reset() : "") + System.getProperty("line.separator");
            if (jLine) {
                reader.flush();
                writer.write("\b\b" + message);
                writer.flush();
                reader.drawLine();
                return this;
            }
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private LogTypeId getId(LogType type) {
        for (LogTypeId value : LogTypeId.values()) {
            if (value.name().equalsIgnoreCase(type.getId()))
                return value;
        }
        return null;
    }

    private String colorize(String message) {
        return LoggingColors.format(message, ansiSupported);
    }

}