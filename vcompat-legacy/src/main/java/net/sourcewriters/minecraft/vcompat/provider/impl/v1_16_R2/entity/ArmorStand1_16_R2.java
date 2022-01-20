package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R2.entity;

import net.minecraft.server.v1_16_R2.EntityArmorStand;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsArmorStand;

public class ArmorStand1_16_R2 extends EntityLiving1_16_R2<EntityArmorStand> implements NmsArmorStand {

    public ArmorStand1_16_R2(World world) {
        super(new EntityArmorStand(EntityTypes.ARMOR_STAND, world));
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