package net.sourcewriters.minecraft.vcompat.provider.data.wrap;

import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;

public class SimpleWrapType<P, C> implements WrapType<P, C> {

    private final IDataType<P, C> type;

    public SimpleWrapType(IDataType<P, C> type) {
        this.type = type;
    }

    @Override
    public Class<C> getComplexWrapped() {
        return type.getComplex();
    }

    @Override
    public Class<P> getPrimitiveWrapped() {
        return type.getPrimitive();
    }

    @Override
    public P wrapToPrimitive(C complex, WrappedContext<?> context) {
        return type.toPrimitive(context, complex);
    }

    @Override
    public C wrapToComplex(P primitive, WrappedContext<?> context) {
        return type.fromPrimitive(context, primitive);
    }

}