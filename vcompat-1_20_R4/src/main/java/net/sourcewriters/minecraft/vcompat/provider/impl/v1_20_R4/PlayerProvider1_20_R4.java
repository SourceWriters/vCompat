package net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.provider.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.entity.Player1_20_R4;

public class PlayerProvider1_20_R4 extends PlayerProvider<VersionControl1_20_R4> {

    protected PlayerProvider1_20_R4(VersionControl1_20_R4 versionControl) {
        super(versionControl);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_20_R4(player);
    }

}