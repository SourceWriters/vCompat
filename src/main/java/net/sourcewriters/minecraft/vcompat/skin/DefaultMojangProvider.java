package net.sourcewriters.minecraft.vcompat.skin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class DefaultMojangProvider implements MojangProvider {

    private final UUID identifier;

    private final ArrayList<Profile> profiles = new ArrayList<>();

    public DefaultMojangProvider(UUID identifier) {
        this.identifier = identifier;
    }

    @Override
    public UUID getClientIdentifier() {
        return identifier;
    }

    @Override
    public List<Profile> getProfiles() {
        return new ArrayList<>(profiles);
    }

    @Override
    public Profile create(String username, String password) {
        Profile profile = new Profile(this, username, password);
        profiles.add(profile);
        return profile;
    }

    @Override
    public void clear() {
        profiles.clear();
    }

}
