package net.sourcewriters.minecraft.vcompat.skin;

import java.util.Optional;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.utils.NbtStorage;

import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;

public abstract class SkinStore implements NbtStorage<NbtList<NbtCompound>> {

    public abstract boolean add(Skin skin);

    public boolean remove(Skin skin) {
        return skin == null ? false : remove(skin.getName());
    }

    public abstract boolean remove(String name);
    
    public abstract boolean contains(Skin skin);

    public abstract boolean contains(String name);
    
    public Skin get(String name) {
        return optional(name).orElse(null);
    }

    public abstract Optional<Skin> optional(String name);

    public abstract Skin[] getSkins();

    public abstract void clear();

    @Override
    public NbtList<NbtCompound> asNbt() {
        Skin[] array = getSkins();
        NbtList<NbtCompound> list = new NbtList<>(NbtType.COMPOUND);
        for (Skin skin : array) {
            list.add(skin.asNbt());
        }
        return list;
    }

    @Override
    public void fromNbt(NbtList<NbtCompound> list) {
        clear();
        for (NbtCompound compound : list) {
            Skin skin = Skin.load(compound);
            if (!skin.isValid()) {
                continue;
            }
            add(skin);
        }
    }
}
