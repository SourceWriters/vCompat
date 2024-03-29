package net.sourcewriters.minecraft.vcompat.provider.impl.v1_19_R1.utils;

import java.util.function.Function;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_19_R1.entity.ArmorStand1_19_R1;

public abstract class EntityConstructors1_19_R1 {

    public static final Function<Level, ArmorStand1_19_R1> ARMOR_STAND = (world -> new ArmorStand1_19_R1(world));

}