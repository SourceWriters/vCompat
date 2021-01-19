package net.sourcewriters.minecraft.versiontools.reflection.data.wrap;

import com.syntaxphoenix.syntaxapi.data.DataType;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;

public class SimpleWrapType<P, C> implements WrapType<P, C> {

	private final DataType<P, C> type;

	public SimpleWrapType(DataType<P, C> type) {
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