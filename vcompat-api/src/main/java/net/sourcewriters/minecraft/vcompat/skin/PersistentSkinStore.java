package net.sourcewriters.minecraft.vcompat.skin;

import java.util.ArrayList;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;

import net.sourcewriters.minecraft.vcompat.provider.data.persistence.PersistentContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.type.SkinDataType;
import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;

public class PersistentSkinStore<E> extends SkinStore {

    private final PersistentContainer<E> container;

    public PersistentSkinStore(PersistentContainer<E> container) {
        this.container = container;
    }

    @Override
    public boolean add(Skin skin) {
        if (!skin.isValid() || contains(skin)) {
            return false;
        }
        container.set(skin.getName(), skin, SkinDataType.INSTANCE);
        return true;
    }

    @Override
    public boolean remove(String name) {
        return container.remove(name);
    }

    @Override
    public boolean contains(Skin skin) {
        return skin != null && contains(skin.getName());
    }

    @Override
    public boolean contains(String name) {
        return name != null && container.has(name, SkinDataType.INSTANCE);
    }

    @Override
    public Skin get(String name) {
        return name == null ? null : container.get(name, SkinDataType.INSTANCE);
    }

    @Override
    public Optional<Skin> optional(String name) {
        return Optional.ofNullable(get(name));
    }

    @Override
    public Skin[] getSkins() {
        ArrayList<Skin> skins = new ArrayList<>();
        for (IKey key : container.getKeys()) {
            Skin skin = container.get(key, SkinDataType.INSTANCE);
            if (skin == null) {
                continue;
            }
            skins.add(skin);
        }
        return skins.toArray(new Skin[skins.size()]);
    }

    @Override
    public void clear() {
        container.clear();
    }

}
