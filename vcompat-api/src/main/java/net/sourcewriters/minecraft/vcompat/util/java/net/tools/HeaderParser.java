package net.sourcewriters.minecraft.vcompat.util.java.net.tools;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public final class HeaderParser {

    private HeaderParser() {}

    public static void parse(JsonObject output, Map<String, List<String>> input) {
        for (Entry<String, List<String>> entry : input.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            List<String> content = entry.getValue();
            int size = content.size();
            if (size == 0) {
                output.set(entry.getKey(), true);
                continue;
            }
            if (size == 1) {
                output.set(entry.getKey(), identify(content.get(0)));
                continue;
            }
            JsonArray array = new JsonArray();
            for (int index = 0; index < size; index++) {
                array.add(identify(content.get(index)));
            }
            output.set(entry.getKey(), array);
        }
    }

    public static Object identify(String value) {
        if (Strings.isBoolean(value)) {
            return Boolean.valueOf(value);
        }
        if (Strings.isNumeric(value)) {
            try {
                return Byte.parseByte(value);
            } catch (NumberFormatException e) {
            }
            try {
                return Short.parseShort(value);
            } catch (NumberFormatException e) {
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
            }
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
            }
            return new BigInteger(value);
        }
        if (Strings.isDecimal(value)) {
            String number = value.split("\\.")[0];
            try {
                Integer.parseInt(number);
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
            }
            try {
                Long.parseLong(number);
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
            }
            return new BigDecimal(value);
        }
        return value.equalsIgnoreCase("null") ? null : value;
    }

}
