package net.sourcewriters.minecraft.vcompat.util.java.tools;

import java.util.Optional;

public final class OptionTools {

    private OptionTools() {}
    
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