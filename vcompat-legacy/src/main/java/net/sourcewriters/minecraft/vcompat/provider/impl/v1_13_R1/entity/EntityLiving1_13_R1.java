package net.sourcewriters.minecraft.vcompat.provider.impl.v1_13_R1.entity;

import net.minecraft.server.v1_13_R1.EntityLiving;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntityLiving;

public abstract class EntityLiving1_13_R1<E extends EntityLiving> extends Entity1_13_R1<E> implements NmsEntityLiving {

    public EntityLiving1_13_R1(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}