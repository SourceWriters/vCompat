package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.entity.Player1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;

public class PlayerProvider1_16_R3 extends PlayerProvider<VersionControl1_16_R3> {

    protected PlayerProvider1_16_R3(VersionControl1_16_R3 versionControl) {
        super(versionControl);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_16_R3(player);
    }

}