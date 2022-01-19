package net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R2.utils;

import java.util.function.Function;

import net.minecraft.server.v1_9_R2.World;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R2.entity.ArmorStand1_9_R2;

public abstract class EntityConstructors1_9_R2 {

    public static final Function<World, ArmorStand1_9_R2> ARMOR_STAND = (world -> new ArmorStand1_9_R2(world));

}