package net.sourcewriters.minecraft.vcompat.provider.data.wrap;

import java.util.Objects;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.BukkitConversion;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;

public class SimpleSyntaxType<P, C> implements IDataType<P, C> {

    private final WrapType<P, C> type;
    private final BukkitConversion<?> conversion;

    public SimpleSyntaxType(WrapType<P, C> type) {
        this.conversion = Objects.requireNonNull(VersionCompatProvider.get(), "Can't initialize before VersionCompatProvider isn't loaded").getControl().getBukkitConversion();
        this.type = type;
    }

    @Override
    public Class<C> getComplex() {
        return type.getComplexWrapped();
    }

    @Override
    public Class<P> getPrimitive() {
        return type.getPrimitiveWrapped();
    }

    @Override
    public P toPrimitive(IDataAdapterContext context, C complex) {
        return type.wrapToPrimitive(complex, conversion.createContext(context));
    }

    @Override
    public C fromPrimitive(IDataAdapterContext context, P primitive) {
        return type.wrapToComplex(primitive, conversion.createContext(context));
    }

}