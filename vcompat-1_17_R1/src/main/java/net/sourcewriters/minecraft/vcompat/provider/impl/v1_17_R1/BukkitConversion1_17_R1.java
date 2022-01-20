package net.sourcewriters.minecraft.vcompat.provider.impl.v1_17_R1;

import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;

import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
import net.sourcewriters.minecraft.vcompat.provider.BukkitConversion;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityType;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_17_R1.data.BukkitContext1_17_R1;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_17_R1.data.BukkitType1_17_R1;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtByte;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtByteArray;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtCompound;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtDouble;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtEnd;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtFloat;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtInt;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtIntArray;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtList;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtLong;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtLongArray;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtShort;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtString;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtTag;
import net.sourcewriters.minecraft.vcompat.shaded.syntaxapi.nbt.NbtType;

public class BukkitConversion1_17_R1 extends BukkitConversion<VersionControl1_17_R1> {

    protected BukkitConversion1_17_R1(VersionControl1_17_R1 versionControl) {
        super(versionControl);
    }

    @Override
    public EntityType toEntityType(NmsEntityType type) {
        try {
            return EntityType.valueOf(type.name());
        } catch (IllegalArgumentException ignore) {
            return null;
        }
    }

    @Override
    public NmsEntityType fromEntityType(EntityType type) {
        try {
            return NmsEntityType.valueOf(type.name());
        } catch (IllegalArgumentException ignore) {
            return null;
        }
    }

    @Override
    public Tag toMinecraftTag(NbtTag tag) {
        switch (tag.getType()) {
        case END:
            return EndTag.INSTANCE;
        case BYTE:
            return ByteTag.valueOf(((NbtByte) tag).getValue());
        case BYTE_ARRAY:
            return new ByteArrayTag(((NbtByteArray) tag).getValue());
        case DOUBLE:
            return DoubleTag.valueOf(((NbtDouble) tag).getValue());
        case FLOAT:
            return FloatTag.valueOf(((NbtFloat) tag).getValue());
        case INT:
            return IntTag.valueOf(((NbtInt) tag).getValue());
        case INT_ARRAY:
            return new IntArrayTag(((NbtIntArray) tag).getValue());
        case LONG:
            return LongTag.valueOf(((NbtLong) tag).getValue());
        case LONG_ARRAY:
            return new LongArrayTag(((NbtLongArray) tag).getValue());
        case SHORT:
            return ShortTag.valueOf(((NbtShort) tag).getValue());
        case STRING:
            return StringTag.valueOf(((NbtString) tag).getValue());
        case LIST:
            return toMinecraftList((NbtList<?>) tag);
        case COMPOUND:
            return toMinecraftCompound((NbtCompound) tag);
        default:
            return null;
        }
    }

    @Override
    public NbtTag fromMinecraftTag(Object tag) {
        if (tag != null && tag instanceof Tag) {
            return fromMinecraftTag0((Tag) tag);
        }
        return null;
    }

    public NbtTag fromMinecraftTag0(Tag tag) {
        switch (NbtType.getById(tag.getId())) {
        case END:
            return NbtEnd.INSTANCE;
        case BYTE:
            return new NbtByte(((ByteTag) tag).getAsByte());
        case BYTE_ARRAY:
            return new NbtByteArray(((ByteArrayTag) tag).getAsByteArray());
        case DOUBLE:
            return new NbtDouble(((DoubleTag) tag).getAsDouble());
        case FLOAT:
            return new NbtFloat(((FloatTag) tag).getAsFloat());
        case INT:
            return new NbtInt(((IntTag) tag).getAsInt());
        case INT_ARRAY:
            return new NbtIntArray(((IntArrayTag) tag).getAsIntArray());
        case LONG:
            return new NbtLong(((LongTag) tag).getAsLong());
        case LONG_ARRAY:
            return new NbtLongArray(((LongArrayTag) tag).getAsLongArray());
        case SHORT:
            return new NbtShort(((ShortTag) tag).getAsShort());
        case STRING:
            return new NbtString(((StringTag) tag).getAsString());
        case LIST:
            return fromMinecraftList(tag);
        case COMPOUND:
            return fromMinecraftCompound(tag);
        default:
            return null;
        }
    }

    @Override
    public ListTag toMinecraftList(NbtList<?> list) {
        ListTag output = new ListTag();
        for (NbtTag tag : list) {
            output.add(toMinecraftTag(tag));
        }
        return output;
    }

    @Override
    public NbtList<NbtTag> fromMinecraftList(Object raw) {
        if (!(raw instanceof ListTag)) {
            return null;
        }
        ListTag list = (ListTag) raw;
        NbtList<NbtTag> output = new NbtList<>(NbtType.getById(list.getElementType()));
        for (Tag base : list) {
            output.add(fromMinecraftTag(base));
        }
        return output;
    }

    @Override
    public CompoundTag toMinecraftCompound(NbtCompound compound) {
        NbtCompound compoundTag = compound;
        CompoundTag targetCompound = new CompoundTag();
        for (String key : compoundTag.getKeys()) {
            targetCompound.put(key, toMinecraftTag(compoundTag.get(key)));
        }
        return targetCompound;
    }

    @Override
    public NbtCompound fromMinecraftCompound(Object raw) {
        if (!(raw instanceof CompoundTag)) {
            return null;
        }
        CompoundTag compoundTag = (CompoundTag) raw;
        NbtCompound targetCompound = new NbtCompound();
        for (String key : compoundTag.getAllKeys()) {
            targetCompound.set(key, fromMinecraftTag(compoundTag.get(key)));
        }
        return targetCompound;
    }

    @Override
    public org.bukkit.inventory.ItemStack itemFromCompound(NbtCompound compound) {
        return CraftItemStack.asBukkitCopy(ItemStack.of(toMinecraftCompound(compound)));
    }

    @Override
    public NbtCompound itemToCompound(org.bukkit.inventory.ItemStack itemStack) {
        return fromMinecraftCompound(CraftItemStack.asNMSCopy(itemStack).save(new CompoundTag()));
    }

    @Override
    public WrappedContext<IDataAdapterContext> createContext(IDataAdapterContext context) {
        return new BukkitContext1_17_R1(context);
    }

    @Override
    public <P, C> WrapType<P, C> wrap(IDataType<P, C> dataType) {
        return new BukkitType1_17_R1<>(dataType);
    }

}