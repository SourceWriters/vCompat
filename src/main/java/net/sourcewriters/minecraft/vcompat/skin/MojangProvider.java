package net.sourcewriters.minecraft.vcompat.skin;

import java.util.List;
import java.util.UUID;

public interface MojangProvider {

    UUID getClientIdentifier();

    List<Profile> getProfiles();

}