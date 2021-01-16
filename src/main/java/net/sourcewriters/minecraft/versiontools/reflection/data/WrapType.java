package net.sourcewriters.minecraft.versiontools.reflection.data;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.data.PrimitiveDataType;

import net.sourcewriters.minecraft.versiontools.reflection.data.wrap.SimpleSyntaxType;
import net.sourcewriters.minecraft.versiontools.reflection.data.wrap.SimpleWrapType;

public interface WrapType<P, C> {

	public static final SimpleWrapType<Byte, Byte> BYTE = new SimpleWrapType<>(PrimitiveDataType.BYTE);
	public static final SimpleWrapType<Short, Short> SHORT = new SimpleWrapType<>(PrimitiveDataType.SHORT);
	public static final SimpleWrapType<Integer, Integer> INTEGER = new SimpleWrapType<>(PrimitiveDataType.INTEGER);
	public static final SimpleWrapType<Long, Long> LONG = new SimpleWrapType<>(PrimitiveDataType.LONG);

	public static final SimpleWrapType<Float, Float> FLOAT = new SimpleWrapType<>(PrimitiveDataType.FLOAT);
	public static final SimpleWrapType<Double, Double> DOUBLE = new SimpleWrapType<>(PrimitiveDataType.DOUBLE);

	public static final SimpleWrapType<BigInteger, BigInteger> BIG_INTEGER = new SimpleWrapType<>(
		PrimitiveDataType.BIG_INTEGER);
	public static final SimpleWrapType<BigDecimal, BigDecimal> BIG_DECIMAL = new SimpleWrapType<>(
		PrimitiveDataType.BIG_DECIMAL);

	public static final SimpleWrapType<Boolean, Boolean> BOOLEAN = new SimpleWrapType<>(PrimitiveDataType.BOOLEAN);

	public static final SimpleWrapType<String, String> STRING = new SimpleWrapType<>(PrimitiveDataType.STRING);

	public static final SimpleWrapType<int[], int[]> INT_ARRAY = new SimpleWrapType<>(PrimitiveDataType.INT_ARRAY);
	public static final SimpleWrapType<byte[], byte[]> BYTE_ARRAY = new SimpleWrapType<>(PrimitiveDataType.BYTE_ARRAY);
	public static final SimpleWrapType<long[], long[]> LONG_ARRAY = new SimpleWrapType<>(PrimitiveDataType.LONG_ARRAY);
	
	/*
	 * Interface
	 */

	Class<P> getPrimitiveWrapped();

	Class<C> getComplexWrapped();

	P wrapToPrimitive(C complex, WrappedContext<?> context);

	C wrapToComplex(P primitive, WrappedContext<?> context);

	public default DataType<P, C> syntaxType() {
		return new SimpleSyntaxType<>(this);
	}

}