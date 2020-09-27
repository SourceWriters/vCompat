package net.sourcewriters.minecraft.versiontools.reflection.tools;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.sourcewriters.minecraft.versiontools.skin.Skin;

public abstract class SkinTools {

	public abstract Skin skinFromPlayer(Player player);

	public Skin skinFromGameProfile(GameProfile profile) {
		PropertyMap properties = profile.getProperties();
		if (!properties.containsKey("textures")) {
			return null;
		}

		Property property = properties.get("textures").iterator().next();
		return new Skin(profile.getName(), property.getValue(), property.getSignature());
	}

}
