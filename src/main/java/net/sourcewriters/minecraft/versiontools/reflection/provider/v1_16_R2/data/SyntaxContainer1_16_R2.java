package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2.data;

import static net.sourcewriters.minecraft.versiontools.utils.constants.DefaultConstants.NAMESPACE_STRING;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.data.key.DataKey;
import com.syntaxphoenix.syntaxapi.data.key.NamespacedKey;

import net.sourcewriters.minecraft.versiontools.reflection.data.SyntaxKey;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedKey;
import net.sourcewriters.minecraft.versiontools.utils.data.type.ObjectType;

public final class SyntaxContainer1_16_R2 extends WrappedContainer<PersistentDataContainer> implements IDataContainer {

	public static final PersistentDataType<Object, Object> OBJECT_TYPE = new SimpleBukkitType1_16_R2<>(ObjectType.INSTANCE);

	private final PersistentDataContainer container;

	public SyntaxContainer1_16_R2(PersistentDataContainer container) {
		this.container = container;
	}

	@Override
	public PersistentDataContainer getHandle() {
		return container;
	}

	@Override
	public IDataContainer getAsSyntaxContainer() {
		return new SyntaxContainer1_16_R2(container);
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
		return has(new SyntaxKey(key), WrappedType1_16_R2.wrap(type));
	}

	@Override
	public <C> C get(String key, DataType<?, C> type) {
		return get(NamespacedKey.fromStringOrDefault(key, NAMESPACE_STRING), type);
	}

	@Override
	public <C> C get(DataKey key, DataType<?, C> type) {
		return get(new SyntaxKey(key), WrappedType1_16_R2.wrap(type));
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
		set(wrappedKey(key), value, WrappedType1_16_R2.wrap(type));
	}

	@Override
	public <E, V> void set(DataKey key, E value, DataType<V, E> type) {
		set(new SyntaxKey(key), value, WrappedType1_16_R2.wrap(type));
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
		return container.getKeys().stream().map(org.bukkit.NamespacedKey::toString).collect(Collectors.toSet());
	}

	@Override
	public DataKey[] getDataKeys() {
		return container
			.getKeys()
			.stream()
			.map(BukkitKey1_16_R2::new)
			.map(WrappedKey::getNamespacedKey)
			.toArray(DataKey[]::new);
	}

	@Override
	public DataAdapterContext getAdapterContext() {
		return getContext();
	}

	/*
	 * 
	 */

	@Override
	public SyntaxContext1_16_R2 getContext() {
		return new SyntaxContext1_16_R2(container.getAdapterContext());
	}

	@Override
	public boolean has(String key) {
		return has(wrappedKey(key));
	}

	@Override
	public boolean has(WrappedKey<?> key) {
		return container.has(BukkitKey1_16_R2.asBukkit(key), OBJECT_TYPE);
	}

	@Override
	public <P, C> boolean has(String key, WrapType<P, C> type) {
		return has(wrappedKey(key), type);
	}

	@Override
	public <P, C> boolean has(WrappedKey<?> key, WrapType<P, C> type) {
		return container.has(BukkitKey1_16_R2.asBukkit(key), new SimpleBukkitType1_16_R2<>(type));
	}

	@Override
	public Object get(WrappedKey<?> key) {
		return container.get(BukkitKey1_16_R2.asBukkit(key), OBJECT_TYPE);
	}

	@Override
	public <P, C> C get(String key, WrapType<P, C> type) {
		return get(wrappedKey(key), type);
	}

	@Override
	public <P, C> C get(WrappedKey<?> key, WrapType<P, C> type) {
		return container.get(BukkitKey1_16_R2.asBukkit(key), new SimpleBukkitType1_16_R2<>(type));
	}

	@Override
	public <P, C> void set(String key, C value, WrapType<P, C> type) {
		set(wrappedKey(key), value, type);
	}

	@Override
	public <P, C> void set(WrappedKey<?> key, C value, WrapType<P, C> type) {
		container.set(BukkitKey1_16_R2.asBukkit(key), new SimpleBukkitType1_16_R2<>(type), value);
	}

	@Override
	public boolean remove(WrappedKey<?> key) {
		Object value = get(key);
		container.remove(BukkitKey1_16_R2.asBukkit(key));
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
		return container.getKeys().size();
	}

}
