package net.sourcewriters.minecraft.versiontools.reflection.data;

import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtAdapterRegistry;
import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtContainer;

public abstract class PersistentDataAdapter extends NbtContainer {

	public PersistentDataAdapter(NbtAdapterRegistry registry) {
		super(registry);
	}

}
