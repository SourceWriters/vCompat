package net.sourcewriters.minecraft.versiontools.setup.reflections;

import static net.sourcewriters.minecraft.versiontools.utils.java.ReflectionTools.*;
import static net.sourcewriters.minecraft.versiontools.version.Versions.*;

import java.io.DataInputStream;
import java.io.DataOutput;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.minecraft.versiontools.setup.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.setup.Reflections;

public class MinecraftReflections extends Reflections {

	@Override
	public void setup(ReflectionProvider provider) {

		//
		//
		// Needed classes to create Reflects
		//

		//
		// NMS Class

		Class<?> nmsPacket = provider.getNMSClass("Packet");
		Class<?> nmsDimensionManager = provider.getNMSClass("DimensionManager");

		//
		// NMS Reflect

		Class<?> nmsNbtCompound = provider.createNMSReflect("nmsNBTCompound", "NBTCompound").getOwner();

		Class<?> nmsEntityPlayer = provider
			.createNMSReflect("nmsEntityPlayer", "EntityPlayer")
			.searchField("connection", "playerConnection")
			.getOwner();
		Class<?> nmsWorldType = provider
			.createNMSReflect("nmsWorldType", "WorldType")
			.searchMethod("get", "getType", String.class)
			.getOwner();

		Class<?> nmsEnumPlayerInfoAction = provider
			.createReflect("nmsEnumPlayerInfoAction",
				subclass(provider.getNMSClass("PacketPlayOutPlayerInfo"), "EnumPlayerInfoAction"))
			.searchMethod("get", "getType", String.class)
			.getOwner();
		Class<?> nmsEnumDifficulty = provider.createNMSReflect("nmsEnumDifficulty", "EnumDifficulty").getOwner();
		Class<?> nmsEnumGamemode = provider.createNMSReflect("nmsEnumGamemode", "EnumGamemode").getOwner();
		Class<?> nmsEntityHuman = provider
			.createNMSReflect("nmsEntityHuman", "EntityHuman")
			.searchField("profile", GameProfile.class)
			.getOwner();

		//
		// CB Reflect

		//
		//
		// Create Reflects
		//

		provider
			.createNMSReflect("nmsPlayerConnection", "PlayerConnection")
			.searchMethod("sendPacket", "sendPacket", nmsPacket);
		provider
			.createNMSReflect("nmsNBTTools", "NBTCompressedStreamTools")
			.searchMethod("read", "a", DataInputStream.class)
			.searchMethod("write", "a", nmsNbtCompound, DataOutput.class);
		provider.createNMSReflect("nmsEntity", "Entity").searchField("dimension", "dimension");
		provider
			.createNMSReflect("nmsItemStack", "ItemStack")
			.searchMethod("save", "save", nmsNbtCompound)
			.searchMethod("load", "a", nmsNbtCompound);

		//
		// Packets

		provider
			.createNMSReflect("nmsPacketPlayOutPlayerInfo", "PacketPlayOutPlayerInfo")
			.searchConstructor("construct", nmsEnumPlayerInfoAction, arrayclass(nmsEntityPlayer));
		provider
			.createNMSReflect("nmsPacketPlayOutEntityDestroy", "PacketPlayOutEntityDestroy")
			.searchConstructor("construct", arrayclass(int.class));
		provider
			.createNMSReflect("nmsPacketPlayOutNamedEntitySpawn", "PacketPlayOutNamedEntitySpawn")
			.searchConstructor("construct", nmsEntityHuman);
		provider
			.createNMSReflect("nmsPacketPlayOutPlayerInfo", "PacketPlayOutPlayerInfo")
			.searchConstructor(ignore -> minor(13), "construct", nmsDimensionManager, nmsEnumDifficulty, nmsWorldType,
				nmsEnumGamemode)
			.searchConstructor(ignore -> minor(14, 15), "construct", nmsDimensionManager, nmsWorldType,
				nmsEnumGamemode);

	}

	@Override
	public int priority() {
		return 10;
	}

}
