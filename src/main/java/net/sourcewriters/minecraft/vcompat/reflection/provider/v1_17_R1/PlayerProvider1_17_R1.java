package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.reflection.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_17_R1.entity.Player1_17_R1;

public class PlayerProvider1_17_R1 extends PlayerProvider<VersionControl1_17_R1> {

    protected PlayerProvider1_17_R1(VersionControl1_17_R1 versionControl) {
        super(versionControl);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_17_R1(player);
    }

}