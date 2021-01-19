package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.entity;

import net.minecraft.server.v1_14_R1.EntityLiving;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntityLiving;

public abstract class EntityLiving1_14_R1<E extends EntityLiving> extends Entity1_14_R1<E> implements NmsEntityLiving {

	public EntityLiving1_14_R1(E handle) {
		super(handle);
	}

	@Override
	public void setCollidable(boolean collidable) {
		handle.collides = collidable;
	}

}
