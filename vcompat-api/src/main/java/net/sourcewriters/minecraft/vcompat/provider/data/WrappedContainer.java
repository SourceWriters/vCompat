package net.sourcewriters.minecraft.vcompat.provider.data;

import static net.sourcewriters.minecraft.vcompat.util.constants.DefaultConstants.NAMESPACE_STRING;

import java.util.Optional;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;
import com.syntaxphoenix.syntaxapi.utils.key.Namespace;
import com.syntaxphoenix.syntaxapi.utils.key.NamespacedKey;

import net.sourcewriters.minecraft.vcompat.data.api.IDataContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.wrap.SyntaxKey;

public abstract class WrappedContainer {

    public abstract Object getHandle();

    public abstract WrappedContext<?> getWrapContext();

    public abstract IDataContainer getAsSyntaxContainer();

    public abstract boolean has(String key);

    public abstract boolean has(WrappedKey<?> key);

    public abstract <P, C> boolean has(String key, WrapType<P, C> type);

    public abstract <P, C> boolean has(WrappedKey<?> key, WrapType<P, C> type);

    public abstract Object get(String key);

    public abstract Object get(WrappedKey<?> key);

    public abstract <P, C> C get(String key, WrapType<P, C> type);

    public abstract <P, C> C get(WrappedKey<?> key, WrapType<P, C> type);

    public abstract <B> void set(String key, B value, WrapType<?, B> type);

    public abstract <B> void set(WrappedKey<?> key, B value, WrapType<?, B> type);

    public abstract boolean remove(String key);

    public abstract boolean remove(WrappedKey<?> key);

    public abstract Set<String> keySet();

    public abstract boolean isEmpty();

    public abstract int size();
    
    protected IKey syntaxKey(String key) {
        return NamespacedKey.fromStringOrCompute(key, key0 -> Namespace.of(NAMESPACE_STRING).create(key0));
    }

    protected SyntaxKey wrappedKey(String key) {
        return new SyntaxKey(syntaxKey(key));
    }

    public <P, C> C getOrDefault(String key, WrapType<P, C> type, C value) {
        return Optional.ofNullable(get(key, type)).orElse(value);
    }

    public <P, C> C getOrDefault(WrappedKey<?> key, WrapType<P, C> type, C value) {
        return Optional.ofNullable(get(key, type)).orElse(value);
    }

}