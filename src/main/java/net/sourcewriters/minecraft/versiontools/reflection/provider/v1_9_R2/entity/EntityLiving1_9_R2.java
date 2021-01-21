package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_9_R2.entity;

import net.minecraft.server.v1_9_R2.EntityLiving;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntityLiving;

public abstract class EntityLiving1_9_R2<E extends EntityLiving> extends Entity1_9_R2<E> implements NmsEntityLiving {

    public EntityLiving1_9_R2(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.collides = collidable;
    }

}