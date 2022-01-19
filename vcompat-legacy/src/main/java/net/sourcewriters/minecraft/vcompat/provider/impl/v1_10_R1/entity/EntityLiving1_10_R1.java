package net.sourcewriters.minecraft.vcompat.provider.impl.v1_10_R1.entity;

import net.minecraft.server.v1_10_R1.EntityLiving;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityLiving;

public abstract class EntityLiving1_10_R1<E extends EntityLiving> extends Entity1_10_R1<E> implements NmsEntityLiving {

    public EntityLiving1_10_R1(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}