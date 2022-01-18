package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.entity;

import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.level.World;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsArmorStand;

public class ArmorStand1_18_R1 extends EntityLiving1_18_R1<EntityArmorStand> implements NmsArmorStand {

    public ArmorStand1_18_R1(World world) {
        super(new EntityArmorStand(EntityTypes.c, world));
    }

    @Override
    public void setSmall(boolean small) {
        handle.a(small);
    }

    @Override
    public boolean isSmall() {
        return handle.n();
    }

}