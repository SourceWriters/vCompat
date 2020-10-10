package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_13_R2.tools;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.reflection.tools.SkinTools;
import net.sourcewriters.minecraft.versiontools.skin.Skin;

public class SkinTools1_13_R2 extends SkinTools {

	@Override
	public Skin skinFromPlayer(Player player) {
		return skinFromGameProfile(((CraftPlayer) player).getHandle().getProfile());
	}

}
