package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R1.entity;

import net.minecraft.world.entity.LivingEntity;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityLiving;

public abstract class EntityLiving1_17_R1<E extends LivingEntity> extends Entity1_17_R1<E> implements NmsEntityLiving {

    public EntityLiving1_17_R1(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}