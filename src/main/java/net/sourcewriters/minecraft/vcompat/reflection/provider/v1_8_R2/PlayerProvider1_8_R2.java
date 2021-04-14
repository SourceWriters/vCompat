package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R2;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.reflection.DataProvider;
import net.sourcewriters.minecraft.vcompat.reflection.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.reflection.data.persistence.DataDistributor;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsPlayer;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R2.entity.Player1_8_R2;

public class PlayerProvider1_8_R2 extends PlayerProvider<VersionControl1_8_R2> {

    private final DataDistributor<UUID> distributor;

    protected PlayerProvider1_8_R2(VersionControl1_8_R2 versionControl) {
        super(versionControl);
        distributor = versionControl.getDataProvider().createDistributor(new File(Bukkit.getWorlds().get(0).getWorldFolder(), "playerdata"),
            uuid -> "custom_" + uuid.toString(), DataProvider.DEFAULT_RANDOM);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_8_R2(player, distributor.get(player.getUniqueId()));
    }

}