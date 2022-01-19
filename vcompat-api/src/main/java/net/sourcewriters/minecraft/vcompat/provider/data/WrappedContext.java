package net.sourcewriters.minecraft.vcompat.provider.data;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;

public abstract class WrappedContext<H> implements IDataAdapterContext {

    public abstract H getHandle();

    public abstract WrappedContainer newWrapContainer();

}