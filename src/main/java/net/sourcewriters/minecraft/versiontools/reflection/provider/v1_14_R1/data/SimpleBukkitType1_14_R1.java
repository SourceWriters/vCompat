package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;

public class SimpleBukkitType1_14_R1<P, C> implements PersistentDataType<P, C> {

	private final WrapType<P, C> type;

	public SimpleBukkitType1_14_R1(WrapType<P, C> type) {
		this.type = type;
	}

	@Override
	public Class<C> getComplexType() {
		return type.getComplexWrapped();
	}

	@Override
	public Class<P> getPrimitiveType() {
		return type.getPrimitiveWrapped();
	}

	@Override
	public P toPrimitive(C complex, PersistentDataAdapterContext context) {
		return type.wrapToPrimitive(complex, new SyntaxContext1_14_R1(context));
	}

	@Override
	public C fromPrimitive(P primitive, PersistentDataAdapterContext context) {
		return type.wrapToComplex(primitive, new SyntaxContext1_14_R1(context));
	}

}
