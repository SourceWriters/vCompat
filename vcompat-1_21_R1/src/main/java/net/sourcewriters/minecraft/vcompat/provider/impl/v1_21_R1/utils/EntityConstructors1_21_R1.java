package net.sourcewriters.minecraft.vcompat.provider.impl.v1_21_R1.utils;

import java.util.function.Function;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_21_R1.entity.ArmorStand1_21_R1;

public final class EntityConstructors1_21_R1 {
    
    private EntityConstructors1_21_R1() {
        throw new UnsupportedOperationException();
    }

    public static final Function<Level, ArmorStand1_21_R1> ARMOR_STAND = (world -> new ArmorStand1_21_R1(world));

}