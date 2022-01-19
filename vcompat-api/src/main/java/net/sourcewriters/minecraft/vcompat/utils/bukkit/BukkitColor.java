package net.sourcewriters.minecraft.vcompat.utils.bukkit;

import java.util.regex.Pattern;

public final class BukkitColor {

    private BukkitColor() {}

    public static final char COLOR_CHAR = '\u00A7';
    public static final char REPLACEMENT_CHAR = '\u0026';
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-ORX]");

    public static String strip(String text) {
        return text == null ? null : STRIP_COLOR_PATTERN.matcher(text).replaceAll("");
    }

    public static String apply(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length - 1; ++i) {
            if (chars[i] == REPLACEMENT_CHAR && ALL_CODES.indexOf(chars[i + 1]) > -1) {
                chars[i] = COLOR_CHAR;
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }
        return new String(chars);
    }

    public static String unapply(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length - 1; ++i) {
            if (chars[i] == COLOR_CHAR && ALL_CODES.indexOf(chars[i + 1]) > -1) {
                chars[i] = REPLACEMENT_CHAR;
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }
        return new String(chars);
    }
}