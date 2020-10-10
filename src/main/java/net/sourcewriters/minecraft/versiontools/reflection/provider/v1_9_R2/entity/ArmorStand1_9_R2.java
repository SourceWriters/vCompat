package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_9_R2.entity;

import net.minecraft.server.v1_9_R2.EntityArmorStand;
import net.minecraft.server.v1_9_R2.EntityTypes;
import net.minecraft.server.v1_9_R2.World;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsArmorStand;

public class ArmorStand1_9_R2 extends EntityLiving1_9_R2<EntityArmorStand> implements NmsArmorStand {

	public ArmorStand1_9_R2(World world) {
		super(new EntityArmorStand(EntityTypes.ARMOR_STAND, world));
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
