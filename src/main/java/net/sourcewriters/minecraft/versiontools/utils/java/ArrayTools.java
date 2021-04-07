package net.sourcewriters.minecraft.versiontools.utils.java;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayTools {

    public static List<String> toLowercaseList(String... values) {
        ArrayList<String> list = new ArrayList<>();
        for (String value : values) {
            list.add(value.toLowerCase());
        }
        return list;
    }

    public static Object filter(Object[] array, String name) {
        for (Object object : array) {
            if (object.toString().equals(name)) {
                return object;
            }
        }
        return null;
    }

}