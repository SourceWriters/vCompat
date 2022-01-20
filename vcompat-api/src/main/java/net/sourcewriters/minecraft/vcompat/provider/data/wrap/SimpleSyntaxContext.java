package net.sourcewriters.minecraft.vcompat.provider.data.wrap;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;

public class SimpleSyntaxContext extends WrappedContext<IDataAdapterContext> {

    private final IDataAdapterContext context;

    public SimpleSyntaxContext(IDataAdapterContext context) {
        this.context = context;
    }

    @Override
    public IDataAdapterContext getHandle() {
        return context;
    }

    @Override
    public IDataContainer newContainer() {
        return context.newContainer();
    }

    @Override
    public WrappedContainer newWrapContainer() {
        return new SimpleSyntaxContainer<>(context.newContainer());
    }

}