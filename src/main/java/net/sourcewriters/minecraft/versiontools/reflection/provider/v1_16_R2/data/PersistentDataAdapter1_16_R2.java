package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2.data;

import static net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2.VersionControl1_16_R2.INSTANCE;
import static net.sourcewriters.minecraft.versiontools.utils.bukkit.KeyCache.*;

import java.util.Set;

import org.bukkit.craftbukkit.v1_16_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

import net.sourcewriters.minecraft.versiontools.reflection.data.PersistentDataAdapter;
import net.sourcewriters.minecraft.versiontools.skin.Skin;
import net.sourcewriters.minecraft.versiontools.utils.bukkit.persistence.SkinDataType;

public class PersistentDataAdapter1_16_R2 extends PersistentDataAdapter {

	private final CraftPersistentDataContainer handle;

	public PersistentDataAdapter1_16_R2(CraftPersistentDataContainer handle) {
		super(INSTANCE.getDataProvider().getRegistry());
		this.handle = handle;
	}

	@Override
	public Object get(String key) {
		NbtTag tag = INSTANCE.getBukkitConversion().fromMinecraftTag(handle.getRaw().get(key));
		if (tag == null)
			return tag;
		return getAdapterRegistry().extract(tag);
	}

	@Override
	public <E> E get(String key, DataType<?, E> type) {
		return handle.get(key(key), map(type));
	}

	@Override
	public <E, V> void set(String key, E value, DataType<V, E> type) {
		super.set(key, value, type);
	}

	@Override
	public void set(String key, NbtTag tag) {
		super.set(key, tag);
	}

	@Override
	public void set(String key, Object object) {
		super.set(key, object);
	}

	@Override
	public boolean remove(String key) {
		return super.remove(key);
	}

	@Override
	public Set<String> getKeys() {
		return super.getKeys();
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public int size() {
		return super.size();
	}

	public PersistentDataContainer getHandle() {
		return handle;
	}

	@Override
	public NbtCompound getRoot() {
		return INSTANCE.getBukkitConversion().fromMinecraftCompound(handle.toTagCompound());
	}

	@Override
	public NbtCompound asNbt() {
		return getRoot();
	}

	@Override
	public void fromNbt(NbtCompound nbt) {
		handle.getRaw().clear();
		handle.putAll(INSTANCE.getBukkitConversion().toMinecraftCompound(nbt));
	}

	public static <T0, T1> PersistentDataType<T0, T1> map(DataType<T0, T1> type) {

	}

	private static class BukkitDataType<F0, F1> implements PersistentDataType<F0, F1> {

		private final DataType<F0, F1> type;

		private BukkitDataType(DataType<F0, F1> type) {
			this.type = type;
		}

		@Override
		public Class<F0> getPrimitiveType() {
			return type.getPrimitive();
		}

		@Override
		public Class<F1> getComplexType() {
			return type.getComplex();
		}

		@Override
		public F0 toPrimitive(F1 value, PersistentDataAdapterContext context) {
			return type.toPrimitive(context, value);
		}

		@Override
		public F1 fromPrimitive(F0 value, PersistentDataAdapterContext context) {
			return type.fromPrimitive(context, value);
		}

	}

}
