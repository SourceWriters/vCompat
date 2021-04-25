package net.sourcewriters.minecraft.vcompat.logging;

import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeColor;
import org.bukkit.ChatColor;
import org.fusesource.jansi.Ansi;

import java.awt.*;

public class BukkitLogType extends LogTypeColor {

    public static final BukkitLogType DEFAULT = new BukkitLogType("info", ChatColor.GRAY);

    public BukkitLogType(String id) {
        super(id);
    }

    public BukkitLogType(String id, Color color) {
        super(id, color);
    }

    public BukkitLogType(String id, ChatColor chatColor) {
        super(id, LoggingColors.byChatColor(chatColor));
    }

    public BukkitLogType(String id, String name, Color color) {
        super(id, name, color);
    }

    public BukkitLogType(String id, String name, ChatColor chatColor) {
        super(id, name, LoggingColors.byChatColor(chatColor));
    }

    public BukkitLogType(String id, String hex) {
        super(id, ColorTools.hex2rgb(hex));
    }

    public BukkitLogType(String id, String name, String hex) {
        super(id, name, ColorTools.hex2rgb(hex));
    }

    public BukkitLogType(String id, int red, int green, int blue) {
        super(id, new Color(red, green, blue));
    }

    public BukkitLogType(String id, String name, int red, int green, int blue) {
        super(id, name, new Color(red, green, blue));
    }

    @Override
    public String asColorString() {
        return "\033[38;2;" + asColor().getRed() + ";" + asColor().getGreen() + ";" + asColor().getBlue() + "m";
    }

}