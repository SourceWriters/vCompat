package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_15_R1;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.reflection.PlayerProvider;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_15_R1.entity.Player1_15_R1;

public class PlayerProvider1_15_R1 extends PlayerProvider<VersionControl1_15_R1> {

    protected PlayerProvider1_15_R1(VersionControl1_15_R1 versionControl) {
        super(versionControl);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_15_R1(player);
    }

}
