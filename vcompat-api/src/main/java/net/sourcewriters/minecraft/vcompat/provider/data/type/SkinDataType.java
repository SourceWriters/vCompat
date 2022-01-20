package net.sourcewriters.minecraft.vcompat.provider.data.type;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;

public final class SkinDataType implements IDataType<IDataContainer, Skin> {

    public static final IDataType<IDataContainer, Skin> INSTANCE = new SkinDataType();
    public static final WrapType<?, Skin> WRAPPED_INSTANCE = VersionCompatProvider.get().getControl().getBukkitConversion().wrap(INSTANCE);

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
    public IDataContainer toPrimitive(IDataAdapterContext context, Skin complex) {
        IDataContainer container = context.newContainer();
        container.set("name", complex.getName(), IDataType.STRING);
        container.set("value", complex.getValue(), IDataType.STRING);
        container.set("signature", complex.getSignature(), IDataType.STRING);
        return container;
    }

    @Override
    public Skin fromPrimitive(IDataAdapterContext context, IDataContainer container) {
        String name = container.get("name", IDataType.STRING);
        String value = container.get("value", IDataType.STRING);
        String signature = container.get("signature", IDataType.STRING);
        if(name == null || value == null || signature == null) {
            return null;
        }
        return new Skin(name, value, signature, false);
    }

}