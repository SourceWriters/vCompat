package net.sourcewriters.minecraft.versiontools.setup;

import static net.sourcewriters.minecraft.versiontools.setup.ClassExtension.*;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.lang.reflect.Array;

import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;
import com.syntaxphoenix.syntaxapi.reflection.ReflectCache;

public class MinecraftReflect {

	public static final ReflectCache CACHE = new ReflectCache();

	/*
	 * 
	 */

	static {

		//
		//
		// Needed classes to create Reflects
		//

		//
		// NMS Class

		Class<?> nmsPacket = nms("Packet");
		Class<?> nmsDimensionManager = nms("DimensionManager");

		//
		// NMS Reflect

		Class<?> nmsNbtCompound = CACHE.create("nmsNBTCompound", nms("NBTCompound")).getOwner();

		Class<?> nmsItemStack = CACHE
			.create("nmsItemStack", nms("ItemStack"))
			.searchMethod("save", "save", nmsNbtCompound)
			.searchMethod("load", "a", nmsNbtCompound)
			.getOwner();
		Class<?> nmsEntityPlayer = CACHE
			.create("nmsEntityPlayer", nms("EntityPlayer"))
			.searchField("connection", "playerConnection")
			.getOwner();
		Class<?> nmsWorldType = CACHE
			.create("nmsWorldType", nms("WorldType"))
			.searchMethod("get", "getType", String.class)
			.getOwner();

		Class<?> nmsEnumPlayerInfoAction = CACHE
			.create("nmsEnumPlayerInfoAction", search(nms("PacketPlayOutPlayerInfo"), "EnumPlayerInfoAction"))
			.getOwner();
		Class<?> nmsEnumDifficulty = CACHE.create("nmsEnumDifficulty", nms("EnumDifficulty")).getOwner();
		Class<?> nmsEnumGamemode = CACHE.create("nmsEnumGamemode", nms("EnumGamemode")).getOwner();
		Class<?> nmsEntityHuman = CACHE
			.create("nmsEntityHuman", nms("EntityHuman"))
			.searchField("profile", GameProfile.class)
			.getOwner();

		//
		// CB

		//
		//
		// Create Reflects
		//

		//
		// Mojang

		CACHE.create("mjGameProfile", GameProfile.class).searchField("name", "name");

		//
		// NMS

		CACHE
			.create("nmsPlayerConnection", nms("PlayerConnection"))
			.searchMethod("sendPacket", "sendPacket", nmsPacket);
		CACHE
			.create("nmsNBTTools", nms("NBTCompressedStreamTools"))
			.searchMethod("read", "a", DataInputStream.class)
			.searchMethod("write", "a", nmsNbtCompound, DataOutput.class);
		CACHE.create("nmsEntity", nms("Entity")).searchField("dimension", "dimension");

		//
		// NMS Packet

		CACHE
			.create("nmsPacketPlayOutPlayerInfo", nms("PacketPlayOutPlayerInfo"))
			.searchConstructor("construct", nmsEnumPlayerInfoAction, array(nmsEntityPlayer));
		CACHE
			.create("nmsPacketPlayOutEntityDestroy", nms("PacketPlayOutEntityDestroy"))
			.searchConstructor("construct", array(int.class));
		CACHE
			.create("nmsPacketPlayOutNamedEntitySpawn", nms("PacketPlayOutNamedEntitySpawn"))
			.searchConstructor("construct", nmsEntityHuman);
		CACHE
			.create("nmsPacketPlayOutRespawn", nms("PacketPlayOutRespawn"))
			.searchConstructor(ignore -> minor(13), "construct", nmsDimensionManager, nmsEnumDifficulty, nmsWorldType,
				nmsEnumGamemode)
			.searchConstructor(ignore -> minor(14, 15), patch("construct"), nmsDimensionManager, nmsWorldType,
				nmsEnumGamemode);

		//
		// CB
		//

		CACHE.create("cbCraftPlayer", cb("entity.CraftPlayer")).searchMethod("handle", "getHandle");
		CACHE
			.create("cbItemStack", cb("inventory.CraftItemStack"))
			.searchMethod("nms", "asNMSCopy", ItemStack.class)
			.searchMethod("bukkit", "asBukkitCopy", nmsItemStack);

	}

	/*
	 * 
	 */

	private static Class<?> nms(String name) {
		return getMinecraftClass(name);
	}

	private static Class<?> cb(String name) {
		return getCraftbukkitClass(name);
	}

	/*
	 * 
	 */

	private static Class<?> search(Class<?> clazz, String name) {
		for (Class<?> search : clazz.getClasses())
			if (search.getSimpleName().split("\\.")[0].equals(name))
				return search;
		return null;
	}

	private static Class<?> array(Class<?> clazz) {
		return Array.newInstance(clazz, 1).getClass();
	}

}
