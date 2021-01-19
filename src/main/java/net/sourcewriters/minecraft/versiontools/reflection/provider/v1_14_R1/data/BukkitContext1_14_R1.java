package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;

public final class BukkitContext1_14_R1 extends WrappedContext<DataAdapterContext> implements PersistentDataAdapterContext {

	private final DataAdapterContext context;

	public BukkitContext1_14_R1(DataAdapterContext context) {
		this.context = context;
	}

	@Override
	public DataAdapterContext getHandle() {
		return context;
	}

	@Override
	public PersistentDataContainer newPersistentDataContainer() {
		return newContainer();
	}

	@Override
	public IDataContainer newDataContainer() {
		return context.newDataContainer();
	}

	@Override
	public BukkitContainer1_14_R1 newContainer() {
		return new BukkitContainer1_14_R1(context.newDataContainer());
	}

}
