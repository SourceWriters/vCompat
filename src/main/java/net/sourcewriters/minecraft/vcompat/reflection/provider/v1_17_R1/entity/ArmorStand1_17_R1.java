package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1.entity;

import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.level.World;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsArmorStand;

public class ArmorStand1_17_R1 extends EntityLiving1_17_R1<EntityArmorStand> implements NmsArmorStand {

    public ArmorStand1_17_R1(World world) {
        super(new EntityArmorStand(EntityTypes.c, world));
    }

    @Override
    public void setSmall(boolean small) {
        handle.setSmall(small);
    }

    @Override
    public boolean isSmall() {
        return handle.isSmall();
    }

}