package net.sourcewriters.minecraft.vcompat.provider.data;

import com.syntaxphoenix.syntaxapi.utils.key.Namespace;
import com.syntaxphoenix.syntaxapi.utils.key.NamespacedKey;

public abstract class WrappedKey<H> {

    public abstract H getHandle();

    public abstract String getName();

    public abstract String getKey();

    public abstract String toString();

    public Namespace getNamespace() {
        return Namespace.of(getName());
    }

    public NamespacedKey getNamespacedKey() {
        return getNamespace().create(getKey());
    }

}