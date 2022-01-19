package net.sourcewriters.minecraft.vcompat.utils.java.net;

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

    default void modify(URL url, JsonValue<?> value) {}

}
