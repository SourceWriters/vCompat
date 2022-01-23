package net.sourcewriters.minecraft.vcompat.provider.lookup;

import java.util.Optional;

import com.syntaxphoenix.syntaxapi.reflection.ClassCache;

public final class JavaTracker {

    private JavaTracker() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Optional<Class<?>> getClassFromStack(int offset) {
        StackTraceElement element = getStack()[3 + offset];
        return element == null ? Optional.empty() : ClassCache.getOptionalClass(element.getClassName());
    }

    public static Optional<Class<?>> getCallerClass() {
        return getClassFromStack(1);
    }

    private static StackTraceElement[] getStack() {
        return new Throwable().getStackTrace();
    }

}