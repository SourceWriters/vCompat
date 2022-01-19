package net.sourcewriters.minecraft.vcompat.utils.logging;

import org.bukkit.ChatColor;
import org.fusesource.jansi.Ansi;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;

public class LoggingColors {

    public static final String ANSI_FORMAT = "\033[38;2;%s;%s;%sm";

    private static final Map<ChatColor, String> REPLACEMENTS = new EnumMap<>(ChatColor.class);
    private static final Map<ChatColor, Color> CHAT_COLOR_TO_COLOR = new EnumMap<>(ChatColor.class);

    private LoggingColors() {}

    static {
        REPLACEMENTS.put(ChatColor.BLACK, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString());
        REPLACEMENTS.put(ChatColor.DARK_BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString());
        REPLACEMENTS.put(ChatColor.DARK_GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString());
        REPLACEMENTS.put(ChatColor.DARK_AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString());
        REPLACEMENTS.put(ChatColor.DARK_RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString());
        REPLACEMENTS.put(ChatColor.DARK_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString());
        REPLACEMENTS.put(ChatColor.GOLD, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString());
        REPLACEMENTS.put(ChatColor.GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString());
        REPLACEMENTS.put(ChatColor.DARK_GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString());
        REPLACEMENTS.put(ChatColor.BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString());
        REPLACEMENTS.put(ChatColor.GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString());
        REPLACEMENTS.put(ChatColor.AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString());
        REPLACEMENTS.put(ChatColor.RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString());
        REPLACEMENTS.put(ChatColor.LIGHT_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString());
        REPLACEMENTS.put(ChatColor.YELLOW, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString());
        REPLACEMENTS.put(ChatColor.WHITE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString());
        REPLACEMENTS.put(ChatColor.MAGIC, Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString());
        REPLACEMENTS.put(ChatColor.BOLD, Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString());
        REPLACEMENTS.put(ChatColor.STRIKETHROUGH, Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString());
        REPLACEMENTS.put(ChatColor.UNDERLINE, Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString());
        REPLACEMENTS.put(ChatColor.ITALIC, Ansi.ansi().a(Ansi.Attribute.ITALIC).toString());
        REPLACEMENTS.put(ChatColor.RESET, Ansi.ansi().a(Ansi.Attribute.RESET).toString());

        CHAT_COLOR_TO_COLOR.put(ChatColor.BLACK, new Color(0));
        CHAT_COLOR_TO_COLOR.put(ChatColor.DARK_BLUE, new Color(170));
        CHAT_COLOR_TO_COLOR.put(ChatColor.DARK_GREEN, new Color(43520));
        CHAT_COLOR_TO_COLOR.put(ChatColor.DARK_AQUA, new Color(43690));
        CHAT_COLOR_TO_COLOR.put(ChatColor.DARK_RED, new Color(11141120));
        CHAT_COLOR_TO_COLOR.put(ChatColor.DARK_PURPLE, new Color(11141290));
        CHAT_COLOR_TO_COLOR.put(ChatColor.GOLD, new Color(16755200));
        CHAT_COLOR_TO_COLOR.put(ChatColor.GRAY, new Color(11184810));
        CHAT_COLOR_TO_COLOR.put(ChatColor.DARK_GRAY, new Color(5592405));
        CHAT_COLOR_TO_COLOR.put(ChatColor.BLUE, new Color(5592575));
        CHAT_COLOR_TO_COLOR.put(ChatColor.GREEN, new Color(5635925));
        CHAT_COLOR_TO_COLOR.put(ChatColor.AQUA, new Color(5636095));
        CHAT_COLOR_TO_COLOR.put(ChatColor.RED, new Color(16733525));
        CHAT_COLOR_TO_COLOR.put(ChatColor.LIGHT_PURPLE, new Color(16733695));
        CHAT_COLOR_TO_COLOR.put(ChatColor.YELLOW, new Color(16777045));
        CHAT_COLOR_TO_COLOR.put(ChatColor.WHITE, new Color(16777215));
    }

    public static String toAnsi(int red, int green, int blue) {
        return String.format(ANSI_FORMAT, red, green, blue);
    }

    public static String toAnsi(Color color) {
        return toAnsi(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static String format(String raw, boolean supportAnsi) {
        if (!supportAnsi)
            return ChatColor.stripColor(raw);
        String result = raw;
        for (ChatColor color : REPLACEMENTS.keySet()) {
            result = result.replaceAll("(?i)" + color.toString(), REPLACEMENTS.getOrDefault(color, ""));
        }
        return result;
    }

    public static Color byChatColor(ChatColor chatColor) {
        return CHAT_COLOR_TO_COLOR.get(chatColor);
    }

}
