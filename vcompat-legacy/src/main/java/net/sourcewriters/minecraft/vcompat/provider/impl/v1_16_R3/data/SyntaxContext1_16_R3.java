package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContext;

public final class SyntaxContext1_16_R3 extends WrappedContext<PersistentDataAdapterContext> implements PersistentDataAdapterContext {

    private final PersistentDataAdapterContext context;

    public SyntaxContext1_16_R3(PersistentDataAdapterContext context) {
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
    public SyntaxContainer1_16_R3 newContainer() {
        return new SyntaxContainer1_16_R3(context.newPersistentDataContainer());
    }

}