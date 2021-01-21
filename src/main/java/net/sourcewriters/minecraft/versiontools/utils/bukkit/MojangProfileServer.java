package net.sourcewriters.minecraft.versiontools.utils.bukkit;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

public abstract class MojangProfileServer {

     private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
     private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

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
          return UUID.fromString(uid.substring(0, 8) + "-" + uid.substring(8, 12) + "-" + uid.substring(12, 16) + "-"
               + uid.substring(16, 20) + "-" + uid.substring(20, 32));
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
          String uuid = shortUUID(uniqueId);
          try {
               URL url = new URL(String.format(PROFILE_URL, uuid));
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
}
