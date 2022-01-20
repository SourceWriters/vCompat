package net.sourcewriters.minecraft.vcompat.skin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.provider.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.util.java.net.EasyRequest;
import net.sourcewriters.minecraft.vcompat.util.java.net.EasyRequestType;
import net.sourcewriters.minecraft.vcompat.util.java.net.EasyResponse;
import net.sourcewriters.minecraft.vcompat.util.java.net.EasyResponseCode;
import net.sourcewriters.minecraft.vcompat.util.java.net.content.EasyUrlEncodedContent;
import net.sourcewriters.minecraft.vcompat.util.minecraft.MojangProfileServer;
import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;
import net.sourcewriters.minecraft.vcompat.util.minecraft.SkinModel;

public class Mojang {

    public static final String URL_SKIN_UPLOAD = "https://api.mojang.com/user/profile/%s/skin";
    public static final String AUTH_SERVER = "https://authserver.mojang.com/%s";

    private final MojangProvider provider;
    private final SkinStore store;
    private final ILogger logger;

    private final PlayerProvider<?> playerProvider = VersionCompatProvider.get().getControl().getPlayerProvider();

    private boolean debug = false;

    public Mojang(ILogger logger, MojangProvider provider, SkinStore store) {
        this.logger = logger;
        this.provider = provider;
        this.store = store;
    }

    public boolean debug() {
        return debug;
    }

    public Mojang debug(boolean state) {
        this.debug = state;
        return this;
    }

    /*
     * Getter
     */

    public final MojangProvider getProvider() {
        return provider;
    }

    public final SkinStore getStore() {
        return store;
    }

    /*
     * Skin management
     */

    private Skin apply(Skin skin) {
        store.add(skin);
        return skin;
    }

    public Skin getSkin(String name) {
        return store.get(name);
    }

    public Optional<Skin> getSkinAsOptional(String name) {
        return store.optional(name);
    }

    public boolean hasSkin(String name) {
        return store.contains(name);
    }

    public boolean addSkin(Skin skin) {
        return store.add(skin);
    }

    public boolean removeSkin(Skin skin) {
        return store.remove(skin);
    }

    public boolean removeSkin(String name) {
        return store.remove(name);
    }

    public Skin[] getSkins() {
        return store.getSkins();
    }

    /*
     * Skin requests
     */

    public Skin getSkinOf(String name) {
        return getSkinOf(MojangProfileServer.getUniqueId(name));
    }

    public Skin getSkinOf(UUID uniqueId) {
        return apply(MojangProfileServer.getSkin(uniqueId));
    }

    public Skin getSkinOf(String name, String playerName) {
        return getSkinOf(name, MojangProfileServer.getUniqueId(playerName));
    }

    public Skin getSkinOf(String name, UUID uniqueId) {
        return apply(MojangProfileServer.getSkin(name, uniqueId));
    }

    public Skin getSkinFrom(String url, SkinModel model) {
        try {
            return getSkinFrom(new URL(url), model);
        } catch (MalformedURLException e) {
            logger.log(e);
        }
        return null;
    }

    public Skin getSkinFrom(String url, SkinModel model, int timeout) {
        try {
            return getSkinFrom(new URL(url), model, timeout);
        } catch (MalformedURLException e) {
            logger.log(e);
        }
        return null;
    }

    public Skin getSkinFrom(URL url, SkinModel model) {
        return getSkinFrom(url, model, 15000);
    }

