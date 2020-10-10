package net.sourcewriters.minecraft.versiontools.reflection;

import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtAdapterRegistry;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;

import net.sourcewriters.minecraft.versiontools.reflection.data.PersistentDataAdapter;

public abstract class DataProvider<V extends VersionControl> extends VersionHandler<V> {

	protected final NbtAdapterRegistry registry = new NbtAdapterRegistry();

	protected DataProvider(V versionControl) {
		super(versionControl);
	}

	public final NbtAdapterRegistry getRegistry() {
		return registry;
	}

	public abstract PersistentDataAdapter createAdapter();

	public abstract PersistentDataAdapter createAdapter(Object handle);

	public abstract PersistentDataAdapter createAdapter(NbtCompound data);

}
