package net.sourcewriters.minecraft.vcompat.reflection.reflect.provider;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.minecraft.vcompat.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.Reflections;

public class GeneralReflections extends Reflections {

    public static GeneralReflections INSTANCE = new GeneralReflections();

    private GeneralReflections() {}

    @Override
    public void setup(ReflectionProvider provider) {

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

        provider.createReflect("mjGameProfile", GameProfile.class).searchField("name", "name");

    }

}