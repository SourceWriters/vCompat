package net.sourcewriters.minecraft.vcompat.provider.impl.v1_13_R1.entity;

import net.minecraft.server.v1_13_R1.EntityArmorStand;
import net.minecraft.server.v1_13_R1.World;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsArmorStand;

public class ArmorStand1_13_R1 extends EntityLiving1_13_R1<EntityArmorStand> implements NmsArmorStand {

    public ArmorStand1_13_R1(World world) {
        super(new EntityArmorStand(world));
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