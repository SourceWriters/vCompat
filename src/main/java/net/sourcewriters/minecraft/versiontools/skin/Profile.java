package net.sourcewriters.minecraft.versiontools.skin;

import static net.sourcewriters.minecraft.versiontools.skin.Mojang.AUTH_SERVER;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.net.http.Request;
import com.syntaxphoenix.syntaxapi.net.http.RequestType;
import com.syntaxphoenix.syntaxapi.net.http.StandardContentType;

public class Profile {

	private final Mojang mojang;

	private String username;
	private String password;

	private String name = "<N/A>";
	private String uuid;

	private String authToken;

	public Profile(Mojang mojang, String user, String pass) {
		this.mojang = mojang;
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

			request
				.parameter("accessToken", authToken)
				.parameter("clientToken", mojang.getProvider().getClientIdentifier().toString());

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

			request
				.parameter("accessToken", authToken)
				.parameter("clientToken", mojang.getProvider().getClientIdentifier().toString());

			JsonObject response = request
				.execute(String.format(AUTH_SERVER, "refresh"), StandardContentType.JSON)
				.getResponseAsJson();

			authToken = null;

			if (!response.has("accessToken")) {
				return this;
			}

			authToken = response.get("accessToken").getAsString();

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
			agent.addProperty("name", "Minecraft");
			agent.addProperty("version", 1);
			object.add("agent", agent);

			object.addProperty("username", username);
			object.addProperty("password", password);
			object.addProperty("clientToken", mojang.getProvider().getClientIdentifier().toString());

			request.parameter(object);

			JsonObject response = request
				.execute(String.format(AUTH_SERVER, "authenticate"), StandardContentType.JSON)
				.getResponseAsJson();

			authToken = null;

			if (!object.has("selectedProfile")) {
				return this;
			}

			JsonObject profile = response.get("selectedProfile").getAsJsonObject();

			uuid = profile.get("id").getAsString();
			name = profile.get("name").getAsString();

			authToken = response.get("accessToken").getAsString();

			return this;

		} catch (IOException ignore) {
			return this;
		}

	}

}