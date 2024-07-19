package net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.utils;

import java.util.function.Function;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.entity.ArmorStand1_20_R4;

public final class EntityConstructors1_20_R4 {
    
    private EntityConstructors1_20_R4() {
        throw new UnsupportedOperationException();
    }

    public static final Function<Level, ArmorStand1_20_R4> ARMOR_STAND = (world -> new ArmorStand1_20_R4(world));

}