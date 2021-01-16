package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsArmorStand;

public class ArmorStand1_16_R3 extends EntityLiving1_16_R3<EntityArmorStand> implements NmsArmorStand {

	public ArmorStand1_16_R3(World world) {
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