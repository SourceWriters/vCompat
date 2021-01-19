package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3.utils;

import java.util.function.Function;

import net.minecraft.server.v1_16_R3.World;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3.entity.ArmorStand1_16_R3;

public abstract class EntityConstructors1_16_R3 {

    public static final Function<World, ArmorStand1_16_R3> ARMOR_STAND = (world -> new ArmorStand1_16_R3(world));

}