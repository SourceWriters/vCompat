package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.entity;

import net.minecraft.world.entity.LivingEntity;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityLiving;

public abstract class EntityLiving1_18_R2<E extends LivingEntity> extends Entity1_18_R2<E> implements NmsEntityLiving {

    public EntityLiving1_18_R2(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}