package net.sourcewriters.minecraft.vcompat.provider.lookup.handle;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

import com.syntaxphoenix.syntaxapi.utils.java.Arrays;

import net.sourcewriters.minecraft.vcompat.provider.lookup.JavaAccessor;
import net.sourcewriters.minecraft.vcompat.util.java.tools.ReflectionTools;

public class ClassLookup {

    private Class<?> owner;

    private final HashMap<String, Constructor<?>> constructors = new HashMap<>();
    private final HashMap<String, Method> methods = new HashMap<>();
    private final HashMap<String, Field> fields = new HashMap<>();

    protected ClassLookup(final String classPath) throws IllegalAccessException {
        this(ReflectionTools.getClass(classPath));
    }

    protected ClassLookup(final Class<?> owner) throws IllegalAccessException {
        this.owner = owner;
    }

    /*
     * 
     */

    public Class<?> getOwner() {
        return owner;
    }

    /*
     * 
     */

    public void delete() {
        constructors.clear();
        methods.clear();
        fields.clear();
        owner = null;
    }

    public boolean isValid() {
        return owner != null;
    }

    /*
     * 
     */

    public Collection<Constructor<?>> getConstructors() {
        return constructors.values();
    }

    public Collection<Method> getMethods() {
        return methods.values();
    }

    public Collection<Field> getFields() {
        return fields.values();
    }

    /*
     * 
     */

    public Constructor<?> getConstructor(final String name) {
        return isValid() ? constructors.get(name) : null;
    }

    public Method getMethod(final String name) {
        return isValid() ? methods.get(name) : null;
    }

    public Field getField(final String name) {
        return isValid() ? fields.get(name) : null;
    }

    /*
     * 
     */

    public boolean hasConstructor(final String name) {
        return isValid() && constructors.containsKey(name);
    }

    public boolean hasMethod(final String name) {
        return isValid() && methods.containsKey(name);
    }

    public boolean hasField(final String name) {
        return isValid() && fields.containsKey(name);
    }

    /*
     * 
     */

    public Object init() {
        if (!isValid()) {
            return null;
        }
        final Constructor<?> constructor = constructors.computeIfAbsent("$base#empty", ignore -> JavaAccessor.getConstructor(owner));
        if (constructor == null) {
            constructors.remove("$base#empty");
            return null;
        }
        return JavaAccessor.init(constructor);
    }

    public Object init(final String name, final Object... args) {
        if (!isValid() || !constructors.containsKey(name)) {
            return null;
        }
        return JavaAccessor.init(constructors.get(name), args);
    }

    /*
     * 
     */

    public ClassLookup execute(final String name, final Object... args) {
        run(name, args);
        return this;
    }

    public ClassLookup execute(final Object source, final String name, final Object... args) {
        run(source, name, args);
        return this;
    }

    public Object run(final String name, final Object... args) {
        if (!isValid() || !methods.containsKey(name)) {
            return null;
        }
        return JavaAccessor.invokeStatic(methods.get(name), args);
    }

    public Object run(final Object source, final String name, final Object... args) {
        if (!isValid() || !methods.containsKey(name)) {
            return null;
        }
        return JavaAccessor.invoke(source, methods.get(name), args);
    }

    /*
     * 
     */

    public Object getFieldValue(final String name) {
        if (!isValid() || !fields.containsKey(name)) {
            return null;
        }
        return JavaAccessor.getStaticValue(fields.get(name));
    }

    public Object getFieldValue(final Object source, final String name) {
        if (!isValid() || !fields.containsKey(name)) {
            return null;
        }
        return JavaAccessor.getObjectValue(source, fields.get(name));
    }

    public void setFieldValue(final String name, final Object value) {
        if (!isValid() || !fields.containsKey(name)) {
            return;
        }
        JavaAccessor.setStaticValue(fields.get(name), value);
    }

    public void setFieldValue(final Object source, final String name, final Object value) {
        if (!isValid() || !fields.containsKey(name)) {
            return;
        }
        JavaAccessor.setObjectValue(source, fields.get(name), value);
    }

    /*
     * 
     */

    public ClassLookup searchConstructor(final Predicate<ClassLookup> predicate, final String name, final Class<?>... args) {
        return predicate.test(this) ? searchConstructor(name, args) : this;
    }

    public ClassLookup searchConstructor(final String name, final Class<?>... arguments) {
        if (!isValid() || hasConstructor(name)) {
            return this;
        }
        Constructor<?> constructor = JavaAccessor.getConstructor(owner, arguments);
        if (constructor != null) {
            constructors.put(name, constructor);
        }
        return this;
    }

    public ClassLookup searchConstructorsByArguments(String base, final Class<?>... arguments) {
        if (!isValid()) {
            return this;
        }
        final Constructor<?>[] constructors = Arrays.merge(Constructor<?>[]::new, owner.getDeclaredConstructors(), owner.getConstructors());
        if (constructors.length == 0) {
            return this;
        }
        base += '-';
        int current = 0;
        for (final Constructor<?> constructor : constructors) {
            final Class<?>[] args = constructor.getParameterTypes();
            if (args.length != arguments.length) {
                continue;
            }
            if (ReflectionTools.hasSameArguments(arguments, args)) {
                this.constructors.put(base + current, constructor);
                current++;
            }
        }
        return this;
    }

    /*
     * 
     */

    public ClassLookup searchMethod(final Predicate<ClassLookup> predicate, final String name, final String methodName,
        final Class<?>... arguments) {
        return predicate.test(this) ? searchMethod(name, methodName, arguments) : this;
    }

    public ClassLookup searchMethod(final String name, final String methodName, final Class<?>... arguments) {
        if (!isValid() || hasMethod(name)) {
            return this;
        }
        Method method = JavaAccessor.getMethod(owner, methodName, arguments);
        if (method != null) {
            methods.put(name, method);
        }
        return this;
    }

    public ClassLookup searchMethodsByArguments(String base, final Class<?>... arguments) {
        if (!isValid()) {
            return this;
        }
        final Method[] methods = Arrays.merge(Method[]::new, owner.getDeclaredMethods(), owner.getMethods());
        if (methods.length == 0) {
            return this;
        }
        base += '-';
        int current = 0;
        for (final Method method : methods) {
            final Class<?>[] args = method.getParameterTypes();
            if (args.length != arguments.length) {
                continue;
            }
            if (ReflectionTools.hasSameArguments(arguments, args)) {
                this.methods.put(base + current, method);
                current++;
            }
        }
        return this;
    }

    /*
     * 
     */

    public ClassLookup searchField(final Predicate<ClassLookup> predicate, final String name, final String fieldName) {
        return predicate.test(this) ? searchField(name, fieldName) : this;
    }

    public ClassLookup searchField(final String name, final String fieldName) {
        if (!isValid() || hasField(name)) {
            return this;
        }
        Field field = JavaAccessor.getField(owner, fieldName);
        if (field != null) {
            fields.put(name, field);
        }
        return this;
    }

    /*
     * 
     */

    public boolean putField(final String name, final Field field) {
        if (!isValid() || field == null || name == null || field.getDeclaringClass() != owner || fields.containsKey(name)) {
            return false;
        }
        fields.put(name, field);
        return true;
    }

    /*
     * 
     */

    public static final ClassLookup of(final Class<?> clazz) {
        try {
            return new ClassLookup(clazz);
        } catch (final IllegalAccessException e) {
            return null;
        }
    }

    public static final ClassLookup of(final String path) {
        try {
            return new ClassLookup(path);
        } catch (final IllegalAccessException e) {
            return null;
        }
    }

}