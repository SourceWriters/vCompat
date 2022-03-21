package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsArmorStand;

public class ArmorStand1_18_R2 extends EntityLiving1_18_R2<ArmorStand> implements NmsArmorStand {

	public ArmorStand1_18_R2(Level world) {
		super(new ArmorStand(EntityType.ARMOR_STAND, world));
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