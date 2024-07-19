package net.sourcewriters.minecraft.vcompat.provider.impl.v1_19_R3.utils;

import java.util.function.Function;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_19_R3.entity.ArmorStand1_19_R3;

public abstract class EntityConstructors1_19_R3 {

    public static final Function<Level, ArmorStand1_19_R3> ARMOR_STAND = (world -> new ArmorStand1_19_R3(world));

}