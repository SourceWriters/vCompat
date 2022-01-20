package net.sourcewriters.minecraft.vcompat.data;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterRegistry;
import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.data.api.IDataFactory;

public abstract class AbstractDataFactory<B> implements IDataFactory<B> {

    protected final IDataAdapterRegistry<B> registry;

    public AbstractDataFactory(IDataAdapterRegistry<B> registry) {
        this.registry = registry;
    }

    @Override
    public IDataAdapterRegistry<B> getRegistry() {
        return registry;
    }

    @Override
    public abstract AbstractDataFactory<B> toFile(IDataContainer container, File file);

    @Override
    public abstract AbstractDataFactory<B> toStream(IDataContainer container, OutputStream stream);

    @Override
    public abstract AbstractDataFactory<B> toString(IDataContainer container, StringBuilder builder);

    @Override
    public abstract AbstractDataFactory<B> fromFile(IDataContainer container, File file);

    @Override
    public abstract AbstractDataFactory<B> fromStream(IDataContainer container, InputStream stream);

    @Override
    public abstract AbstractDataFactory<B> fromString(IDataContainer container, String string);

}
