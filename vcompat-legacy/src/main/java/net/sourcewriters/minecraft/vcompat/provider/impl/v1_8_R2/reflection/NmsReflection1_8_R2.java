package net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R2.reflection;

import net.minecraft.server.v1_8_R2.NBTTagEnd;
import net.minecraft.server.v1_8_R2.NBTTagList;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerListHeaderFooter;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookups;

public class NmsReflection1_8_R2 extends ClassLookups {

    public static NmsReflection1_8_R2 INSTANCE = new NmsReflection1_8_R2();

    private NmsReflection1_8_R2() {}

    @Override
    public void setup(ClassLookupProvider provider) {

        //
        //
        // Create Reflects
        //

        //
        // Minecraft

        provider.createLookup("nmsPacketPlayOutPlayerListHeaderFooter", PacketPlayOutPlayerListHeaderFooter.class)
            .searchField("header", "a").searchField("header", "b");

        provider.createLookup("nmsNBTTagEnd", NBTTagEnd.class);
        provider.createLookup("nmsNBTTagList", NBTTagList.class).searchField("value", "list");

    }

}