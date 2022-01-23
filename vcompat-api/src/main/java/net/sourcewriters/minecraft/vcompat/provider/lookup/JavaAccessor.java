package net.sourcewriters.minecraft.vcompat.provider.lookup;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

import sun.misc.Unsafe;

public final class JavaAccessor {

    private static final JavaAccessor INSTANCE = new JavaAccessor();

    private final AccessUnsuccessful unsuccessful = new AccessUnsuccessful();

    private Unsafe unsafe;
    private Lookup lookup;

    private JavaAccessor() {
        Optional<Class<?>> option = JavaTracker.getCallerClass();
        if (option.isEmpty() || option.get() != JavaAccessor.class) {
            throw new UnsupportedOperationException("Utility class");
        }
    }

    public Unsafe unsafe() {
        if (unsafe != null) {
            return unsafe;
        }
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return unsafe = (Unsafe) field.get(null);
        } catch (Exception exp) {
            return null;
        }
    }

    public Lookup lookup() {
        if (lookup != null) {
            return lookup;
        }
        return lookup = (Lookup) getStaticValueUnsafe(getField(Lookup.class, "IMPL_LOOKUP"));
    }

    /*
     * Object creation
     */

    public Object initialize(Constructor<?> constructor, Object... arguments) {
        if (constructor == null || (constructor.getParameterCount() != arguments.length)) {
            return null;
        }
        try {
            if (arguments.length == 0) {
                return lookup.unreflectConstructor(constructor).invoke();
            }
            return lookup().unreflectConstructor(constructor).invokeWithArguments(arguments);
        } catch (Throwable e) {
            return null;
        }
    }

    /*
     * Method invokation
     */

    public Object execute(Object instance, Method method, Object... arguments) {
        if (method == null || (method.getParameterCount() != arguments.length)) {
            return null;
        }
        try {
            if (!Modifier.isStatic(method.getModifiers())) {
                if (instance == null) {
                    return null;
                }
                if (arguments.length == 0) {
                    return lookup().unreflect(method).invokeWithArguments(instance);
                }
                Object[] input = new Object[arguments.length + 1];
                input[0] = instance;
                System.arraycopy(arguments, 0, input, 1, arguments.length);
                return lookup().unreflect(method).invokeWithArguments(input);
            }
            return lookup().unreflect(method).invokeWithArguments(arguments);
        } catch (Throwable e) {
            return null;
        }
    }

    /*
     * Safe Accessors
     */

    public Object getObjectValueSafe(Object instance, Field field) {
        if (instance == null || field == null) {
            return null;
        }
        try {
            return lookup().unreflectGetter(field).invoke(instance);
        } catch (Throwable e) {
            throw unsuccessful;
        }
    }

    public Object getStaticValueSafe(Field field) {
        if (field == null) {
            return null;
        }
        try {
            return lookup().unreflectGetter(field).invoke();
        } catch (Throwable e) {
            throw unsuccessful;
        }
    }

    public void setObjectValueSafe(Object instance, Field field, Object value) {
        if (instance == null || field == null || (value != null && !field.getType().isAssignableFrom(value.getClass()))) {
            return;
        }
        unfinalize(field);
        try {
            lookup().unreflectSetter(field).invokeWithArguments(instance, value);
        } catch (Throwable e) {
            throw unsuccessful;
        }
    }

    public void setStaticValueSafe(Field field, Object value) {
        if (field == null || (value != null && !field.getType().isAssignableFrom(value.getClass()))) {
            return;
        }
        unfinalize(field);
        try {
            lookup().unreflectSetter(field).invokeWithArguments(value);
        } catch (Throwable e) {
            throw unsuccessful;
        }
    }

    /*
     * Unsafe Accessors
     */

    public Object getObjectValueUnsafe(Object instance, Field field) {
        if (instance == null || field == null) {
            return null;
        }
        Unsafe unsafe = unsafe();
        return unsafe.getObjectVolatile(instance, unsafe.objectFieldOffset(field));
    }

    public Object getStaticValueUnsafe(Field field) {
        if (field == null) {
            return null;
        }
        Unsafe unsafe = unsafe();
        return unsafe.getObjectVolatile(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field));
    }

    public void setObjectValueUnsafe(Object instance, Field field, Object value) {
        if (instance == null || field == null) {
            return;
        }
        unfinalize(field);
        Unsafe unsafe = unsafe();
        if (value == null) {
            unsafe.putObject(instance, unsafe.objectFieldOffset(field), null);
            return;
        }
        if (field.getType().isAssignableFrom(value.getClass())) {
            unsafe.putObject(instance, unsafe.objectFieldOffset(field), field.getType().cast(value));
        }
    }

    public void setStaticValueUnsafe(Field field, Object value) {
        if (field == null) {
            return;
        }
        unfinalize(field);
        Unsafe unsafe = unsafe();
        if (value == null) {
            unsafe.putObject(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), null);
            return;
        }
        if (field.getType().isAssignableFrom(value.getClass())) {
            unsafe.putObject(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), field.getType().cast(value));
        }
    }

    /*
     * Internal Utilities
     */

    private void unfinalize(Field field) {
        if (!Modifier.isFinal(field.getModifiers())) {
            return;
        }
        try {
            lookup().findSetter(Field.class, "modifiers", int.class).invokeExact(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (Throwable e) {
            // Ignore
        }
    }

    /*
     * Static Accessors
     */

    // Initialisation
    
    public static Object init(Constructor<?> constructor, Object... arguments) {
        return INSTANCE.initialize(constructor, arguments);
    }

    // Invokation

    public static Object invokeStatic(Method method, Object... arguments) {
        return INSTANCE.execute(null, method, arguments);
    }

    public static Object invoke(Object instance, Method method, Object... arguments) {
        return INSTANCE.execute(instance, method, arguments);
    }

    // Setter

    public static void setValue(Object instance, Class<?> clazz, String fieldName, Object value) {
        setValue(instance, getField(clazz, fieldName), value);
    }

    public static void setObjectValue(Object instance, Class<?> clazz, String fieldName, Object value) {
        setObjectValue(instance, getField(clazz, fieldName), value);
    }

    public static void setStaticValue(Class<?> clazz, String fieldName, Object value) {
        setStaticValue(getField(clazz, fieldName), value);
    }

    public static void setValue(Object instance, Field field, Object value) {
        if (field == null) {
            return;
        }
        if (Modifier.isStatic(field.getModifiers())) {
            setStaticValue(field, value);
            return;
        }
        setObjectValue(instance, field, value);
    }

    public static void setObjectValue(Object instance, Field field, Object value) {
        if (instance == null || field == null) {
            return;
        }
        try {
            INSTANCE.setObjectValueSafe(instance, field, value);
        } catch (AccessUnsuccessful unsafe) {
            INSTANCE.setObjectValueUnsafe(instance, field, value);
        }
    }

    public static void setStaticValue(Field field, Object value) {
        if (field == null) {
            return;
        }
        try {
            INSTANCE.setStaticValueSafe(field, value);
        } catch (AccessUnsuccessful unsafe) {
            INSTANCE.setStaticValueUnsafe(field, value);
        }
    }

    // Getter

    public static Object getValue(Object instance, Class<?> clazz, String fieldName) {
        return getValue(instance, getField(clazz, fieldName));
    }

    public static Object getObjectValue(Object instance, Class<?> clazz, String fieldName) {
        return getObjectValue(instance, getField(clazz, fieldName));
    }

    public static Object getStaticValue(Class<?> clazz, String fieldName) {
        return getStaticValue(getField(clazz, fieldName));
    }

    public static Object getValue(Object instance, Field field) {
        if (field == null) {
            return null;
        }
        if (Modifier.isStatic(field.getModifiers())) {
            return getStaticValue(field);
        }
        return getObjectValue(instance, field);
    }

    public static Object getObjectValue(Object instance, Field field) {
        if (instance == null || field == null) {
            return null;
        }
        try {
            return INSTANCE.getObjectValueSafe(instance, field);
        } catch (AccessUnsuccessful unsafe) {
            return INSTANCE.getObjectValueUnsafe(instance, field);
        }
    }

    public static Object getStaticValue(Field field) {
        if (field == null) {
            return null;
        }
        try {
            return INSTANCE.getStaticValueSafe(field);
        } catch (AccessUnsuccessful unsafe) {
            return INSTANCE.getStaticValueUnsafe(field);
        }
    }

    /*
     * Static Utilities
     */

    public static Field getField(Class<?> clazz, String field) {
        if (clazz == null || field == null) {
            return null;
        }
        try {
            return clazz.getDeclaredField(field);
        } catch (NoSuchFieldException ignore) {
            try {
                return clazz.getField(field);
            } catch (NoSuchFieldException ignore0) {
                return null;
            }
        }
    }

    public static Method getMethod(Class<?> clazz, String method, Class<?>... arguments) {
        if (clazz == null || method == null) {
            return null;
        }
        try {
            return clazz.getDeclaredMethod(method, arguments);
        } catch (NoSuchMethodException ignore) {
            try {
                return clazz.getMethod(method, arguments);
            } catch (NoSuchMethodException ignore0) {
                return null;
            }
        }
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... arguments) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getDeclaredConstructor(arguments);
        } catch (NoSuchMethodException ignore) {
            try {
                return clazz.getConstructor(arguments);
            } catch (NoSuchMethodException ignore0) {
                return null;
            }
        }
    }

    public static Class<?> getClass(Class<?> clazz, String name) {
        if (clazz == null || name == null) {
            return null;
        }
        int size = clazz.getClasses().length + clazz.getDeclaredClasses().length;
        if (size == 0) {
            return null;
        }
        Class<?>[] classes = new Class<?>[size];
        Class<?>[] tmp = clazz.getClasses();
        System.arraycopy(tmp, 0, classes, 0, tmp.length);
        System.arraycopy(clazz.getDeclaredClasses(), tmp.length, classes, tmp.length, size - tmp.length);
        for (int i = 0; i < size; i++) {
            String target = classes[i].getSimpleName();
            if (target.contains(".")) {
                target = target.split(".", 2)[0];
            }
            if (target.equals(name)) {
                return classes[i];
            }
        }
        return null;
    }

    public static <A extends Annotation> A getAnnotation(AnnotatedElement element, Class<A> annotationType) {
        A annotation = element.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        return element.getDeclaredAnnotation(annotationType);
    }

    /*
     * Internal Exceptions
     */

    private static final class AccessUnsuccessful extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

}
