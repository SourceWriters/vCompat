package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.World;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsArmorStand;

public class ArmorStand1_12_R1 extends EntityLiving1_12_R1<EntityArmorStand> implements NmsArmorStand {

    public ArmorStand1_12_R1(World world) {
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