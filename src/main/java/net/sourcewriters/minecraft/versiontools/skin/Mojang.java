package net.sourcewriters.minecraft.versiontools.skin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.net.http.ContentType;
import com.syntaxphoenix.syntaxapi.net.http.Request;
import com.syntaxphoenix.syntaxapi.net.http.RequestType;
import com.syntaxphoenix.syntaxapi.utils.json.JsonTools;

import net.sourcewriters.minecraft.versiontools.reflection.VersionControl;

public class Mojang {

	public static final String URL_SKIN_PROFILE = "https://sessionserver.mojang.com/session/minecraft/profile/%s";
	public static final String URL_SKIN_UPLOAD = "https://api.mojang.com/user/profile/%s/skin";

	public static final String AUTH_SERVER = "https://authserver.mojang.com/%s";

	private final ArrayList<Skin> skins = new ArrayList<>();

	private final MojangProvider provider;

	/*
	 * 
	 */

	public Mojang(MojangProvider provider) {
		this.provider = provider;
	}

	/*
	 * 
	 */

	public boolean request(Player player, String name) {

		Skin skin = getSkin(name.toLowerCase());

		if (skin == null) {
			return false;
		}

		return request(player, skin);

	}

	public boolean request(Player player, String name, String uniqueId) {

		Skin skin = getSkin((name = name.toLowerCase()));

		if (skin == null) {
			if ((skin = downloadSkinFromPlayer(name, uniqueId)) == null) {
				return false;
			}
		}

		return request(player, skin);

	}

	public boolean request(SkinRequest request) {

		Skin skin = getSkin(request.getName());

		if (skin == null) {
			if ((skin = downloadSkin(request)) == null) {
				return false;
			}
		}

		return request(request.getRequester(), skin);

	}

	/*
	 * 
	 */

	public boolean request(Player player, Skin skin) {

		if (player == null || skin == null) {
			return false;
		}

		provider.setSkinProperty(player, skin);

		if (player.isOnline()) {
			VersionControl.get().getPlayerProvider().getPlayer(player).setSkin(skin);
		}

		return true;

	}

	/*
	 * 
	 */

	public final MojangProvider getProvider() {
		return provider;
	}

	public final List<Profile> getProfiles() {
		return provider.getProfiles();
	}

	public final ArrayList<Skin> getSkins() {
		return skins;
	}

	/*
	 * 
	 */

	public Skin getSkin(String name) {
		Optional<Skin> option = getOptionalSkin(name);
		if (option.isPresent()) {
			return option.get();
		}
		return null;
	}

	public Optional<Skin> getOptionalSkin(String name) {
		return skins.stream().filter(skin -> skin.getName().equals(name)).findAny();
	}

	/*
	 * 
	 */

	public Profile getUseableProfile() {

		Profile[] array = getProfiles()
			.stream()
			.filter(profile -> profile.isAuthenticated())
			.toArray(size -> new Profile[size]);

		if (array.length != 0) {
			for (Profile profile : array) {
				if (profile.validate()) {
					return profile;
				}
				if (profile.refresh().validate()) {
					return profile;
				}
			}
		}

		array = getProfiles().stream().filter(profile -> !profile.isAuthenticated()).toArray(size -> new Profile[size]);

		if (array.length == 0) {
			return null;
		}

		for (Profile profile : array) {
			if (profile.authenticate().isAuthenticated()) {
				return profile;
			}
		}

		return null;

	}

	/*
	 * 
	 */

	private Skin downloadSkin(SkinRequest skinRequest) {

		Profile profile = getUseableProfile();

		if (profile == null) {
			return null;
		}

		try {

			Request request = new Request(RequestType.POST);

			request.header("Authorization", "Bearer " + profile.getAuthToken());

			request.parameter("url", skinRequest.getUrl()).parameter("model", skinRequest.getModel().toString());

			int code = request
				.execute(String.format(URL_SKIN_UPLOAD, profile.getUniqueId()), ContentType.X_WWW_FORM_URLENCODED)
				.getCode();

			if (code != 204) {
				return null;
			}

			return downloadSkinFromPlayer(skinRequest.getName(), profile.getUniqueId());

		} catch (IOException ignore) {

		}

		return null;
	}

	public Skin downloadSkinFromPlayer(String name, String uniqueId) {

		try {

			Request request = new Request(RequestType.GET);

			JsonObject object = request
				.execute(URL_SKIN_PROFILE, ContentType.X_WWW_FORM_URLENCODED)
				.getResponseAsJson();

			if (!object.has("properties")) {
				return null;
			}

			JsonObject property = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();

			String value = property.get("value").getAsString();
			String signature = property.get("signature").getAsString();
			String url = getSkinUrl(value);

			if (url == null) {
				return null;
			}

			return new Skin(name, value, signature, false);

		} catch (IOException ignore) {
			return null;
		}

	}

	/*
	 * 
	 */

	public String getSkinUrl(String base64) {

		String decoded = Base64Coder.decodeString(base64);

		JsonObject json = JsonTools.readJson(decoded);

		if (!json.has("textures")) {
			return null;
		}

		JsonObject textures = json.get("textures").getAsJsonObject();

		if (textures.entrySet().isEmpty()) {
			return null;
		}

		return textures.get("SKIN").getAsJsonObject().get("url").getAsString();

	}

}
