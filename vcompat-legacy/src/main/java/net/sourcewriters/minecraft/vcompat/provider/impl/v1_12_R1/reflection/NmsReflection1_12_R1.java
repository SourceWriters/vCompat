package net.sourcewriters.minecraft.vcompat.provider.impl.v1_12_R1.reflection;

import net.minecraft.server.v1_12_R1.NBTTagEnd;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLongArray;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.Reflections;

public class NmsReflection1_12_R1 extends Reflections {

    public static NmsReflection1_12_R1 INSTANCE = new NmsReflection1_12_R1();

    private NmsReflection1_12_R1() {}

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
        provider.createReflect("nmsNBTTagLongArray", NBTTagLongArray.class).searchField("value", "b");
        provider.createReflect("nmsNBTTagList", NBTTagList.class).searchField("value", "list");

    }

}