    public Skin getSkinFrom(URL url, SkinModel model, int timeout) {
        Profile profile = getUseableProfile();
        if (profile == null || url == null || model == null) {
            if (debug) {
                logger.log(LogTypeId.DEBUG, "Smth is null (" + (profile == null) + "/" + (url == null) + "/" + (model == null) + ") o.0");
            }
            return null;
        }
        try {
            URLConnection connection = url.openConnection();
            if (!(connection instanceof HttpURLConnection)) {
                logger.log(LogTypeId.ERROR, "Url has to be http or https!");
                return null;
            }
            HttpURLConnection http = (HttpURLConnection) connection;
            http.setConnectTimeout(timeout);
            http.setReadTimeout(timeout / 2);
            try {
                http.connect();
                http.disconnect();
            } catch (SocketTimeoutException exception) {
                logger.log(LogTypeId.ERROR, "Can't connect to url!");
                return null;
            }
            EasyRequest request = new EasyRequest(EasyRequestType.POST);
            request.header("Authorization", "Bearer " + profile.getAuthToken());
            request.data("url", url.toString()).data("model", model.toString());
            EasyResponse response = request.run(String.format(URL_SKIN_UPLOAD, profile.getUniqueId()), EasyUrlEncodedContent.URL_ENCODED);
            if (response.getCode() != EasyResponseCode.NO_CONTENT) {
                if (debug) {
                    logger.log(LogTypeId.DEBUG, "Code: " + response.getCode() + " / Length: " + response.getData().length);
                    logger.log(LogTypeId.DEBUG, response.getDataAsJson().toPrettyString().split("\n"));
                }
                return null;
            }
            return apply(MojangProfileServer.getSkinShorten(profile.getUniqueId()));
        } catch (IOException e) {
            logger.log(e);
            return null;
        }
    }

    public Skin getSkinFrom(String name, String url, SkinModel model) {
        try {
            return getSkinFrom(name, new URL(url), model);
        } catch (MalformedURLException e) {
            logger.log(e);
        }
        return null;
    }

    public Skin getSkinFrom(String name, String url, SkinModel model, int timeout) {
        try {
            return getSkinFrom(name, new URL(url), model, timeout);
        } catch (MalformedURLException e) {
            logger.log(e);
        }
        return null;
    }

    public Skin getSkinFrom(String name, URL url, SkinModel model) {
        return getSkinFrom(name, url, model, 15000);
    }

    public Skin getSkinFrom(String name, URL url, SkinModel model, int timeout) {
        Profile profile = getUseableProfile();
        if (profile == null || name != null || url == null || model == null) {
            if (debug) {
                logger.log(LogTypeId.DEBUG, "Smth is null (" + (profile == null) + "/" + (url == null) + "/" + (model == null) + ") o.0");
            }
            return null;
        }
        try {
            URLConnection connection = url.openConnection();
            if (!(connection instanceof HttpURLConnection)) {
                logger.log(LogTypeId.ERROR, "Url has to be http or https!");
                return null;
            }
            HttpURLConnection http = (HttpURLConnection) connection;
            http.setConnectTimeout(timeout);
            http.setReadTimeout(timeout / 2);
            try {
                http.connect();
                http.disconnect();
            } catch (SocketTimeoutException exception) {
                logger.log(LogTypeId.ERROR, "Can't connect to url!");
                return null;
            }
            EasyRequest request = new EasyRequest(EasyRequestType.POST);
            request.header("Authorization", "Bearer " + profile.getAuthToken());
            request.data("url", url.toString()).data("model", model.toString());
            EasyResponse response = request.run(String.format(URL_SKIN_UPLOAD, profile.getUniqueId()), EasyUrlEncodedContent.URL_ENCODED);
            if (response.getCode() != EasyResponseCode.NO_CONTENT) {
                if (debug) {
                    logger.log(LogTypeId.DEBUG, "Code: " + response.getCode() + " / Length: " + response.getData().length);
                    logger.log(LogTypeId.DEBUG, response.getDataAsJson().toPrettyString().split("\n"));
                }
                return null;
            }
            return apply(MojangProfileServer.getSkinShorten(name, profile.getUniqueId()));
        } catch (IOException e) {
            logger.log(e);
            return null;
        }
    }

    /*
     * Player requests
     */

    public boolean request(Player player, String name) {
        return request(playerProvider.getPlayer(player), name);
    }

