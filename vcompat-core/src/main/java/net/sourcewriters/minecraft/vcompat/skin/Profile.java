package net.sourcewriters.minecraft.vcompat.skin;

import static net.sourcewriters.minecraft.vcompat.skin.Mojang.AUTH_SERVER;

import java.io.IOException;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.net.http.RequestType;

import net.sourcewriters.minecraft.vcompat.utils.java.net.EasyRequest;

public class Profile {

    private final MojangProvider provider;

    private String username;
    private String password;

    private String name = "<N/A>";
    private String uuid;

    private String authToken;

    private boolean debug = false;

    public Profile(MojangProvider provider, String user, String pass) {
        this.provider = provider;
        this.username = user;
        this.password = pass;
    }

    public boolean debug() {
        return debug;
    }

    public Profile debug(boolean state) {
        this.debug = state;
        return this;
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
            EasyRequest request = new EasyRequest(RequestType.POST).data("accessToken", authToken).data("clientToken",
                provider.getClientIdentifier().toString());
            return request.run(String.format(AUTH_SERVER, "validate")).getCode() == 204;
        } catch (IOException ignore) {
            return false;
        }
    }

    public Profile refresh() {
        if (authToken == null) {
            return this;
        }
        try {
            EasyRequest request = new EasyRequest(RequestType.POST).data("accessToken", authToken).data("clientToken",
                provider.getClientIdentifier().toString());
            JsonValue<?> responseRaw = request.run(String.format(AUTH_SERVER, "refresh")).getDataAsJson();
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
            EasyRequest request = new EasyRequest(RequestType.POST);
            JsonObject object = new JsonObject();
            JsonObject agent = new JsonObject();
            agent.set("name", "Minecraft");
            agent.set("version", 1);
            object.set("agent", agent);
            object.set("username", username);
            object.set("password", password);
            object.set("clientToken", provider.getClientIdentifier().toString());
            request.data(object);
            JsonValue<?> responseRaw = request.run(String.format(AUTH_SERVER, "authenticate")).getDataAsJson();
            if (debug) {
                System.out.println(responseRaw.toPrettyString());
            }
            if (!responseRaw.hasType(ValueType.OBJECT)) {
                return this;
            }
            JsonObject response = (JsonObject) responseRaw;
            authToken = null;
            if (!response.has("selectedProfile")) {
                return this;
            }
            JsonObject profile = (JsonObject) response.get("selectedProfile");
            uuid = profile.get("id").getValue().toString();
            name = profile.get("name").getValue().toString();
            authToken = response.get("accessToken").getValue().toString();
            Thread.yield();
            return this;
        } catch (IOException ignore) {
            return this;
        }
    }

}