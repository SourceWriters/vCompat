package net.sourcewriters.minecraft.versiontools.utils.data.type;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.data.PrimitiveDataType;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;
import net.sourcewriters.minecraft.versiontools.skin.Skin;

@SuppressWarnings("rawtypes")
public final class SkinDataType implements WrapType<Skin, WrappedContainer> {

	public static final SkinDataType INSTANCE = new SkinDataType();

	private SkinDataType() {
	}

	@Override
	public Class<Skin> getPrimitiveWrapped() {
		return Skin.class;
	}

	@Override
	public Class<WrappedContainer> getComplexWrapped() {
		return WrappedContainer.class;
	}

	@Override
	public Skin wrapToPrimitive(WrappedContainer complex, WrappedContext<?> context) {
		IDataContainer container = complex.getAsSyntaxContainer();
		String name = container.get("name", PrimitiveDataType.STRING);
		String value = container.get("value", PrimitiveDataType.STRING);
		String signature = container.get("signature", PrimitiveDataType.STRING);
		return new Skin(name, value, signature, false);
	}

	@Override
	public WrappedContainer wrapToComplex(Skin primitive, WrappedContext<?> context) {
		WrappedContainer wrapped = context.newContainer();
		IDataContainer container = wrapped.getAsSyntaxContainer();
		container.set("name", primitive.getName(), PrimitiveDataType.STRING);
		container.set("value", primitive.getValue(), PrimitiveDataType.STRING);
		container.set("signature", primitive.getSignature(), PrimitiveDataType.STRING);
		return wrapped;
	}

}
