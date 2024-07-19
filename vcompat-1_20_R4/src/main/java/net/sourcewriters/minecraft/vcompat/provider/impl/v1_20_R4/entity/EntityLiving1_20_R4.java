package net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.entity;

import net.minecraft.world.entity.LivingEntity;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityLiving;

public abstract class EntityLiving1_20_R4<E extends LivingEntity> extends Entity1_20_R4<E> implements NmsEntityLiving {

    public EntityLiving1_20_R4(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}