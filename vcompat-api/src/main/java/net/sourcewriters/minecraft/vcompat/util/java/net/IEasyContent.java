package net.sourcewriters.minecraft.vcompat.util.java.net;

import java.net.URL;

import com.syntaxphoenix.syntaxapi.json.JsonValue;

public interface IEasyContent {

    public static final byte[] EMPTY = new byte[0];

    String name();

    default String type() {
        return name();
    }

    boolean modifyUrl();

    byte[] serialize(JsonValue<?> value);

    JsonValue<?> deserialize(byte[] data);

    default URL modify(URL url, JsonValue<?> value) {
        return url;
    }

}
