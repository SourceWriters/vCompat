package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.utils;

import java.util.function.Function;

import net.minecraft.world.level.World;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.entity.ArmorStand1_18_R1;

public abstract class EntityConstructors1_18_R1 {

    public static final Function<World, ArmorStand1_18_R1> ARMOR_STAND = (world -> new ArmorStand1_18_R1(world));

}