package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2.data;

import java.util.Arrays;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;

public abstract class WrappedType1_16_R2<H, P0, P1, C0, C1> implements WrapType<P0, C0> {

	protected final Class<P0> primitiveType;
	protected final Class<C0> complexType;

	private final int primitiveWrap;
	private final int complexWrap;

	@SuppressWarnings("unchecked")
	protected WrappedType1_16_R2(Class<P1> primitive, Class<C1> complex) {
		this.primitiveWrap = WrappedType1_16_R2.internalState(primitive);
		this.complexWrap = WrappedType1_16_R2.internalState(complex);
		this.primitiveType = (Class<P0>) WrappedType1_16_R2.internalWrap(primitive, primitiveWrap);
		this.complexType = (Class<C0>) WrappedType1_16_R2.internalWrap(complex, complexWrap);
	}

	public abstract H getHandle();

	public Class<P0> getPrimitiveWrapped() {
		return primitiveType;
	}

	public Class<C0> getComplexWrapped() {
		return complexType;
	}

	public abstract Class<P1> getPrimitiveOriginal();

	public abstract Class<C1> getComplexOriginal();

	@SuppressWarnings("unchecked")
	public P0 toPrimitiveWrapped(P1 primitive) {
		switch (primitiveWrap) {
		case 1:
			return (P0) new SyntaxContainer1_16_R2((PersistentDataContainer) primitive);
		case 2:
			return (P0) Arrays.stream((PersistentDataContainer[]) primitive).map(SyntaxContainer1_16_R2::new).toArray(IDataContainer[]::new);
		case 3:
			return (P0) new BukkitContainer1_16_R2((IDataContainer) primitive);
		case 4:
			return (P0) Arrays.stream((IDataContainer[]) primitive).map(BukkitContainer1_16_R2::new).toArray(PersistentDataContainer[]::new);
		default:
			return (P0) primitive;
		}
	}

	@SuppressWarnings("unchecked")
	public C0 toComplexWrapped(C1 complex) {
		switch (complexWrap) {
		case 1:
			return (C0) new SyntaxContainer1_16_R2((PersistentDataContainer) complex);
		case 2:
			return (C0) Arrays.stream((PersistentDataContainer[]) complex).map(SyntaxContainer1_16_R2::new).toArray(IDataContainer[]::new);
		case 3:
			return (C0) new BukkitContainer1_16_R2((IDataContainer) complex);
		case 4:
			return (C0) Arrays.stream((IDataContainer[]) complex).map(BukkitContainer1_16_R2::new).toArray(PersistentDataContainer[]::new);
		default:
			return (C0) complex;
		}
	}

	@SuppressWarnings("unchecked")
	public P1 toPrimitiveOriginal(P0 primitive) {
		switch (primitiveWrap) {
		case 1:
			return (P1) new BukkitContainer1_16_R2((IDataContainer) primitive);
		case 2:
			return (P1) Arrays.stream((IDataContainer[]) primitive).map(BukkitContainer1_16_R2::new).toArray(PersistentDataContainer[]::new);
		case 3:
			return (P1) new SyntaxContainer1_16_R2((PersistentDataContainer) primitive);
		case 4:
			return (P1) Arrays.stream((PersistentDataContainer[]) primitive).map(SyntaxContainer1_16_R2::new).toArray(IDataContainer[]::new);
		default:
			return (P1) primitive;
		}
	}

	@SuppressWarnings("unchecked")
	public C1 toComplexOriginal(C0 complex) {
		switch (complexWrap) {
		case 1:
			return (C1) new BukkitContainer1_16_R2((IDataContainer) complex);
		case 2:
			return (C1) Arrays.stream((IDataContainer[]) complex).map(BukkitContainer1_16_R2::new).toArray(PersistentDataContainer[]::new);
		case 3:
			return (C1) new SyntaxContainer1_16_R2((PersistentDataContainer) complex);
		case 4:
			return (C1) Arrays.stream((PersistentDataContainer[]) complex).map(SyntaxContainer1_16_R2::new).toArray(IDataContainer[]::new);
		default:
			return (C1) complex;
		}
	}

	protected static Class<?> internalWrap(Class<?> clazz, int state) {
		switch (state) {
		case 1:
			return IDataContainer.class;
		case 2:
			return IDataContainer[].class;
		case 3:
			return PersistentDataContainer.class;
		case 4:
			return PersistentDataContainer[].class;
		default:
			return clazz;
		}
	}

	protected static int internalState(Class<?> clazz) {
		if (clazz.equals(PersistentDataContainer.class)) {
			return 1;
		}
		if (clazz.equals(PersistentDataContainer[].class)) {
			return 2;
		}
		if (clazz.equals(IDataContainer.class)) {
			return 3;
		}
		if (clazz.equals(IDataContainer[].class)) {
			return 4;
		}
		return 0;
	}

	public static <A, B> BukkitType1_16_R2<?, A, ?, B> wrap(DataType<A, B> type) {
		return new BukkitType1_16_R2<>(type);
	}

	public static <A, B> SyntaxType1_16_R2<?, A, ?, B> wrap(PersistentDataType<A, B> type) {
		return new SyntaxType1_16_R2<>(type);
	}

}
