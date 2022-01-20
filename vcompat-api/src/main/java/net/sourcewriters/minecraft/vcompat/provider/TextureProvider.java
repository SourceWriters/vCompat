package net.sourcewriters.minecraft.vcompat.provider;

import static net.sourcewriters.minecraft.vcompat.util.constants.MinecraftConstants.TEXTURE_SIGNATURE;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.random.Keys;

public abstract class TextureProvider<V extends VersionControl> extends VersionHandler<V> {

    protected final Map<String, GameProfile> textures = Collections.synchronizedMap(new HashMap<>());
    protected final Keys random = new Keys();

    protected TextureProvider(V versionControl) {
        super(versionControl);
    }

    protected final GameProfile generateProfile() {
        return new GameProfile(UUID.randomUUID(), random.makeKey(12));
    }

    protected final void applyTexture(GameProfile profile, String texture) {
        PropertyMap map = profile.getProperties();
        map.removeAll("textures");
        map.put("textures", new Property("textures", TEXTURE_SIGNATURE + texture));
    }

    public String textureFromProfile(GameProfile profile) {
        return profile.getProperties().get("textures").iterator().next().getValue();
    }

    public String textureFromBlock(Block block) {
        return textureFromProfile(profileFromBlock(block));
    }

    public String textureFromItem(ItemStack itemStack) {
        return textureFromProfile(profileFromItem(itemStack));
    }

    public GameProfile getCacheProfile(String texture) {
        texture = texture.replace(TEXTURE_SIGNATURE, "");
        if (textures.containsKey(texture)) {
            return textures.get(texture);
        }
        GameProfile profile = profileFromTexture(texture);
        textures.put(texture, profile);
        return profile;
    }

    public GameProfile getCacheProfile(Block block) {
        GameProfile profile = profileFromBlock(block);
        return textures.containsValue(profile) ? profile : getCacheProfile(textureFromProfile(profile));
    }

    public GameProfile getCacheProfile(ItemStack itemStack) {
        GameProfile profile = profileFromItem(itemStack);
        return textures.containsValue(profile) ? profile : getCacheProfile(textureFromProfile(profile));
    }

    public GameProfile profileFromTexture(String texture) {
        GameProfile profile = generateProfile();
        applyTexture(profile, texture);
        return profile;
    }

    public abstract GameProfile profileFromBlock(Block block);

    public abstract GameProfile profileFromItem(ItemStack itemStack);

    public GameProfile removeProfile(String texture) {
        return textures.remove(texture);
    }

    public ItemStack getItem(String texture) {
        return getItem(getCacheProfile(texture));
    }

    public abstract ItemStack getItem(GameProfile profile);

    public boolean applyItem(ItemStack itemStack, String texture) {
        return applyItem(itemStack, getCacheProfile(texture));
    }

    public abstract boolean applyItem(ItemStack itemStack, GameProfile profile);

    public boolean applyBlock(Block block, String texture) {
        return applyBlock(block, getCacheProfile(texture));
    }

    public abstract boolean applyBlock(Block block, GameProfile profile);

}