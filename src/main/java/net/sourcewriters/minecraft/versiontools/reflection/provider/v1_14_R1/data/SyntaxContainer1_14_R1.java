package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.data;

import static net.sourcewriters.minecraft.versiontools.utils.constants.DefaultConstants.NAMESPACE_STRING;

import java.util.Collections;
import java.util.Set;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.data.key.DataKey;
import com.syntaxphoenix.syntaxapi.data.key.NamespacedKey;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedKey;
import net.sourcewriters.minecraft.versiontools.reflection.data.type.ObjectType;
import net.sourcewriters.minecraft.versiontools.reflection.data.wrap.SyntaxKey;

public final class SyntaxContainer1_14_R1 extends WrappedContainer implements IDataContainer {

	public static final PersistentDataType<Object, Object> OBJECT_TYPE = new SimpleBukkitType1_14_R1<>(ObjectType.INSTANCE);

	private final PersistentDataContainer container;

	public SyntaxContainer1_14_R1(PersistentDataContainer container) {
		this.container = container;
	}

	@Override
	public PersistentDataContainer getHandle() {
		return container;
	}

	@Override
	public IDataContainer getAsSyntaxContainer() {
		return new SyntaxContainer1_14_R1(container);
	}

	/*
	 * 
	 */

	@Override
	public boolean has(DataKey key) {
		return has(new SyntaxKey(key));
	}

	@Override
	public boolean has(String key, DataType<?, ?> type) {
		return has(NamespacedKey.fromStringOrDefault(key, NAMESPACE_STRING), type);
	}

	@Override
	public boolean has(DataKey key, DataType<?, ?> type) {
		return has(new SyntaxKey(key), WrappedType1_14_R1.wrap(type));
	}

	@Override
	public <C> C get(String key, DataType<?, C> type) {
		return get(NamespacedKey.fromStringOrDefault(key, NAMESPACE_STRING), type);
	}

	@Override
	public <C> C get(DataKey key, DataType<?, C> type) {
		return get(new SyntaxKey(key), WrappedType1_14_R1.wrap(type));
	}

	@Override
	public Object get(String key) {
		return get(NamespacedKey.fromStringOrDefault(key, NAMESPACE_STRING));
	}

	@Override
	public Object get(DataKey key) {
		return get(new SyntaxKey(key));
	}

	@Override
	public <E, V> void set(String key, E value, DataType<V, E> type) {
		set(wrappedKey(key), value, WrappedType1_14_R1.wrap(type));
	}

	@Override
	public <E, V> void set(DataKey key, E value, DataType<V, E> type) {
		set(new SyntaxKey(key), value, WrappedType1_14_R1.wrap(type));
	}

	@Override
	public boolean remove(String key) {
		return remove(wrappedKey(key));
	}

	@Override
	public boolean remove(DataKey key) {
		return remove(new SyntaxKey(key));
	}

	@Override
	public Set<String> getKeys() {
		return Collections.emptySet();
	}

	@Override
	public DataKey[] getDataKeys() {
		return new DataKey[0];
	}

	@Override
	public DataAdapterContext getAdapterContext() {
		return getContext();
	}

	/*
	 * 
	 */

	@Override
	public SyntaxContext1_14_R1 getContext() {
		return new SyntaxContext1_14_R1(container.getAdapterContext());
	}

	@Override
	public boolean has(String key) {
		return has(wrappedKey(key));
	}

	@Override
	public boolean has(WrappedKey<?> key) {
		return container.has(BukkitKey1_14_R1.asBukkit(key), OBJECT_TYPE);
	}

	@Override
	public <P, C> boolean has(String key, WrapType<P, C> type) {
		return has(wrappedKey(key), type);
	}

	@Override
	public <P, C> boolean has(WrappedKey<?> key, WrapType<P, C> type) {
		return container.has(BukkitKey1_14_R1.asBukkit(key), new SimpleBukkitType1_14_R1<>(type));
	}

	@Override
	public Object get(WrappedKey<?> key) {
		return container.get(BukkitKey1_14_R1.asBukkit(key), OBJECT_TYPE);
	}

	@Override
	public <P, C> C get(String key, WrapType<P, C> type) {
		return get(wrappedKey(key), type);
	}

	@Override
	public <P, C> C get(WrappedKey<?> key, WrapType<P, C> type) {
		return container.get(BukkitKey1_14_R1.asBukkit(key), new SimpleBukkitType1_14_R1<>(type));
	}

	@Override
	public <B> void set(String key, B value, WrapType<?, B> type) {
		set(wrappedKey(key), value, type);
	}

	@Override
	public <B> void set(WrappedKey<?> key, B value, WrapType<?, B> type) {
		container.set(BukkitKey1_14_R1.asBukkit(key), new SimpleBukkitType1_14_R1<>(type), value);
	}

	@Override
	public boolean remove(WrappedKey<?> key) {
		Object value = get(key);
		container.remove(BukkitKey1_14_R1.asBukkit(key));
		return value != null && get(key) == null;
	}

	@Override
	public Set<String> keySet() {
		return getKeys();
	}

	@Override
	public boolean isEmpty() {
		return container.isEmpty();
	}

	@Override
	public int size() {
		return 0;
	}

}