    public boolean request(NmsPlayer player, String name) {
        Skin skin = store.get(name);
        if (skin == null) {
            skin = getSkinOf(name);
            if (skin == null) {
                return false;
            }
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, UUID unqiueId) {
        return request(playerProvider.getPlayer(player), unqiueId);
    }

    public boolean request(NmsPlayer player, UUID unqiueId) {
        Skin skin = getSkinOf(unqiueId);
        if (skin == null) {
            return false;
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String name, String playerName) {
        return request(playerProvider.getPlayer(player), name, playerName);
    }

    public boolean request(NmsPlayer player, String name, String playerName) {
        Skin skin = store.get(name);
        if (skin == null) {
            skin = getSkinOf(name, playerName);
            if (skin == null) {
                return false;
            }
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String name, UUID unqiueId) {
        return request(playerProvider.getPlayer(player), unqiueId);
    }

    public boolean request(NmsPlayer player, String name, UUID unqiueId) {
        Skin skin = store.get(name);
        if (skin == null) {
            skin = getSkinOf(name, unqiueId);
            if (skin == null) {
                return false;
            }
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String url, SkinModel model) {
        return request(playerProvider.getPlayer(player), url, model);
    }

    public boolean request(NmsPlayer player, String url, SkinModel model) {
        Skin skin = getSkinFrom(url, model);
        if (skin == null) {
            return false;
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, URL url, SkinModel model) {
        return request(playerProvider.getPlayer(player), url, model);
    }

    public boolean request(NmsPlayer player, URL url, SkinModel model) {
        Skin skin = getSkinFrom(url, model);
        if (skin == null) {
            return false;
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String url, SkinModel model, int timeout) {
        return request(playerProvider.getPlayer(player), url, model, timeout);
    }

    public boolean request(NmsPlayer player, String url, SkinModel model, int timeout) {
        Skin skin = getSkinFrom(url, model, timeout);
        if (skin == null) {
            return false;
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, URL url, SkinModel model, int timeout) {
        return request(playerProvider.getPlayer(player), url, model, timeout);
    }

    public boolean request(NmsPlayer player, URL url, SkinModel model, int timeout) {
        Skin skin = getSkinFrom(url, model, timeout);
        if (skin == null) {
            return false;
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String name, String url, SkinModel model) {
        return request(playerProvider.getPlayer(player), name, url, model);
    }

    public boolean request(NmsPlayer player, String name, String url, SkinModel model) {
        Skin skin = store.get(name);
        if (skin == null) {
            skin = getSkinFrom(url, model);
            if (skin == null) {
                return false;
            }
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String name, URL url, SkinModel model) {
        return request(playerProvider.getPlayer(player), name, url, model);
    }

    public boolean request(NmsPlayer player, String name, URL url, SkinModel model) {
        Skin skin = store.get(name);
        if (skin == null) {
            skin = getSkinFrom(url, model);
            if (skin == null) {
                return false;
            }
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String name, String url, SkinModel model, int timeout) {
        return request(playerProvider.getPlayer(player), name, url, model, timeout);
    }

    public boolean request(NmsPlayer player, String name, String url, SkinModel model, int timeout) {
        Skin skin = store.get(name);
        if (skin == null) {
            skin = getSkinFrom(url, model, timeout);
            if (skin == null) {
                return false;
            }
        }
        player.setSkin(skin);
        return true;
    }

    public boolean request(Player player, String name, URL url, SkinModel model, int timeout) {
        return request(playerProvider.getPlayer(player), name, url, model, timeout);
    }

    public boolean request(NmsPlayer player, String name, URL url, SkinModel model, int timeout) {
        Skin skin = store.get(name);
        if (skin == null) {
            skin = getSkinFrom(url, model, timeout);
            if (skin == null) {
                return false;
            }
        }
        player.setSkin(skin);
        return true;
    }

    /*
     * Profile management
     */

    public List<Profile> getProfiles() {
        return provider.getProfiles();
    }

    public Profile getUseableProfile() {
        Profile[] array = getProfiles().stream().filter(profile -> profile.isAuthenticated()).toArray(size -> new Profile[size]);
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

}