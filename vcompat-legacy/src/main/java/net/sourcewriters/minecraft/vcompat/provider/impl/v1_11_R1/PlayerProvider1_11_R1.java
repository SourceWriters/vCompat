package net.sourcewriters.minecraft.vcompat.provider.impl.v1_11_R1;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_11_R1.entity.Player1_11_R1;
import net.sourcewriters.minecraft.vcompat.provider.DataProvider;
import net.sourcewriters.minecraft.vcompat.provider.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.provider.data.persistence.DataDistributor;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;

public class PlayerProvider1_11_R1 extends PlayerProvider<VersionControl1_11_R1> {

    private final DataDistributor<UUID> distributor;

    protected PlayerProvider1_11_R1(VersionControl1_11_R1 versionControl) {
        super(versionControl);
        distributor = versionControl.getDataProvider().createDistributor(new File(Bukkit.getWorlds().get(0).getWorldFolder(), "playerdata"),
            uuid -> "custom_" + uuid.toString(), DataProvider.DEFAULT_RANDOM);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_11_R1(player, distributor.get(player.getUniqueId()));
    }

}