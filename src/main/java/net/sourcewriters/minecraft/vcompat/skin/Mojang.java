package net.sourcewriters.minecraft.vcompat.skin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.syntaxphoenix.syntaxapi.net.http.Request;
import com.syntaxphoenix.syntaxapi.net.http.RequestType;
import com.syntaxphoenix.syntaxapi.net.http.StandardContentType;

import net.sourcewriters.minecraft.vcompat.reflection.VersionControl;
import net.sourcewriters.minecraft.vcompat.utils.minecraft.MojangProfileServer;
import net.sourcewriters.minecraft.vcompat.utils.minecraft.Skin;

public class Mojang {

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

    public boolean request(Player player, String name, UUID uniqueId) {
        Skin skin = getSkin((name = name.toLowerCase()));

        if (skin == null) {
            if ((skin = MojangProfileServer.getSkin(uniqueId)) == null) {
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
        VersionControl.get().getPlayerProvider().getPlayer(player).setSkin(skin);
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

    public boolean setSkin(Skin skin) {
        if (getOptionalSkin(skin.getName()).isPresent()) {
            return false;
        }
        return skins.add(skin);
    }

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
            int code = request.execute(String.format(URL_SKIN_UPLOAD, profile.getUniqueId()), StandardContentType.URL_ENCODED).getCode();
            if (code != 204) {
                return null;
            }
            return MojangProfileServer.getSkinShorten(skinRequest.getName(), profile.getUniqueId());
        } catch (IOException ignore) {

        }
        return null;
    }

}