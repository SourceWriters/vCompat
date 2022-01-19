package net.sourcewriters.minecraft.vcompat.provider.impl.v1_15_R1.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;

import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;

public final class BukkitContext1_15_R1 extends WrappedContext<IDataAdapterContext> implements PersistentDataAdapterContext {

    private final IDataAdapterContext context;

    public BukkitContext1_15_R1(IDataAdapterContext context) {
        this.context = context;
    }

    @Override
    public IDataAdapterContext getHandle() {
        return context;
    }

    @Override
    public PersistentDataContainer newPersistentDataContainer() {
        return newContainer();
    }

    @Override
    public IDataContainer newContainer() {
        return context.newDataContainer();
    }

    @Override
    public BukkitContainer1_15_R1 newWrapContainer() {
        return new BukkitContainer1_15_R1(context.newDataContainer());
    }

}