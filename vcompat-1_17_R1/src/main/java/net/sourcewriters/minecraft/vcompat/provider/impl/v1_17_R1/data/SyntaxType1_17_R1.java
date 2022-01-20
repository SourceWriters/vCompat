package net.sourcewriters.minecraft.vcompat.provider.impl.v1_17_R1.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;

public class SyntaxType1_17_R1<P0, P1, C0, C1> extends WrappedType1_17_R1<PersistentDataType<P1, C1>, P0, P1, C0, C1>
    implements IDataType<P0, C0> {

    private final PersistentDataType<P1, C1> type;

    public SyntaxType1_17_R1(PersistentDataType<P1, C1> type) {
        super(type.getPrimitiveType(), type.getComplexType());
        this.type = type;
    }

    @Override
    public PersistentDataType<P1, C1> getHandle() {
        return type;
    }

    @Override
    public Class<P1> getPrimitiveOriginal() {
        return type.getPrimitiveType();
    }

    @Override
    public Class<C1> getComplexOriginal() {
        return type.getComplexType();
    }

    /*
    * 
    */

    @Override
    public Class<C0> getComplex() {
        return complexType;
    }

    @Override
    public Class<P0> getPrimitive() {
        return primitiveType;
    }

    @Override
    public P0 toPrimitive(IDataAdapterContext context, C0 complex) {
        return wrapToPrimitive(complex, new BukkitContext1_17_R1(context));
    }

    @Override
    public C0 fromPrimitive(IDataAdapterContext context, P0 primitive) {
        return wrapToComplex(primitive, new BukkitContext1_17_R1(context));
    }

    @Override
    public P0 wrapToPrimitive(C0 complex, WrappedContext<?> context) {
        if (!(context instanceof PersistentDataAdapterContext)) {
            return null;
        }
        return toPrimitiveWrapped(type.toPrimitive(toComplexOriginal(complex), (PersistentDataAdapterContext) context));
    }

    @Override
    public C0 wrapToComplex(P0 primitive, WrappedContext<?> context) {
        if (!(context instanceof PersistentDataAdapterContext)) {
            return null;
        }
        return toComplexWrapped(type.fromPrimitive(toPrimitiveOriginal(primitive), (PersistentDataAdapterContext) context));
    }

}