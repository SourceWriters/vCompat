package net.sourcewriters.minecraft.versiontools.reflection.reflect.provider;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.minecraft.versiontools.reflection.reflect.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.reflection.reflect.Reflections;

public class GeneralReflections extends Reflections {

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