package net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R1;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.provider.impl.v1_8_R1.entity.Player1_8_R1;
import net.sourcewriters.minecraft.vcompat.reflection.DataProvider;
import net.sourcewriters.minecraft.vcompat.reflection.PlayerProvider;
import net.sourcewriters.minecraft.vcompat.reflection.data.persistence.DataDistributor;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsPlayer;

public class PlayerProvider1_8_R1 extends PlayerProvider<VersionControl1_8_R1> {

    private final DataDistributor<UUID> distributor;

    protected PlayerProvider1_8_R1(VersionControl1_8_R1 versionControl) {
        super(versionControl);
        distributor = versionControl.getDataProvider().createDistributor(new File(Bukkit.getWorlds().get(0).getWorldFolder(), "playerdata"),
            uuid -> "custom_" + uuid.toString(), DataProvider.DEFAULT_RANDOM);
    }

    @Override
    protected NmsPlayer createPlayer(Player player) {
        return new Player1_8_R1(player, distributor.get(player.getUniqueId()));
    }

}