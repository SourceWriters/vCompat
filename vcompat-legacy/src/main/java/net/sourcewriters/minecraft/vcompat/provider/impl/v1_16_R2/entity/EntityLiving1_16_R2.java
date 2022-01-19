package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R2.entity;

import net.minecraft.server.v1_16_R2.EntityLiving;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntityLiving;

public abstract class EntityLiving1_16_R2<E extends EntityLiving> extends Entity1_16_R2<E> implements NmsEntityLiving {

    public EntityLiving1_16_R2(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}