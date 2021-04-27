package net.sourcewriters.minecraft.vcompat.utils.java.net;

import java.util.concurrent.ConcurrentHashMap;

import net.sourcewriters.minecraft.vcompat.utils.java.net.content.EasyJsonContent;
import net.sourcewriters.minecraft.vcompat.utils.java.net.content.EasyUrlEncodedContent;

public final class EasyContentRegistry {

    private static final ConcurrentHashMap<String, IEasyContent> CONTENT = new ConcurrentHashMap<>();

    static {
        add(EasyJsonContent.JSON);
        add(EasyUrlEncodedContent.URL_ENCODED);
    }

    private EasyContentRegistry() {}

    public static boolean add(IEasyContent content) {
        String name = content.name().toLowerCase();
        if (CONTENT.contains(name)) {
            return false;
        }
        CONTENT.put(name, content);
        return true;
    }

    public static IEasyContent get(String name) {
        return CONTENT.get(name.toLowerCase());
    }

    public static boolean has(String name) {
        return CONTENT.containsKey(name.toLowerCase());
    }

}
