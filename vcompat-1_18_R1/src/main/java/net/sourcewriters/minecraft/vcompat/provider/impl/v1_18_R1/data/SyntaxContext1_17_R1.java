package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R1.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;

public final class SyntaxContext1_17_R1 extends WrappedContext<PersistentDataAdapterContext> implements PersistentDataAdapterContext {

    private final PersistentDataAdapterContext context;

    public SyntaxContext1_17_R1(PersistentDataAdapterContext context) {
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
    public IDataContainer newContainer() {
        return newWrapContainer();
    }

    @Override
    public SyntaxContainer1_17_R1 newWrapContainer() {
        return new SyntaxContainer1_17_R1(context.newPersistentDataContainer());
    }

}