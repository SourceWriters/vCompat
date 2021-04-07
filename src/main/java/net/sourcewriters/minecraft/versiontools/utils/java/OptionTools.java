package net.sourcewriters.minecraft.versiontools.utils.java;

import java.util.Optional;

public abstract class OptionTools {

    @SuppressWarnings("rawtypes")
    public static boolean checkPresence(Optional... optionals) {
        for (Optional optional : optionals) {
            if (!optional.isPresent()) {
                return false;
            }
        }
        return true;
    }

}