package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.version.Versions.*;
import static net.sourcewriters.minecraft.versiontools.reflection.PacketTools.sendPacket;
import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.ArrayTools.filter;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;
import static net.sourcewriters.minecraft.versiontools.utils.bukkit.Players.getOnline;
import static net.sourcewriters.minecraft.versiontools.utils.bukkit.Players.getOnlineWithout;

import java.lang.reflect.Array;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.sourcewriters.minecraft.versiontools.skin.Skin;
import net.sourcewriters.minecraft.versiontools.utils.java.ArrayTools;

public abstract class PlayerTools {
	
	/*
	 * Getter
	 */

	public static int getPing(Player player) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntityPlayer");

		if (checkPresence(optional0, optional1)) {
			return -1;
		}

		Object entityPlayer = optional0.get().run(player, "handle");

		return (int) optional1.get().run(entityPlayer, "ping");

	}

	/*
	 * Setter
	 */

	public static Player setSkin(Player player, Skin skin) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntityHuman");

		if (checkPresence(optional0, optional1)) {
			return null;
		}

		if (player == null || skin == null) {
			return player;
		}

		Object entityPlayer = optional0.get().run(player, "handle");
		GameProfile profile = (GameProfile) optional1.get().getFieldValue("profile", entityPlayer);

		PropertyMap properties = profile.getProperties();
		properties.removeAll("textures");

		Property property = new Property("textures", skin.getValue(), skin.getSignature());
		properties.put("textures", property);

		return player;

	}

	public static Player setName(Player player, String name) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntityHuman");
		Optional<Reflect> optional2 = DEFAULT.getOptionalReflect("mjGameProfile");

		if (checkPresence(optional0, optional1, optional2)) {
			return null;
		}

		Object entityPlayer = optional0.get().run(player, "handle");
		GameProfile profile = (GameProfile) optional1.get().getFieldValue("profile", entityPlayer);

		optional2.get().setFieldValue(profile, "name", name);

		return player;

	}

	/*
	 * Respawn
	 */

	public static Player respawn(Player player) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsEnumClientCommand");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsPacketPlayInClientCommand");

		if (checkPresence(optional0, optional1)) {
			return null;
		}

		Object clientCommand = optional0
			.map(reflect -> ArrayTools.filter(reflect.getOwner().getEnumConstants(), "PERFORM_RESPAWN"))
			.get();

		Object clientCommandPacket = optional1.get().init("construct", clientCommand);

		sendPacket(clientCommandPacket);

		return player;

	}

	public static Player fakeRespawn(Player player) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftPlayer");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEntity");
		Optional<Reflect> optional2 = DEFAULT.getOptionalReflect("nmsEnumGamemode");
		Optional<Reflect> optional3 = DEFAULT.getOptionalReflect("nmsWorldType");
		Optional<Reflect> optional4 = DEFAULT.getOptionalReflect("nmsEntityPlayer");
		Optional<Reflect> optional5 = DEFAULT.getOptionalReflect("nmsEnumPlayerInfoAction");
		Optional<Reflect> optional6 = DEFAULT.getOptionalReflect("nmsPacketPlayOutEntityDestroy");
		Optional<Reflect> optional7 = DEFAULT.getOptionalReflect("nmsPacketPlayOutNamedEntitySpawn");
		Optional<Reflect> optional8 = DEFAULT.getOptionalReflect("nmsPacketPlayOutRespawn");

		if (checkPresence(optional0, optional1, optional2, optional3, optional4, optional5, optional6, optional7,
			optional8)) {
			return null;
		}

		Object entityPlayer = optional0.get().run(player, "handle");
		Object dimension = optional1.get().getFieldValue("dimension", entityPlayer);

		World world = player.getWorld();

		Object gamemode = filter(optional2.get().getOwner().getEnumConstants(), player.getGameMode().name());
		Object worldType = optional3.get().run("get", (Object) world.getWorldType().getName());

		Object[] actionInfoEnums = optional5.get().getOwner().getEnumConstants();
		Object enumRemovePlayerInfoAction = filter(actionInfoEnums, "REMOVE_PLAYER");
		Object enumAddPlayerInfoAction = filter(actionInfoEnums, "ADD_PLAYER");

		Object entityPlayerArray = Array.newInstance(optional4.get().getOwner(), 1);
		Object intArray = Array.newInstance(int.class, 1);

		Array.set(entityPlayerArray, 0, entityPlayer);
		Array.set(intArray, 0, player.getEntityId());

		Reflect refPlayerInfo = optional5.get();
		Object packetRemovePlayerInfo = refPlayerInfo.init("construct", enumRemovePlayerInfoAction, entityPlayerArray);
		Object packetAddPlayerInfo = refPlayerInfo.init("construct", enumAddPlayerInfoAction, entityPlayerArray);

		Object packetEntityDestroy = optional6.get().init("construct", intArray);
		Object packetEntitySpawn = optional7.get().init("construct", entityPlayer);

		UUID uniqueId = player.getUniqueId();

		Player[] withoutOwner = getOnlineWithout(uniqueId);
		Player[] all = getOnline();

		sendPacket(packetRemovePlayerInfo, all);
		sendPacket(packetAddPlayerInfo,

			all);
		sendPacket(packetEntityDestroy,

			withoutOwner);
		sendPacket(packetEntitySpawn, withoutOwner);

		if (minor(13)) {
			sendPacket(optional8
				.get()
				.init("construct", dimension,
					filter(DEFAULT.getOptionalReflect("nmsEnumDifficulty").get().getOwner().getEnumConstants(),
						world.getDifficulty().name()),
					worldType, gamemode),
				player);
		} else {
			sendPacket(optional8.get().init("construct", dimension, worldType, gamemode), player);
		}

		return player;

	}

	/*
	 * 
	 */
	
	public static void setPlayerlistHeaderAndFooter(Player player, String header, String footer) {
		
		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsPacketPlayOutPlayerListHeaderFooter");

		if (checkPresence(optional0, optional1)) {
			return;
		}
		
		Reflect craftChatMessageRef = optional0.get();
		Reflect packetRef = optional1.get();
		
		Object headerComponent = header.trim().isEmpty() ? null : craftChatMessageRef.run("fromString0", (Object) header);
		Object footerComponent = footer.trim().isEmpty() ? null : craftChatMessageRef.run("fromString0", (Object) footer);
		
		Object packet = packetRef.init();
		
		packetRef.setFieldValue(packet, "header", headerComponent);
		packetRef.setFieldValue(packet, "footer", footerComponent);
		
		sendPacket(packet, player);
		
	}

	public static void sendActionBar(Player player, String message) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("cbCraftChatMessage");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsChatMessageType");
		Optional<Reflect> optional2 = DEFAULT.getOptionalReflect("nmsPacketPlayOutChat");

		if (checkPresence(optional0, optional1, optional2)) {
			return;
		}

		Reflect chatMessageTypeRef = optional1.get();

		Object component = optional0.get().run("fromString0", (Object) message);
		Object messageType = filter(chatMessageTypeRef.getOwner().getEnumConstants(), "GAME_INFO");

		Object packet = optional2
			.get()
			.init("construct",
				minor(minor -> minor > 12) ? messageType : chatMessageTypeRef.getFieldValue("asByte", component));

		sendPacket(packet, player);

	}
	
	/*
	 * 
	 */

}
