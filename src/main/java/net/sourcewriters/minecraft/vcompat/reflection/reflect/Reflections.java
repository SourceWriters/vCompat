package net.sourcewriters.minecraft.vcompat.reflection.reflect;

import java.util.function.Consumer;

import net.sourcewriters.minecraft.vcompat.reflection.reflect.provider.GeneralReflections;

public abstract class Reflections {

    public static void globalSetup(ReflectionProvider provider) {
        GeneralReflections.INSTANCE.setup(provider);
    }

    public abstract void setup(ReflectionProvider provider);

    public static <T> T predicate(boolean condition, T value, Consumer<T> action) {
        if (condition) {
            action.accept(value);
        }
        return value;
    }

    public int priority() {
        return 0;
    }

}