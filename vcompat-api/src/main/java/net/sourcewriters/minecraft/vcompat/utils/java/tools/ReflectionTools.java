package net.sourcewriters.minecraft.vcompat.utils.java.tools;

import java.lang.reflect.Array;

public final class ReflectionTools {

    private ReflectionTools() {}
    
    public static Class<?> subclass(Class<?> clazz, String name) {
        for (Class<?> search : clazz.getClasses()) {
            if (search.getSimpleName().split("\\.")[0].equals(name)) {
                return search;
            }
        }
        return null;
    }

    public static Class<?> arrayclass(Class<?> clazz) {
        return Array.newInstance(clazz, 1).getClass();
    }

}