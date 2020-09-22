package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.util.Optional;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.sourcewriters.minecraft.versiontools.skin.Skin;

public abstract class SkinTools {

	public static Skin getSkinFromPlayer(Player player) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntityHuman");

		if (checkPresence(optional0, optional1)) {
			return null;
		}

		Object entityPlayer = optional0.get().run(player, "handle");
		GameProfile profile = (GameProfile) optional1.get().getFieldValue("profile", entityPlayer);

		return getSkinFromGameProfile(profile);

	}

	public static Skin getSkinFromGameProfile(GameProfile profile) {

		PropertyMap properties = profile.getProperties();

		if (!properties.containsKey("textures")) {
			return null;
		}

		Property property = properties.get("textures").iterator().next();

		return new Skin(profile.getName(), property.getValue(), property.getSignature());

	}

}
