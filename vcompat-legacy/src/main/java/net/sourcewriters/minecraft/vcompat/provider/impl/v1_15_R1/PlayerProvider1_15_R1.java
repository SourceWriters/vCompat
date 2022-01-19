package net.sourcewriters.minecraft.vcompat.provider.impl.v1_15_R1;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_15_R1.entity.Player1_15_R1;
import net.sourcewriters.minecraft.vcompat.provider.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;

public class PlayerProvider1_15_R1 extends PlayerProvider<VersionControl1_15_R1> {

    protected PlayerProvider1_15_R1(VersionControl1_15_R1 versionControl) {
        super(versionControl);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_15_R1(player);
    }

}