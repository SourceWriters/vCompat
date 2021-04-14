package net.sourcewriters.minecraft.vcompat.reflection.data.type;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.data.PrimitiveDataType;

import net.sourcewriters.minecraft.vcompat.reflection.VersionControl;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrapType;
import net.sourcewriters.minecraft.vcompat.utils.minecraft.Skin;

public final class SkinDataType implements DataType<IDataContainer, Skin> {

    public static final WrapType<?, Skin> INSTANCE = VersionControl.get().getBukkitConversion().wrap(new SkinDataType());

    private SkinDataType() {}

    @Override
    public Class<Skin> getComplex() {
        return Skin.class;
    }

    @Override
    public Class<IDataContainer> getPrimitive() {
        return IDataContainer.class;
    }

    @Override
    public IDataContainer toPrimitive(DataAdapterContext context, Skin complex) {
        IDataContainer container = context.newDataContainer();
        container.set("name", complex.getName(), PrimitiveDataType.STRING);
        container.set("value", complex.getValue(), PrimitiveDataType.STRING);
        container.set("signature", complex.getSignature(), PrimitiveDataType.STRING);
        return container;
    }

    @Override
    public Skin fromPrimitive(DataAdapterContext context, IDataContainer container) {
        String name = container.get("name", PrimitiveDataType.STRING);
        String value = container.get("value", PrimitiveDataType.STRING);
        String signature = container.get("signature", PrimitiveDataType.STRING);
        if(name == null || value == null || signature == null) {
            return null;
        }
        return new Skin(name, value, signature, false);
    }

}