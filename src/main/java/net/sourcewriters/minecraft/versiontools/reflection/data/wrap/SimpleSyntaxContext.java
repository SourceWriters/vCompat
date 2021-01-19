package net.sourcewriters.minecraft.versiontools.reflection.data.wrap;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;

public class SimpleSyntaxContext extends WrappedContext<DataAdapterContext> {

    private final DataAdapterContext context;

    public SimpleSyntaxContext(DataAdapterContext context) {
        this.context = context;
    }

    @Override
    public DataAdapterContext getHandle() {
        return context;
    }

    @Override
    public IDataContainer newDataContainer() {
        return context.newDataContainer();
    }

    @Override
    public WrappedContainer newContainer() {
        return new SimpleSyntaxContainer<>(context.newDataContainer());
    }

}
