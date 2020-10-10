package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_16_R2.persistence.CraftPersistentDataTypeRegistry;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.sourcewriters.minecraft.versiontools.reflection.DataProvider;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2.data.PersistentDataAdapter1_16_R2;

public class DataProvider1_16_R2 extends DataProvider<VersionControl1_16_R2> {

	public static final CraftPersistentDataTypeRegistry CRAFT_REGISTRY = (CraftPersistentDataTypeRegistry) new Reflect(
		CraftEntity.class).searchField("registry", "DATA_TYPE_REGISTRY").getFieldValue("registry");

	protected DataProvider1_16_R2(VersionControl1_16_R2 versionControl) {
		super(versionControl);
	}

	@Override
	public PersistentDataAdapter1_16_R2 createAdapter() {
		return new PersistentDataAdapter1_16_R2(new CraftPersistentDataContainer(CRAFT_REGISTRY));
	}

	@Override
	public PersistentDataAdapter1_16_R2 createAdapter(Object handle) {
		if (!(handle instanceof CraftPersistentDataContainer))
			return null;
		return new PersistentDataAdapter1_16_R2((CraftPersistentDataContainer) handle);
	}

	@Override
	public PersistentDataAdapter1_16_R2 createAdapter(NbtCompound data) {
		PersistentDataAdapter1_16_R2 adapter = createAdapter();
		adapter.fromNbt(data);
		return adapter;
	}

}
