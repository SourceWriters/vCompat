package net.sourcewriters.minecraft.versiontools.reflection.data.type;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.data.PrimitiveDataType;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;
import net.sourcewriters.minecraft.versiontools.skin.Skin;

public final class SkinDataType implements WrapType<WrappedContainer, Skin> {

	public static final SkinDataType INSTANCE = new SkinDataType();

	private SkinDataType() {
	}

	@Override
	public Class<Skin> getComplexWrapped() {
		return Skin.class;
	}

	@Override
	public Class<WrappedContainer> getPrimitiveWrapped() {
		return WrappedContainer.class;
	}

	@Override
	public Skin wrapToComplex(WrappedContainer primitive, WrappedContext<?> context) {
		IDataContainer container = primitive.getAsSyntaxContainer();
		String name = container.get("name", PrimitiveDataType.STRING);
		String value = container.get("value", PrimitiveDataType.STRING);
		String signature = container.get("signature", PrimitiveDataType.STRING);
		return new Skin(name, value, signature, false);
	}

	@Override
	public WrappedContainer wrapToPrimitive(Skin complex, WrappedContext<?> context) {
		WrappedContainer wrapped = context.newContainer();
		IDataContainer container = wrapped.getAsSyntaxContainer();
		container.set("name", complex.getName(), PrimitiveDataType.STRING);
		container.set("value", complex.getValue(), PrimitiveDataType.STRING);
		container.set("signature", complex.getSignature(), PrimitiveDataType.STRING);
		return wrapped;
	}

}
