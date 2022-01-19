package net.sourcewriters.minecraft.vcompat.reflection.data.type;

import net.sourcewriters.minecraft.vcompat.reflection.data.WrapType;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContext;

public final class ObjectType implements WrapType<Object, Object> {

    public static final ObjectType INSTANCE = new ObjectType();

    private ObjectType() {}

    @Override
    public Class<Object> getPrimitiveWrapped() {
        return Object.class;
    }

    @Override
    public Class<Object> getComplexWrapped() {
        return Object.class;
    }

    @Override
    public Object wrapToPrimitive(Object complex, WrappedContext<?> context) {
        return complex;
    }

    @Override
    public Object wrapToComplex(Object primitive, WrappedContext<?> context) {
        return primitive;
    }

}