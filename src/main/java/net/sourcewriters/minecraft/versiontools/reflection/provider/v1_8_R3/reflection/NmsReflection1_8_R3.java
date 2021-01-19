package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R3.reflection;

import net.minecraft.server.v1_8_R3.NBTTagEnd;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.Reflections;

public class NmsReflection1_8_R3 extends Reflections {

	public static NmsReflection1_8_R3 INSTANCE = new NmsReflection1_8_R3();

	private NmsReflection1_8_R3() {}

	@Override
	public void setup(ReflectionProvider provider) {

		//
		//
		// Create Reflects
		//

		//
		// Minecraft

		provider.createReflect("nmsPacketPlayOutPlayerListHeaderFooter", PacketPlayOutPlayerListHeaderFooter.class).searchField("header", "a")
			.searchField("header", "b");
		
		provider.createReflect("nmsNBTTagEnd", NBTTagEnd.class);
		provider.createReflect("nmsNBTTagList", NBTTagList.class).searchField("value", "list");

	}

}