package net.sourcewriters.minecraft.vcompat.provider;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityType;

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

    public abstract WrappedContext<IDataAdapterContext> createContext(IDataAdapterContext context);
    
    public abstract <P, C> WrapType<P, C> wrap(IDataType<P, C> dataType);

}