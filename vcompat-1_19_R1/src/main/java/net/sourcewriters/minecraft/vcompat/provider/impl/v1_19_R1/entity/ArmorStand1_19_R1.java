package net.sourcewriters.minecraft.vcompat.provider.impl.v1_19_R1.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsArmorStand;

public class ArmorStand1_19_R1 extends EntityLiving1_19_R1<ArmorStand> implements NmsArmorStand {

	public ArmorStand1_19_R1(Level world) {
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