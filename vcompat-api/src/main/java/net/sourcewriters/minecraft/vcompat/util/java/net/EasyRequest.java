package net.sourcewriters.minecraft.vcompat.util.java.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.Map.Entry;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

import net.sourcewriters.minecraft.vcompat.util.java.net.content.EasyJsonContent;

public class EasyRequest {

    private static final String[] EMPTY = new String[0];

    private final HashMap<String, ArrayList<String>> headers = new HashMap<>();
    private final EasyRequestType requestType;

    private JsonValue<?> data;

    private int readTimeout = 20000;
    private int connectTimeout = 30000;

    private String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";

    public EasyRequest(EasyRequestType requestType) {
        this.requestType = Objects.requireNonNull(requestType, "EasyRequestType is needed to cast an Http request");
    }

    public EasyRequestType getRequestType() {
        return requestType;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public EasyRequest setReadTimeout(long readTimeout) {
        return setReadTimeout(readTimeout, TimeUnit.MILLISECONDS);
    }

    public EasyRequest setReadTimeout(long readTimeout, TimeUnit unit) {
        this.readTimeout = (readTimeout = unit.toMillis(Math.abs(readTimeout))) > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) readTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public EasyRequest setConnectTimeout(long connectTimeout) {
        return setReadTimeout(connectTimeout, TimeUnit.MILLISECONDS);
    }

    public EasyRequest setConnectTimeout(long connectTimeout, TimeUnit unit) {
        this.connectTimeout = (connectTimeout = unit.toMillis(Math.abs(connectTimeout))) > Integer.MAX_VALUE ? Integer.MAX_VALUE
            : (int) connectTimeout;
        return this;
    }

    public String getAgent() {
        return agent;
    }

    public EasyRequest setAgent(String agent) {
        this.agent = Objects.requireNonNull(agent, "UserAgent cant be null!");
        return this;
    }

    public String[] header(String name) {
        return headers.containsKey(name) ? headers.get(name).toArray(EMPTY) : EMPTY;
    }

    public EasyRequest header(String name, Object... values) {
        ArrayList<String> current = headers.computeIfAbsent(name, ignore -> new ArrayList<>());
        for (Object value : values) {
            current.add(value.toString());
        }
        return this;
    }

    public EasyRequest clearHeader(String name) {
        headers.remove(name);
        return this;
    }

    public EasyRequest clearHeaders() {
        headers.clear();
        return this;
    }

    public boolean hasHeaders() {
        return !headers.isEmpty();
    }

    public EasyRequest data(String field, JsonValue<?> data) {
        Objects.requireNonNull(field, "Data field name cant be null");
        data = data == null ? JsonNull.get() : data;
        JsonValue<?> output = this.data;
        if (output == null || !output.hasType(ValueType.OBJECT)) {
            this.data = (output = new JsonObject());
        }
        ((JsonObject) output).set(field, data);
        return this;
    }

    public EasyRequest data(String field, Object data) {
        return data(field, JsonValue.fromPrimitive(data));
    }

    public EasyRequest data(JsonValue<?> data) {
        this.data = (data != null && data.hasType(ValueType.NULL)) ? null : data;
        return this;
    }

    public EasyRequest data(Object data) {
        return data(JsonValue.fromPrimitive(data));
    }

    public JsonValue<?> data() {
        return data;
    }

    public boolean hasData() {
        return data != null;
    }

    public EasyResponse run(String url) throws IOException {
        return run(url, EasyJsonContent.JSON);
    }

    public EasyResponse run(URL url) throws IOException {
        return run(url, EasyJsonContent.JSON);
    }

    public EasyResponse run(String url, String contentName) throws IOException {
        return run(url, EasyContentRegistry.get(contentName));
    }

    public EasyResponse run(URL url, String contentName) throws IOException {
        return run(url, EasyContentRegistry.get(contentName));
    }

    public EasyResponse run(String url, IEasyContent content) throws IOException {
        return run(new URL(url), content);
    }

    public EasyResponse run(URL url, IEasyContent content) throws IOException {

        boolean output = hasData() && requestType.hasOutput();
        byte[] data = null;
        if (output) {
            if (content.modifyUrl()) {
                output = false;
                content.modify(url, this.data);
            } else {
                data = content.serialize(this.data);
            }
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestType.name());
        connection.setDoOutput(requestType.hasOutput());

        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);

        for (Entry<String, ArrayList<String>> header : headers.entrySet()) {
            connection.setRequestProperty(header.getKey(), String.join("; ", header.getValue()));
        }
        connection.setRequestProperty("User-Agent", agent);
        if (requestType.hasOutput()) {
            connection.setRequestProperty("Content-Type", content.type());
            connection.setFixedLengthStreamingMode(output ? data.length : 0);
        }

        connection.connect();

        if (output) {
            OutputStream streamOut = connection.getOutputStream();
            streamOut.write(data);
            streamOut.flush();
            streamOut.close();
        }

        InputStream streamIn = null;
        try {
            streamIn = connection.getInputStream();
            if (streamIn == null) {
                streamIn = connection.getErrorStream();
            }
        } catch (IOException ignore) {
            streamIn = connection.getErrorStream();
        }

        byte[] input = IEasyContent.EMPTY;
        if (streamIn != null) {
            input = Streams.toByteArray(streamIn);
        }
        int code = connection.getResponseCode();
        Map<String, List<String>> headers = connection.getHeaderFields();
        connection.disconnect();

        return new EasyResponse(code, input, headers);
    }

}
