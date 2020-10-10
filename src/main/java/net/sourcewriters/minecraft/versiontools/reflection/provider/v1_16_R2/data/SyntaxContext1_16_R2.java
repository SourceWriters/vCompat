package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;

public final class SyntaxContext1_16_R2 extends WrappedContext<PersistentDataAdapterContext> implements PersistentDataAdapterContext {

	private final PersistentDataAdapterContext context;

	public SyntaxContext1_16_R2(PersistentDataAdapterContext context) {
		this.context = context;
	}

	@Override
	public PersistentDataAdapterContext getHandle() {
		return context;
	}

	@Override
	public PersistentDataContainer newPersistentDataContainer() {
		return context.newPersistentDataContainer();
	}

	@Override
	public IDataContainer newDataContainer() {
		return newContainer();
	}

	@Override
	public SyntaxContainer1_16_R2 newContainer() {
		return new SyntaxContainer1_16_R2(context.newPersistentDataContainer());
	}

}
