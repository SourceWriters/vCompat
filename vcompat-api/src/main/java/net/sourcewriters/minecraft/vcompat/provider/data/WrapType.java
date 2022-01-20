package net.sourcewriters.minecraft.vcompat.provider.data;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.data.wrap.SimpleSyntaxType;
import net.sourcewriters.minecraft.vcompat.provider.data.wrap.SimpleWrapType;

public interface WrapType<P, C> {

    public static final SimpleWrapType<Byte, Byte> BYTE = new SimpleWrapType<>(IDataType.BYTE);
    public static final SimpleWrapType<Short, Short> SHORT = new SimpleWrapType<>(IDataType.SHORT);
    public static final SimpleWrapType<Integer, Integer> INTEGER = new SimpleWrapType<>(IDataType.INTEGER);
    public static final SimpleWrapType<Long, Long> LONG = new SimpleWrapType<>(IDataType.LONG);

    public static final SimpleWrapType<Float, Float> FLOAT = new SimpleWrapType<>(IDataType.FLOAT);
    public static final SimpleWrapType<Double, Double> DOUBLE = new SimpleWrapType<>(IDataType.DOUBLE);

    public static final SimpleWrapType<BigInteger, BigInteger> BIG_INTEGER = new SimpleWrapType<>(IDataType.BIG_INTEGER);
    public static final SimpleWrapType<BigDecimal, BigDecimal> BIG_DECIMAL = new SimpleWrapType<>(IDataType.BIG_DECIMAL);

    public static final SimpleWrapType<Boolean, Boolean> BOOLEAN = new SimpleWrapType<>(IDataType.BOOLEAN);

    public static final SimpleWrapType<String, String> STRING = new SimpleWrapType<>(IDataType.STRING);

    public static final SimpleWrapType<int[], int[]> INT_ARRAY = new SimpleWrapType<>(IDataType.INT_ARRAY);
    public static final SimpleWrapType<byte[], byte[]> BYTE_ARRAY = new SimpleWrapType<>(IDataType.BYTE_ARRAY);
    public static final SimpleWrapType<long[], long[]> LONG_ARRAY = new SimpleWrapType<>(IDataType.LONG_ARRAY);

    /*
     * Interface
     */

    Class<P> getPrimitiveWrapped();

    Class<C> getComplexWrapped();

    P wrapToPrimitive(C complex, WrappedContext<?> context);

    C wrapToComplex(P primitive, WrappedContext<?> context);

    public default IDataType<P, C> syntaxType() {
        return new SimpleSyntaxType<>(this);
    }

}