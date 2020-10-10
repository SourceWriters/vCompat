package net.sourcewriters.minecraft.versiontools.reflection.data.wrap;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;

import net.sourcewriters.minecraft.versiontools.reflection.VersionControl;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;

public class SimpleSyntaxType<P, C> implements DataType<P, C> {

	private final WrapType<P, C> type;

	public SimpleSyntaxType(WrapType<P, C> type) {
		this.type = type;
	}

	@Override
	public Class<C> getComplex() {
		return type.getComplexWrapped();
	}

	@Override
	public Class<P> getPrimitive() {
		return type.getPrimitiveWrapped();
	}

	@Override
	public P toPrimitive(DataAdapterContext context, C complex) {
		return type.wrapToPrimitive(complex, VersionControl.get().getBukkitConversion().createContext(context));
	}

	@Override
	public C fromPrimitive(DataAdapterContext context, P primitive) {
		return type.wrapToComplex(primitive, VersionControl.get().getBukkitConversion().createContext(context));
	}

}
