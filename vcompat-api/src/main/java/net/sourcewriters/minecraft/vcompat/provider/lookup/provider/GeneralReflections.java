package net.sourcewriters.minecraft.vcompat.provider.lookup.provider;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookups;

public class GeneralReflections extends ClassLookups {

    public static GeneralReflections INSTANCE = new GeneralReflections();

    private GeneralReflections() {}

    @Override
    public void setup(ClassLookupProvider provider) {

        //
        //
        // Needed classes to create Reflects
        //

        //
        //
        // Create Reflects
        //

        //
        // Mojang

        provider.createLookup("mjGameProfile", GameProfile.class).searchField("name", "name");

    }

}