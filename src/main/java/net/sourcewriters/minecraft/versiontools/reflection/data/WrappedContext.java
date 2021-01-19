package net.sourcewriters.minecraft.versiontools.reflection.data;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;

public abstract class WrappedContext<H> implements DataAdapterContext {

    public abstract H getHandle();

    public abstract WrappedContainer newContainer();

}
