package net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R2.reflection;

import net.minecraft.server.v1_9_R2.NBTTagEnd;
import net.minecraft.server.v1_9_R2.NBTTagList;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerListHeaderFooter;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.Reflections;

public class NmsReflection1_9_R2 extends Reflections {

    public static NmsReflection1_9_R2 INSTANCE = new NmsReflection1_9_R2();

    private NmsReflection1_9_R2() {}

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