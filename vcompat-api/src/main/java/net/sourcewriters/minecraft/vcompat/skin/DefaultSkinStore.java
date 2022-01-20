package net.sourcewriters.minecraft.vcompat.skin;

import java.util.ArrayList;
import java.util.Optional;

import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;

public class DefaultSkinStore extends SkinStore {

    protected final ArrayList<Skin> skins = new ArrayList<>();

    @Override
    public boolean add(Skin skin) {
        return (!skin.isValid() || contains(skin)) ? false : skins.add(skin);
    }

    @Override
    public boolean remove(String name) {
        Optional<Skin> option = optional(name);
        if (!option.isPresent()) {
            return false;
        }
        skins.remove(option.get());
        return true;
    }

    @Override
    public Optional<Skin> optional(String name) {
        return name == null ? Optional.empty() : skins.stream().filter(skin -> skin.getName().equals(name)).findAny();
    }

    @Override
    public boolean contains(Skin skin) {
        return skin != null && (skins.contains(skin) || contains(skin.getName()));
    }

    @Override
    public boolean contains(String name) {
        return name != null && skins.stream().anyMatch(skin -> skin.getName().equals(name));
    }

    @Override
    public Skin[] getSkins() {
        return skins.toArray(new Skin[skins.size()]);
    }

    @Override
    public void clear() {
        skins.clear();
    }

}
