package net.sourcewriters.minecraft.vcompat.provider.impl.v1_10_R1.utils;

import java.util.function.Function;

import net.minecraft.server.v1_10_R1.World;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_10_R1.entity.ArmorStand1_10_R1;

public abstract class EntityConstructors1_10_R1 {

    public static final Function<World, ArmorStand1_10_R1> ARMOR_STAND = (world -> new ArmorStand1_10_R1(world));

}