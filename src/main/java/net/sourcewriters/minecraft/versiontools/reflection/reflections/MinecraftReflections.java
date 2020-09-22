package net.sourcewriters.minecraft.versiontools.reflection.reflections;

import static net.sourcewriters.minecraft.versiontools.utils.java.ReflectionTools.*;
import static net.sourcewriters.minecraft.versiontools.version.Versions.*;

import java.io.DataInputStream;
import java.io.DataOutput;

import org.bukkit.Location;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.reflection.setup.Reflections;

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
		Class<?> nmsIChatBaseComponent = provider.getNMSClass("IChatBaseComponent");
		Class<?> nmsPacketPlayInClientCommand = provider.getNMSClass("PacketPlayInClientCommand");
		Class<?> nmsPacketPlayOutTitle = provider.getNMSClass("PacketPlayOutTitle");
		Class<?> nmsEntityLiving = provider.getNMSClass("EntityLiving");
		Class<?> nmsWorld = provider.getNMSClass("World");

		//
		// NMS Reflect

		Class<?> nmsNbtCompound = provider.createNMSReflect("nmsNBTCompound", "NBTCompound").getOwner();

		
		Class<?> nmsEntityPlayer = provider
			.createNMSReflect("nmsEntityPlayer", "EntityPlayer")
			.searchField("connection", "playerConnection")
			.searchField("ping", "ping")
			.getOwner();
		Class<?> nmsWorldType = provider
			.createNMSReflect("nmsWorldType", "WorldType")
			.searchMethod("get", "getType", String.class)
			.getOwner();
		Class<?> nmsEntityHuman = provider
			.createNMSReflect("nmsEntityHuman", "EntityHuman")
			.searchField("profile", GameProfile.class)
			.getOwner();

		//
		// NMS Enum

		Class<?> nmsEnumPlayerInfoAction = provider
			.createReflect("nmsEnumPlayerInfoAction",
				subclass(provider.getNMSClass("PacketPlayOutPlayerInfo"), "EnumPlayerInfoAction"))
			.searchMethod("get", "getType", String.class)
			.getOwner();
		Class<?> nmsEnumClientCommand = provider
			.createReflect("nmsEnumClientCommand", subclass(nmsPacketPlayInClientCommand, "EnumClientCommand"))
			.getOwner();
		Class<?> nmsEnumTitleAction = provider
			.createReflect("nmsEnumTitleAction", subclass(nmsPacketPlayOutTitle, "EnumTitleAction"))
			.getOwner();

		Class<?> nmsEnumDifficulty = provider.createNMSReflect("nmsEnumDifficulty", "EnumDifficulty").getOwner();
		Class<?> nmsEnumGamemode = provider.createNMSReflect("nmsEnumGamemode", "EnumGamemode").getOwner();
		Class<?> nmsChatMessageType = provider
			.createNMSReflect("nmsChatMessageType", "ChatMessageType")
			.searchField(ignore -> minor(minor -> minor <= 12), "asByte", "a")
			.getOwner();

		//
		//
		// Create Reflects
		//

		provider
			.createNMSReflect("nmsEntity", "Entity")
			.searchMethod(ignore -> minor(minor -> minor <= 12), "setCustomName", "setCustomName", String.class)
			.searchMethod(ignore -> minor(minor -> minor > 12), "setCustomName", "setCustomName", nmsIChatBaseComponent)
			.searchMethod(ignore -> minor(minor -> minor > 9), "setGravity", "setNoGravity", boolean.class)
			.searchMethod(ignore -> minor(minor -> minor <= 9), "setGravity", "setGravity", boolean.class)
			.searchMethod("setCustomNameVisible", "setCustomNameVisible", boolean.class)
			.searchMethod("setInvisible", "setInvisible", boolean.class)
			.searchMethod("setInvulnerable", "setInvulnerable", boolean.class)
			.searchMethod("getId", "getId", int.class)
			.searchMethod("teleportTo", "teleportTo", Location.class, boolean.class)
			.searchField("dimension", "dimension");
		provider
			.createNMSReflect("nmsEntityArmorStand", "EntityArmorStand")
			.searchMethod("setSmall", "setSmall", boolean.class)
			.searchConstructor("construct", nmsWorld);
		provider
			.createNMSReflect("nmsDedicatedServer", "DedicatedServer")
			.searchMethod("setMotd", "setMotd", String.class);
		provider
			.createNMSReflect("nmsPlayerConnection", "PlayerConnection")
			.searchMethod("sendPacket", "sendPacket", nmsPacket);
		provider
			.createNMSReflect("nmsNBTTools", "NBTCompressedStreamTools")
			.searchMethod("read", "a", DataInputStream.class)
			.searchMethod("write", "a", nmsNbtCompound, DataOutput.class);
		provider
			.createNMSReflect("nmsItemStack", "ItemStack")
			.searchMethod("save", "save", nmsNbtCompound)
			.searchMethod("load", "a", nmsNbtCompound);

		//
		// Packets

		provider
			.createReflect("nmsPacketPlayOutTitle", nmsPacketPlayOutTitle)
			.searchConstructor("construct0", nmsEnumTitleAction, nmsIChatBaseComponent)
			.searchConstructor("construct1", nmsEnumTitleAction, nmsIChatBaseComponent, int.class, int.class,
				int.class);
		provider
			.createReflect("nmsPacketPlayInClientCommand", nmsPacketPlayInClientCommand)
			.searchConstructor("construct", nmsEnumClientCommand);

		provider
			.createNMSReflect("nmsPacketPlayOutPlayerListHeaderFooter", "PacketPlayOutPlayerListHeaderFooter")
			.searchField("header", "a")
			.searchField("footer", "b");
		provider
			.createNMSReflect("nmsPacketPlayOutChat", "PacketPlayOutChat")
			.searchConstructor(ignore -> minor(minor -> minor > 12), "construct", nmsIChatBaseComponent, byte.class)
			.searchConstructor(ignore -> minor(minor -> minor <= 12), "construct", nmsIChatBaseComponent,
				nmsChatMessageType);
		provider
			.createNMSReflect("nmsPacketPlayOutPlayerInfo", "PacketPlayOutPlayerInfo")
			.searchConstructor("construct", nmsEnumPlayerInfoAction, arrayclass(nmsEntityPlayer));
		provider
			.createNMSReflect("nmsPacketPlayOutEntityDestroy", "PacketPlayOutEntityDestroy")
			.searchConstructor("construct", arrayclass(int.class));
		provider
			.createNMSReflect("nmsPacketPlayOutSpawnEntityLiving", "PacketPlayOutSpawnEntityLiving")
			.searchConstructor("construct", nmsEntityLiving);
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
