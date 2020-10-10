package net.sourcewriters.minecraft.versiontools.reflection.data.wrap;

import java.util.Set;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedKey;

public final class SimpleSyntaxContainer<E extends IDataContainer> extends WrappedContainer {

	private final E container;

	public SimpleSyntaxContainer(E container) {
		this.container = container;
	}

	@Override
	public E getHandle() {
		return container;
	}

	@Override
	public E getAsSyntaxContainer() {
		return container;
	}

	/*
	 * 
	 */

	@Override
	public SimpleSyntaxContext getContext() {
		return new SimpleSyntaxContext(container.getAdapterContext());
	}

	@Override
	public boolean has(String key) {
		return has(wrappedKey(key));
	}

	@Override
	public boolean has(WrappedKey<?> key) {
		return container.has(key.getNamespacedKey());
	}

	@Override
	public <P, C> boolean has(String key, WrapType<P, C> type) {
		return has(wrappedKey(key), type);
	}

	@Override
	public <P, C> boolean has(WrappedKey<?> key, WrapType<P, C> type) {
		return container.has(key.getNamespacedKey(), type.syntaxType());
	}

	@Override
	public Object get(String key) {
		return get(wrappedKey(key));
	}

	@Override
	public Object get(WrappedKey<?> key) {
		return container.get(key.getNamespacedKey());
	}

	@Override
	public <P, C> C get(String key, WrapType<P, C> type) {
		return get(wrappedKey(key), type);
	}

	@Override
	public <P, C> C get(WrappedKey<?> key, WrapType<P, C> type) {
		return container.get(key.getNamespacedKey(), type.syntaxType());
	}

	@Override
	public <B> void set(String key, B value, WrapType<?, B> type) {
		set(wrappedKey(key), value, type);
	}

	@Override
	public <B> void set(WrappedKey<?> key, B value, WrapType<?, B> type) {
		container.set(key.getNamespacedKey(), value, type.syntaxType());
	}

	@Override
	public boolean remove(String key) {
		return false;
	}

	@Override
	public boolean remove(WrappedKey<?> key) {
		return container.remove(key.getNamespacedKey());
	}

	@Override
	public Set<String> keySet() {
		return container.getKeys();
	}

	@Override
	public boolean isEmpty() {
		return container.isEmpty();
	}

	@Override
	public int size() {
		return container.size();
	}

}
