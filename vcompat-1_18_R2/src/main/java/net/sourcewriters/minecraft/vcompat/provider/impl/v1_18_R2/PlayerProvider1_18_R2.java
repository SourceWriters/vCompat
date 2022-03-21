package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.provider.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.entity.Player1_18_R2;

public class PlayerProvider1_18_R2 extends PlayerProvider<VersionControl1_18_R2> {

    protected PlayerProvider1_18_R2(VersionControl1_18_R2 versionControl) {
        super(versionControl);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_18_R2(player);
    }

}