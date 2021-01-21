package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_10_R1.reflection;

import net.minecraft.server.v1_10_R1.NBTTagEnd;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerListHeaderFooter;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.Reflections;

public class NmsReflection1_10_R1 extends Reflections {

    public static NmsReflection1_10_R1 INSTANCE = new NmsReflection1_10_R1();

    private NmsReflection1_10_R1() {}

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