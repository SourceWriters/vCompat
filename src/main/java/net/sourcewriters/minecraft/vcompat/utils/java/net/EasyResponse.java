package net.sourcewriters.minecraft.vcompat.utils.java.net;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;

import net.sourcewriters.minecraft.vcompat.utils.java.net.tools.HeaderParser;

public class EasyResponse {

    private final int code;
    private final byte[] data;
    private final JsonObject headers = new JsonObject();

    public EasyResponse(int code, byte[] data, Map<String, List<String>> map) {
        this.code = code;
        this.data = data;
        HeaderParser.parse(headers, map);
    }

    public int getCode() {
        return code;
    }

    public byte[] getData() {
        return data;
    }

    public boolean hasData() {
        return data.length != 0;
    }

    public JsonValue<?> getDataAsJson() {
        if (!hasHeader("Content-Type")) {
            return JsonNull.get();
        }
        JsonValue<?> value = getHeaderValue("Content-Type");
        if (value.hasType(ValueType.ARRAY)) {
            value = ((JsonArray) value).get(0);
        }
        IEasyContent content = EasyContentRegistry.get(value.getValue().toString());
        return content == null ? JsonNull.get() : content.deserialize(data);
    }

    public JsonObject getHeaders() {
        return headers;
    }

    public boolean hasHeaders() {
        return !headers.isEmpty();
    }

    public boolean hasHeader(String name) {
        return headers.has(name);
    }

    public boolean hasHeader(String name, ValueType type) {
        return headers.has(name, type);
    }

    public Optional<JsonValue<?>> getHeader(String name) {
        return Optional.ofNullable(headers.get(name));
    }

    public JsonValue<?> getHeaderValue(String name) {
        return getHeader(name).orElse(null);
    }

    public Optional<JsonValue<?>> getHeader(String name, ValueType type) {
        return getHeader(name).filter(value -> value.hasType(type));
    }

    public JsonValue<?> getHeaderValue(String name, ValueType type) {
        return getHeader(name, type).orElse(null);
    }

}
