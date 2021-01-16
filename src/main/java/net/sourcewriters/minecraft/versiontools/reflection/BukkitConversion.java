package net.sourcewriters.minecraft.versiontools.reflection;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntityType;

public abstract class BukkitConversion<V extends VersionControl> extends VersionHandler<V> {

	protected BukkitConversion(V versionControl) {
		super(versionControl);
	}

	public abstract EntityType toEntityType(NmsEntityType type);

	public abstract NmsEntityType fromEntityType(EntityType type);
	
	public abstract Object toMinecraftTag(NbtTag tag);
	
	public abstract NbtTag fromMinecraftTag(Object tag);
	
	public abstract Object toMinecraftList(NbtList<?> list);
	
	public abstract NbtList<?> fromMinecraftList(Object list);
	
	public abstract Object toMinecraftCompound(NbtCompound compound);
	
	public abstract NbtCompound fromMinecraftCompound(Object compound);
	
	public abstract ItemStack itemFromCompound(NbtCompound compound);
	
	public abstract NbtCompound itemToCompound(ItemStack itemStack);
	
	public abstract WrappedContext<DataAdapterContext> createContext(DataAdapterContext context);

}