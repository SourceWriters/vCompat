package net.sourcewriters.minecraft.vcompat.util.java.tools;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public final class ReflectionTools {

    private ReflectionTools() {}

    public static boolean hasSameArguments(Class<?>[] compare1, Class<?>[] compare2) {
        if (compare1.length == 0 && compare2.length == 0) {
            return true;
        } else if (compare1.length != compare2.length) {
            return false;
        }
        for (Class<?> arg1 : compare1) {
            boolean found = true;
            for (Class<?> arg2 : compare2) {
                if (!arg1.isAssignableFrom(arg2)) {
                    found = false;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

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

    public static Class<?> getClass(String classPath) {
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    public static Object createInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Object getValue(Field field, Object source) {
        if (field != null) {
            boolean access = field.canAccess(source);
            if (!access) {
                field.setAccessible(true);
            }
            Object output = null;
            try {
                output = field.get(source);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                if (!access) {
                    field.setAccessible(access);
                }
                e.printStackTrace();
            }
            if (!access) {
                field.setAccessible(access);
            }
            return output;
        }
        return null;
    }

    public static Object getValue(Field field) {
        return getValue(field, null);
    }

}