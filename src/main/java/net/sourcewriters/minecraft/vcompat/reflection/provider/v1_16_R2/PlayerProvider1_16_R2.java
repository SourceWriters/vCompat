package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_16_R2;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.reflection.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_16_R2.entity.Player1_16_R2;

public class PlayerProvider1_16_R2 extends PlayerProvider<VersionControl1_16_R2> {

    protected PlayerProvider1_16_R2(VersionControl1_16_R2 versionControl) {
        super(versionControl);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_16_R2(player);
    }

}