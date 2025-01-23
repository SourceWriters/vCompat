package net.sourcewriters.minecraft.vcompat.provider.impl.v1_21_R3.utils;

import java.util.function.Function;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_21_R3.entity.ArmorStand1_21_R3;

public final class EntityConstructors1_21_R3 {
    
    private EntityConstructors1_21_R3() {
        throw new UnsupportedOperationException();
    }

    public static final Function<Level, ArmorStand1_21_R3> ARMOR_STAND = (world -> new ArmorStand1_21_R3(world));

}