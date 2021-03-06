package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_16_R2.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContext;

public final class BukkitContext1_16_R2 extends WrappedContext<DataAdapterContext> implements PersistentDataAdapterContext {

    private final DataAdapterContext context;

    public BukkitContext1_16_R2(DataAdapterContext context) {
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
    public BukkitContainer1_16_R2 newContainer() {
        return new BukkitContainer1_16_R2(context.newDataContainer());
    }

}