package net.sourcewriters.minecraft.vcompat.reflection.data.wrap;

import java.util.Objects;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.reflection.BukkitConversion;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrapType;

public class SimpleSyntaxType<P, C> implements DataType<P, C> {

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
    public P toPrimitive(DataAdapterContext context, C complex) {
        return type.wrapToPrimitive(complex, conversion.createContext(context));
    }

    @Override
    public C fromPrimitive(DataAdapterContext context, P primitive) {
        return type.wrapToComplex(primitive, conversion.createContext(context));
    }

}