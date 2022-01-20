package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R2.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;

public class SimpleBukkitType1_16_R2<P, C> implements PersistentDataType<P, C> {

    private final WrapType<P, C> type;

    public SimpleBukkitType1_16_R2(WrapType<P, C> type) {
        this.type = type;
    }

    @Override
    public Class<C> getComplexType() {
        return type.getComplexWrapped();
    }

    @Override
    public Class<P> getPrimitiveType() {
        return type.getPrimitiveWrapped();
    }

    @Override
    public P toPrimitive(C complex, PersistentDataAdapterContext context) {
        return type.wrapToPrimitive(complex, new SyntaxContext1_16_R2(context));
    }

    @Override
    public C fromPrimitive(P primitive, PersistentDataAdapterContext context) {
        return type.wrapToComplex(primitive, new SyntaxContext1_16_R2(context));
    }

}