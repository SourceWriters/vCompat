package net.sourcewriters.minecraft.vcompat.reflection.data;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;

public abstract class WrappedContext<H> implements DataAdapterContext {

    public abstract H getHandle();

    public abstract WrappedContainer newContainer();

}