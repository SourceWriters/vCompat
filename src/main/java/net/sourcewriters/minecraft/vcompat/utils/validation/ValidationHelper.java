package net.sourcewriters.minecraft.vcompat.utils.validation;

public class ValidationHelper {

    private ValidationHelper() {}

    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (expression == false) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

}