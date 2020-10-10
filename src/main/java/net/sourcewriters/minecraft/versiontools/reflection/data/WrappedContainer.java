package net.sourcewriters.minecraft.versiontools.reflection.data;

import static net.sourcewriters.minecraft.versiontools.utils.constants.DefaultConstants.NAMESPACE_STRING;

import java.util.Set;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.data.key.NamespacedKey;

import net.sourcewriters.minecraft.versiontools.utils.wrap.context.WrappedContext;
import net.sourcewriters.minecraft.versiontools.utils.wrap.key.SyntaxKey;
import net.sourcewriters.minecraft.versiontools.utils.wrap.key.WrappedKey;
import net.sourcewriters.minecraft.versiontools.utils.wrap.type.WrapType;

public abstract class WrappedContainer<H> {

	public abstract H getHandle();

	public abstract WrappedContext<?> getContext();
	
	public abstract IDataContainer getAsSyntaxContainer();
	
	public abstract boolean has(String key);

	public abstract boolean has(WrappedKey<?> key);
	
	public abstract <P, C> boolean has(String key, WrapType<P, C> type);

	public abstract <P, C> boolean has(WrappedKey<?> key, WrapType<P, C> type);
	
	public abstract Object get(String key);

	public abstract Object get(WrappedKey<?> key);
	
	public abstract <P, C> C get(String key, WrapType<P, C> type);

	public abstract <P, C> C get(WrappedKey<?> key, WrapType<P, C> type);
	
	public abstract <P, C> void set(String key, C value, WrapType<P, C> type);

	public abstract <P, C> void set(WrappedKey<?> key, C value, WrapType<P, C> type);

	public abstract boolean remove(String key);

	public abstract boolean remove(WrappedKey<?> key);
	
	public abstract Set<String> keySet();
	
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	protected SyntaxKey wrappedKey(String key) {
		return SyntaxKey.of(NamespacedKey.fromStringOrDefault(key, NAMESPACE_STRING));
	}

}