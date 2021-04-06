package net.sourcewriters.minecraft.versiontools.utils.minecraft;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;
import com.syntaxphoenix.syntaxapi.utils.json.JsonTools;

public final class MojangProfileServer {

    private MojangProfileServer() {}

    public static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    public static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    public static UUID getUniqueIdPlayed(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(fromShortenId(getUniqueIdString(name)));
        if (player.hasPlayedBefore()) {
            return player.getUniqueId();
        }
        return null;
    }

    public static UUID getUniqueId(String name) {
        return fromShortenId(getUniqueIdString(name));
    }

    public static UUID fromShortenId(String uid) {
        return UUID.fromString(uid.substring(0, 8) + "-" + uid.substring(8, 12) + "-" + uid.substring(12, 16) + "-" + uid.substring(16, 20)
            + "-" + uid.substring(20, 32));
    }

    public static String shortUUID(UUID id) {
        return id.toString().replace("-", "");
    }

    public static String getUniqueIdString(String name) {
        try {
            URL url = new URL(String.format(UUID_URL, name));
            String jsonString = Streams.toString(url.openStream());
            if (!jsonString.isEmpty()) {
                JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject();
                String uuid = object.get("id").getAsString();
                return uuid;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getName(UUID uniqueId) {
        return getNameShorten(shortUUID(uniqueId));
    }

    public static String getNameShorten(String uniqueId) {
        try {
            URL url = new URL(String.format(PROFILE_URL, uniqueId));
            String jsonString = Streams.toString(url.openStream());
            if (!jsonString.isEmpty()) {
                JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject();
                String name = object.get("name").getAsString();
                return name;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Skin getSkin(UUID uniqueId) {
        return getSkinShorten(shortUUID(uniqueId));
    }

    public static Skin getSkin(String name, UUID uniqueId) {
        return getSkinShorten(name, shortUUID(uniqueId));
    }

    public static Skin getSkinShorten(String uniqueId) {
        String name = getNameShorten(uniqueId);
        if (name.isEmpty()) {
            return null;
        }
        return getSkinShorten(name, uniqueId);
    }

    public static Skin getSkinShorten(String name, String uniqueId) {
        try {
            URL url = new URL(String.format(PROFILE_URL, uniqueId));
            String jsonString = Streams.toString(url.openStream());
            if (jsonString.isEmpty()) {
                return null;
            }
            JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject().get("properties").getAsJsonArray().get(0)
                .getAsJsonObject();

            String value = object.get("value").getAsString();
            String signature = object.get("signature").getAsString();
            String skinUrl = getSkinUrl(value);
            if (skinUrl == null) {
                return null;
            }
            return new Skin(name, value, signature, false);
        } catch (IOException ignore) {
            return null;
        }
    }

    private static String getSkinUrl(String base64) {
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
