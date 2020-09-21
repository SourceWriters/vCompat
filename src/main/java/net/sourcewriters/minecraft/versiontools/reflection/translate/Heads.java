package net.sourcewriters.minecraft.versiontools.reflection.translate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.syntaxphoenix.syntaxapi.random.Keys;

/**
 * @author Lauriichen
 */
public class Heads {

	public final static String textureId = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
//	private final static Map<String, ItemStack> heads = new HashMap<>();
//	private final static Map<UUID, ItemStack> pheads = new HashMap<>();
//	private final static Map<String, ItemStack> nheads = new HashMap<>();
//	public final static Map<String, GameProfile> blockHeads = new HashMap<>();

	public static GameProfile toBlockProfile(String textureValue) {
//		if (blockHeads.containsKey(textureValue)) {
//			return blockHeads.get(textureValue);
//		}
		GameProfile profile = new GameProfile(UUID.randomUUID(), Keys.generateKey(10));
		profile.getProperties().put("textures", new Property("textures", textureId + textureValue));
//		blockHeads.put(textureValue, profile);
		return profile;
	}

//	public static String shortTexture(String texture) {
//		return texture.replace(textureId, "");
//	}
//
//	public static String textureFromValue(String value) {
//		return textureId + value;
//	}
//
//	public static ItemStack createItem(HeadValue value) {
//		return createItem(value.texture());
//	}

	public static ItemStack createItem(String textureValue) {
		ItemStack st = new ItemStack(/* TODO: ADD MATERIAL HERE */ Material.AIR);
		SkullMeta meta = (SkullMeta) st.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), Keys.generateKey(10));
		profile.getProperties().put("textures", new Property("textures", textureId + textureValue));
		try {
			Field profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, profile);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			return st;
		}
		st.setItemMeta(meta);
		return st;
	}

//	@SuppressWarnings("deprecation")
//	public static ItemStack getPlayerHead(Player play) {
//		UUID id = play.getUniqueId();
//		if (!pheads.containsKey(id)) {
//			ItemStack is = new ItemStack(RWGMaterial.HEAD_ITEM.get());
//			SkullMeta im = (SkullMeta) is.getItemMeta();
//			im.setOwner(play.getName());
//			is.setItemMeta(im);
//			pheads.put(id, is);
//			return is.clone();
//		} else {
//			return pheads.get(id).clone();
//		}
//	}
//
//	@SuppressWarnings("deprecation")
//	public static ItemStack getPlayerHeadByName(String name) {
//		if (!nheads.containsKey(name)) {
//			ItemStack is = new ItemStack(RWGMaterial.HEAD_ITEM.get());
//			SkullMeta im = (SkullMeta) is.getItemMeta();
//			im.setOwner(name);
//			is.setItemMeta(im);
//			nheads.put(name, is);
//			return is.clone();
//		} else {
//			return nheads.get(name).clone();
//		}
//	}

}
