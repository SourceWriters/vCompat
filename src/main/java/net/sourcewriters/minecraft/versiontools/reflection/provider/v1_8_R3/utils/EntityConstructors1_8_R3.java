package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R3.utils;

import java.util.function.Function;

import net.minecraft.server.v1_8_R3.World;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R3.entity.ArmorStand1_8_R3;

public abstract class EntityConstructors1_8_R3 {

	public static final Function<World, ArmorStand1_8_R3> ARMOR_STAND = (world -> new ArmorStand1_8_R3(world));

}
