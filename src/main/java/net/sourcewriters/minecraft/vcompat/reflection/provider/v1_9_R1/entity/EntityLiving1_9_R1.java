package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_9_R1.entity;

import net.minecraft.server.v1_9_R1.EntityLiving;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntityLiving;

public abstract class EntityLiving1_9_R1<E extends EntityLiving> extends Entity1_9_R1<E> implements NmsEntityLiving {

    public EntityLiving1_9_R1(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}