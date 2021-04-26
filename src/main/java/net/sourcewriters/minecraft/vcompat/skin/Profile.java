package net.sourcewriters.minecraft.vcompat.skin;

import static net.sourcewriters.minecraft.vcompat.skin.Mojang.AUTH_SERVER;

import java.io.IOException;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.net.http.Request;
import com.syntaxphoenix.syntaxapi.net.http.RequestType;
import com.syntaxphoenix.syntaxapi.net.http.StandardContentType;

public class Profile {

    private final MojangProvider provider;
    
    private String username;
    private String password;

    private String name = "<N/A>";
    private String uuid;

    private String authToken;

    public Profile(MojangProvider provider, String user, String pass) {
        this.provider = provider;
        this.username = user;
        this.password = pass;
    }

    /*
    * 
    */

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getUniqueId() {
        return uuid;
    }

    public String getAuthToken() {
        return authToken;
    }

    /*
    * 
    */

    public boolean isAuthenticated() {
        return authToken != null;
    }

    /*
    * 
    */

    public boolean validate() {
        if (authToken == null) {
            return false;
        }

        try {

            Request request = new Request(RequestType.POST);

            request.parameter("accessToken", authToken).parameter("clientToken", provider.getClientIdentifier().toString());

            int code = request.execute(String.format(AUTH_SERVER, "validate"), StandardContentType.JSON).getCode();

            if (code == 204) {
                return true;
            } else {
                return false;
            }

        } catch (IOException ignore) {
            return false;
        }

    }

    public Profile refresh() {
        if (authToken == null) {
            return this;
        }

        try {

            Request request = new Request(RequestType.POST);

            request.parameter("accessToken", authToken).parameter("clientToken", provider.getClientIdentifier().toString());

            JsonValue<?> responseRaw = request.execute(String.format(AUTH_SERVER, "refresh"), StandardContentType.JSON).getResponseAsJson();

            if (!responseRaw.hasType(ValueType.OBJECT)) {
                return this;
            }
            JsonObject response = (JsonObject) responseRaw;

            authToken = null;

            if (!response.has("accessToken")) {
                return this;
            }

            authToken = response.get("accessToken").getValue().toString();

            return this;

        } catch (IOException ignore) {
            return this;
        }

    }

    public Profile authenticate() {

        try {

            Request request = new Request(RequestType.POST);

            JsonObject object = new JsonObject();
            JsonObject agent = new JsonObject();
            agent.set("name", "Minecraft");
            agent.set("version", 1);
            object.set("agent", agent);

            object.set("username", username);
            object.set("password", password);
            object.set("clientToken", provider.getClientIdentifier().toString());

            request.parameter(object);

            JsonValue<?> responseRaw = request.execute(String.format(AUTH_SERVER, "authenticate"), StandardContentType.JSON)
                .getResponseAsJson();

            if (!responseRaw.hasType(ValueType.OBJECT)) {
                return this;
            }
            JsonObject response = (JsonObject) responseRaw;

            authToken = null;

            if (!object.has("selectedProfile")) {
                return this;
            }

            JsonObject profile = (JsonObject) response.get("selectedProfile");

            uuid = profile.get("id").getValue().toString();
            name = profile.get("name").getValue().toString();

            authToken = response.get("accessToken").getValue().toString();

            return this;

        } catch (IOException ignore) {
            return this;
        }

    }

}