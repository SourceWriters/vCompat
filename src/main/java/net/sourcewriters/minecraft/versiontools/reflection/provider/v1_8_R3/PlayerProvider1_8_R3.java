package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R3;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.reflection.PlayerProvider;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_8_R3.entity.Player1_8_R3;

public class PlayerProvider1_8_R3 extends PlayerProvider<VersionControl1_8_R3> {

	protected PlayerProvider1_8_R3(VersionControl1_8_R3 versionControl) {
		super(versionControl);
	}

	@Override
	protected NmsPlayer createPlayer(Player player) {
		return new Player1_8_R3(player);
	}

}
