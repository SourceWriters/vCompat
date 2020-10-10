package net.sourcewriters.minecraft.versiontools.reflection.data;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;

import net.sourcewriters.minecraft.versiontools.utils.wrap.container.WrappedContainer;

public abstract class WrappedContext<H> implements DataAdapterContext {
	
	public abstract H getHandle();
	
	public abstract WrappedContainer<?> newContainer();
	
}

