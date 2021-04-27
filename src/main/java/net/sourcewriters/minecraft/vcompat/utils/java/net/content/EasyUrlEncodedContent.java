package net.sourcewriters.minecraft.vcompat.utils.java.net.content;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.syntaxphoenix.syntaxapi.json.JsonEntry;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.utils.net.UrlReflection;

import net.sourcewriters.minecraft.vcompat.utils.java.net.IEasyTextContent;
import net.sourcewriters.minecraft.vcompat.utils.java.net.tools.HeaderParser;
import net.sourcewriters.minecraft.vcompat.utils.java.net.tools.UrlEncoder;

public class EasyUrlEncodedContent implements IEasyTextContent {

    public static final EasyUrlEncodedContent URL_ENCODED = new EasyUrlEncodedContent(false);
    public static final EasyUrlEncodedContent URL_ENCODED_MODIFY = new EasyUrlEncodedContent(true);

    private final UrlEncoder encoder = UrlEncoder.get(StandardCharsets.UTF_8);
    private final boolean modifyUrl;

    protected EasyUrlEncodedContent(boolean modifyUrl) {
        this.modifyUrl = modifyUrl;
    }

    @Override
    public String name() {
        return "application/x-www-form-urlencoded";
    }

    @Override
    public final boolean modifyUrl() {
        return modifyUrl;
    }

    @Override
    public void modify(URL url, JsonValue<?> value) {
        String data = toString(value);
        if (data == null) {
            return;
        }
        UrlReflection.applyQuery(url, '?' + data);
    }

    @Override
    public String toString(JsonValue<?> value) {
        if (!value.hasType(ValueType.OBJECT)) {
            return null;
        }
        JsonObject object = (JsonObject) value;
        if (object.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (JsonEntry<?> entry : object) {
            if (entry.getType() == ValueType.NULL) {
                continue;
            }
            builder.append(encoder.encode(entry.getKey())).append('=');
            builder.append(encoder.encode(entry.getValue().getValue().toString())).append('&');
        }
        int length = builder.length();
        return length == 0 ? null : builder.substring(0, length - 1);
    }

    @Override
    public JsonObject fromString(String data) {
        JsonObject object = new JsonObject();
        if (data.trim().isEmpty()) {
            return object;
        }
        String[] parameters = data.contains("&") ? data.split("&")
            : new String[] {
                data
            };
        for (String parameter : parameters) {
            if (!parameter.contains("=")) {
                object.set(encoder.decode(parameter), true);
                continue;
            }
            String[] parts = parameter.split("=", 2);
            object.set(encoder.decode(parts[0]), HeaderParser.identify(parts[1]));
        }
        return object;
    }

}
