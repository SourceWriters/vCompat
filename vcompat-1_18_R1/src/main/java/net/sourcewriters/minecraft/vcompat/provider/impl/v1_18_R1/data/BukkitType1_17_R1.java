package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R1.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;

public class BukkitType1_17_R1<P0, P1, C0, C1> extends WrappedType1_17_R1<IDataType<P1, C1>, P0, P1, C0, C1>
    implements PersistentDataType<P0, C0> {

    private final IDataType<P1, C1> type;

    public BukkitType1_17_R1(IDataType<P1, C1> type) {
        super(type.getPrimitive(), type.getComplex());
        this.type = type;
    }

    @Override
    public IDataType<P1, C1> getHandle() {
        return type;
    }

    @Override
    public Class<P1> getPrimitiveOriginal() {
        return type.getPrimitive();
    }

    @Override
    public Class<C1> getComplexOriginal() {
        return type.getComplex();
    }

    /*
    * 
    */

    @Override
    public Class<C0> getComplexType() {
        return complexType;
    }

    @Override
    public Class<P0> getPrimitiveType() {
        return primitiveType;
    }

    @Override
    public P0 toPrimitive(C0 complex, PersistentDataAdapterContext context) {
        return wrapToPrimitive(complex, new SyntaxContext1_17_R1(context));
    }

    @Override
    public C0 fromPrimitive(P0 primitive, PersistentDataAdapterContext context) {
        return wrapToComplex(primitive, new SyntaxContext1_17_R1(context));
    }

    @Override
    public P0 wrapToPrimitive(C0 complex, WrappedContext<?> context) {
        return toPrimitiveWrapped(type.toPrimitive(context, toComplexOriginal(complex)));
    }

    @Override
    public C0 wrapToComplex(P0 primitive, WrappedContext<?> context) {
        return toComplexWrapped(type.fromPrimitive(context, toPrimitiveOriginal(primitive)));
    }

}