package net.sourcewriters.minecraft.versiontools.reflection.data;

import com.syntaxphoenix.syntaxapi.data.DataType;

public interface WrapType<P, C> {

	Class<P> getPrimitiveWrapped();

	Class<C> getComplexWrapped();

	P wrapToPrimitive(C complex, WrappedContext<?> context);

	C wrapToComplex(P primitive, WrappedContext<?> context);

	public default DataType<P, C> syntaxType() {
		return new SimpleSyntaxType<>(this);
	}

}
