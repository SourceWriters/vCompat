package net.sourcewriters.minecraft.versiontools.skin;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.utils.minecraft.Skin;

public interface MojangProvider {

    UUID getClientIdentifier();

    void setSkinProperty(Player player, Skin skin);

    List<Profile> getProfiles();

}