package net.sourcewriters.minecraft.vcompat.util.java.net;

import java.nio.charset.StandardCharsets;

import com.syntaxphoenix.syntaxapi.json.JsonValue;

public interface IEasyTextContent extends IEasyContent {

    String toString(JsonValue<?> value);

    JsonValue<?> fromString(String data);

    default String type() {
        return name() + "; charset=UTF-8";
    }

    @Override
    default byte[] serialize(JsonValue<?> value) {
        String output = toString(value);
        return output == null ? EMPTY : output.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    default JsonValue<?> deserialize(byte[] data) {
        return fromString(new String(data, StandardCharsets.UTF_8));
    }

}
