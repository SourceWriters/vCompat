package net.sourcewriters.minecraft.versiontools.reflection.data;

import com.syntaxphoenix.syntaxapi.data.key.Namespace;
import com.syntaxphoenix.syntaxapi.data.key.NamespacedKey;

public abstract class WrappedKey<H> {

	public abstract H getHandle();

	public abstract String getName();

	public abstract String getKey();

	public abstract String toString();

	public Namespace getNamespace() {
		return new Namespace(getName());
	}

	public NamespacedKey getNamespacedKey() {
		return getNamespace().createKey(getKey());
	}

}
