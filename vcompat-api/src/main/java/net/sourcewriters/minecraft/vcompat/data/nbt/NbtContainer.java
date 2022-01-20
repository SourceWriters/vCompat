package net.sourcewriters.minecraft.vcompat.data.nbt;

import java.util.Set;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.utils.NbtStorage;

import net.sourcewriters.minecraft.vcompat.data.AbstractDataContainer;
import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterRegistry;

public class NbtContainer extends AbstractDataContainer<NbtTag> implements IDataAdapterContext, NbtStorage<NbtCompound> {

    protected final NbtCompound root;

    public NbtContainer(IDataAdapterRegistry<NbtTag> registry) {
        this(new NbtCompound(), registry);
    }

    protected NbtContainer(NbtCompound root, IDataAdapterRegistry<NbtTag> registry) {
        super(registry);
        this.root = root;
    }

    @Override
    public NbtContainer newContainer() {
        return new NbtContainer(registry);
    }

    @Override
    public IDataAdapterContext getContext() {
        return this;
    }

    public NbtCompound getRoot() {
        return root;
    }

    @Override
    public void fromNbt(NbtCompound nbt) {
        root.clear();
        for (String key : nbt.getKeys()) {
            set(key, nbt.get(key));
        }
    }

    @Override
    public NbtCompound asNbt() {
        return root.clone();
    }

    @Override
    public boolean has(String key) {
        return root.hasKey(key);
    }

    @Override
    public NbtTag getRaw(String key) {
        return root.get(key);
    }

    @Override
    public boolean remove(String key) {
        return root.remove(key) != null;
    }

    @Override
    public void set(String key, NbtTag value) {
        if(value == null) {
            remove(key);
            return;
        }
        root.set(key, value);
    }

    @Override
    public Set<String> getKeyspaces() {
        return root.getKeys();
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public int size() {
        return root.size();
    }

}
