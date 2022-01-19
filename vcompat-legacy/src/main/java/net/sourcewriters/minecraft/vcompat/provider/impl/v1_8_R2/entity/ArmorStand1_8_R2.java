package net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R2.entity;

import net.minecraft.server.v1_8_R2.EntityArmorStand;
import net.minecraft.server.v1_8_R2.World;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsArmorStand;

public class ArmorStand1_8_R2 extends EntityLiving1_8_R2<EntityArmorStand> implements NmsArmorStand {

    public ArmorStand1_8_R2(World world) {
        super(new EntityArmorStand(world));
    }

    @Override
    public boolean hasGravity() {
        return handle.hasGravity();
    }

    @Override
    public void setGravity(boolean gravity) {
        handle.setGravity(gravity);
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