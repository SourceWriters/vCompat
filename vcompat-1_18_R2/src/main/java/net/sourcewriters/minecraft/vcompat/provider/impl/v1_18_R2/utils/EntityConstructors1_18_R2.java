package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.utils;

import java.util.function.Function;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.entity.ArmorStand1_18_R2;

public abstract class EntityConstructors1_18_R2 {

    public static final Function<Level, ArmorStand1_18_R2> ARMOR_STAND = (world -> new ArmorStand1_18_R2(world));

}