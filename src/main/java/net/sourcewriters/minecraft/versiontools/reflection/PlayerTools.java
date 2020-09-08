package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.PacketReflect.sendPacket;
import static net.sourcewriters.minecraft.versiontools.setup.ClassExtension.minor;
import static net.sourcewriters.minecraft.versiontools.setup.MinecraftReflect.CACHE;
import static net.sourcewriters.minecraft.versiontools.utils.java.ArrayTools.filter;
import static net.sourcewriters.minecraft.versiontools.utils.bukkit.Players.getOnline;
import static net.sourcewriters.minecraft.versiontools.utils.bukkit.Players.getOnlineWithout;

import java.lang.reflect.Array;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.sourcewriters.minecraft.versiontools.skin.Skin;

public class PlayerTools {

	public static Skin getSkinFromPlayer(Player player) {

		Object entityPlayer = CACHE.get("cbCraftPlayer").get().run(player, "handle");
		GameProfile profile = (GameProfile) CACHE.get("nmsEntityHuman").get().getFieldValue("profile", entityPlayer);

		return getSkinFromGameProfile(profile);

	}

	public static Skin getSkinFromGameProfile(GameProfile profile) {

		PropertyMap properties = profile.getProperties();

		if (!properties.containsKey("textures"))
			return null;

		Property property = properties.get("textures").iterator().next();

		return new Skin(profile.getName(), property.getValue(), property.getSignature());

	}

	/*
	 * 
	 */

	public static Player setSkin(Player player, Skin skin) {

		if (player == null || skin == null)
			return player;

		Object entityPlayer = CACHE.get("cbCraftPlayer").get().run(player, "handle");
		GameProfile profile = (GameProfile) CACHE.get("nmsEntityHuman").get().getFieldValue("profile", entityPlayer);

		PropertyMap properties = profile.getProperties();
		properties.removeAll("textures");

		Property property = new Property("textures", skin.getValue(), skin.getSignature());
		properties.put("textures", property);

		return player;

	}

	public static Player setName(Player player, String name) {

		Object entityPlayer = CACHE.get("cbCraftPlayer").get().run(player, "handle");
		GameProfile profile = (GameProfile) CACHE.get("nmsEntityHuman").get().getFieldValue("profile", entityPlayer);

		CACHE.get("mjGameProfile").get().setFieldValue(profile, "name", name);

		return player;

	}

	/*
	 * 
	 */

	public static Player fakeRespawn(Player player) {

		Object entityPlayer = CACHE.get("cbCraftPlayer").get().run(player, "handle");
		Object dimension = CACHE.get("nmsEntity").get().getFieldValue("dimension", entityPlayer);

		World world = player.getWorld();

		Object gamemode = filter(CACHE.get("nmsEnumGamemode").get().getOwner().getEnumConstants(),
			player.getGameMode().name());
		Object worldType = CACHE.get("nmsWorldType").get().run("get", (Object) world.getWorldType().getName());

		Object[] actionInfoEnums = CACHE.get("nmsEnumPlayerInfoAction").get().getOwner().getEnumConstants();
		Object enumRemovePlayerInfoAction = filter(actionInfoEnums, "REMOVE_PLAYER");
		Object enumAddPlayerInfoAction = filter(actionInfoEnums, "ADD_PLAYER");

		Object entityPlayerArray = Array.newInstance(CACHE.get("nmsEntityPlayer").get().getOwner(), 1);
		Object intArray = Array.newInstance(int.class, 1);

		Array.set(entityPlayerArray, 0, entityPlayer);
		Array.set(intArray, 0, player.getEntityId());

		Reflect refPlayerInfo = CACHE.get("nmsPacketPlayOutPlayerInfo").get();
		Object packetRemovePlayerInfo = refPlayerInfo.init("construct", enumRemovePlayerInfoAction, entityPlayerArray);
		Object packetAddPlayerInfo = refPlayerInfo.init("construct", enumAddPlayerInfoAction, entityPlayerArray);

		Object packetEntityDestroy = CACHE.get("nmsPacketPlayOutEntityDestroy").get().init("construct", intArray);
		Object packetEntitySpawn = CACHE.get("nmsPacketPlayOutNamedEntitySpawn").get().init("construct", entityPlayer);

		UUID uniqueId = player.getUniqueId();

		Player[] withoutOwner = getOnlineWithout(uniqueId);
		Player[] all = getOnline();

		sendPacket(packetRemovePlayerInfo, all);
		sendPacket(packetAddPlayerInfo, all);
		sendPacket(packetEntityDestroy, withoutOwner);
		sendPacket(packetEntitySpawn, withoutOwner);

		if (minor(13)) {
			sendPacket(CACHE
				.get("nmsPacketPlayOutRespawn")
				.get()
				.init("construct", dimension, filter(CACHE.get("nmsEnumDifficulty").get().getOwner().getEnumConstants(),
					world.getDifficulty().name()), worldType, gamemode),
				player);
		} else {
			sendPacket(CACHE.get("nmsPacketPlayOutRespawn").get().init("construct", dimension, worldType, gamemode),
				player);
		}

		return player;

	}

}
