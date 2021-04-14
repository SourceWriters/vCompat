package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R1.reflection;

import net.minecraft.server.v1_8_R1.NBTTagEnd;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.Reflections;

public class NmsReflection1_8_R1 extends Reflections {

    public static NmsReflection1_8_R1 INSTANCE = new NmsReflection1_8_R1();

    private NmsReflection1_8_R1() {}

    @Override
    public void setup(ReflectionProvider provider) {

        //
        //
        // Create Reflects
        //

        //
        // Minecraft

        provider.createReflect("nmsPacketPlayOutPlayerListHeaderFooter", PacketPlayOutPlayerListHeaderFooter.class)
            .searchField("header", "a").searchField("header", "b");

        provider.createReflect("nmsNBTTagEnd", NBTTagEnd.class);
        provider.createReflect("nmsNBTTagList", NBTTagList.class).searchField("value", "list");

    }

}