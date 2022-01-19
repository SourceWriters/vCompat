package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3;

import java.util.Set;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;

import net.sourcewriters.minecraft.vcompat.data.api.IDataAdapterContext;
import net.sourcewriters.minecraft.vcompat.data.api.IDataType;
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

import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTNumber;
import net.minecraft.server.v1_16_R3.NBTTagByte;
import net.minecraft.server.v1_16_R3.NBTTagByteArray;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagDouble;
import net.minecraft.server.v1_16_R3.NBTTagEnd;
import net.minecraft.server.v1_16_R3.NBTTagFloat;
import net.minecraft.server.v1_16_R3.NBTTagInt;
import net.minecraft.server.v1_16_R3.NBTTagIntArray;
import net.minecraft.server.v1_16_R3.NBTTagList;
import net.minecraft.server.v1_16_R3.NBTTagLong;
import net.minecraft.server.v1_16_R3.NBTTagLongArray;
import net.minecraft.server.v1_16_R3.NBTTagShort;
import net.minecraft.server.v1_16_R3.NBTTagString;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.data.BukkitContext1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R3.data.BukkitType1_16_R3;
import net.sourcewriters.minecraft.vcompat.provider.BukkitConversion;
import net.sourcewriters.minecraft.vcompat.provider.data.WrapType;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContext;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityType;

public class BukkitConversion1_16_R3 extends BukkitConversion<VersionControl1_16_R3> {

    protected BukkitConversion1_16_R3(VersionControl1_16_R3 versionControl) {
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
    public NBTBase toMinecraftTag(NbtTag tag) {
        switch (tag.getType()) {
        case BYTE:
            return NBTTagByte.a((byte) tag.getValue());
        case SHORT:
            return NBTTagShort.a((short) tag.getValue());
        case INT:
            return NBTTagInt.a((int) tag.getValue());
        case LONG:
            return NBTTagLong.a((long) tag.getValue());
        case FLOAT:
            return NBTTagFloat.a((float) tag.getValue());
        case DOUBLE:
            return NBTTagDouble.a((double) tag.getValue());
        case STRING:
            return NBTTagString.a((String) tag.getValue());
        case BYTE_ARRAY:
            return new NBTTagByteArray((byte[]) tag.getValue());
        case INT_ARRAY:
            return new NBTTagIntArray((int[]) tag.getValue());
        case LONG_ARRAY:
            return new NBTTagLongArray((long[]) tag.getValue());
        case LIST:
            return toMinecraftList((NbtList<?>) tag);
        case COMPOUND:
            return toMinecraftCompound((NbtCompound) tag);
        case END:
            return NBTTagEnd.b;
        default:
            return null;
        }
    }

    @Override
    public NbtTag fromMinecraftTag(Object raw) {
        if (!(raw instanceof NBTBase)) {
            return null;
        }
        NBTBase tag = (NBTBase) raw;
        NbtType type = NbtType.getById(tag.getTypeId());
        switch (type) {
        case BYTE:
            return new NbtByte(((NBTNumber) tag).asByte());
        case SHORT:
            return new NbtShort(((NBTNumber) tag).asShort());
        case INT:
            return new NbtInt(((NBTNumber) tag).asInt());
        case LONG:
            return new NbtLong(((NBTNumber) tag).asLong());
        case FLOAT:
            return new NbtFloat(((NBTNumber) tag).asFloat());
        case DOUBLE:
            return new NbtDouble(((NBTNumber) tag).asDouble());
        case STRING:
            return new NbtString(((NBTTagString) tag).asString());
        case BYTE_ARRAY:
            return new NbtByteArray(((NBTTagByteArray) tag).getBytes());
        case INT_ARRAY:
            return new NbtIntArray(((NBTTagIntArray) tag).getInts());
        case LONG_ARRAY:
            return new NbtLongArray(((NBTTagLongArray) tag).getLongs());
        case LIST:
            return fromMinecraftList(tag);
        case COMPOUND:
            return fromMinecraftCompound(tag);
        case END:
            return NbtEnd.INSTANCE;
        default:
            return null;
        }
    }

    @Override
    public NBTTagList toMinecraftList(NbtList<?> list) {
        NBTTagList output = new NBTTagList();
        for (NbtTag tag : list) {
            output.add(toMinecraftTag(tag));
        }
        return output;
    }

    @Override
    public NbtList<NbtTag> fromMinecraftList(Object raw) {
        if (!(raw instanceof NBTTagList)) {
            return null;
        }
        NBTTagList list = (NBTTagList) raw;
        NbtList<NbtTag> output = new NbtList<>(NbtType.getById(list.getTypeId()));
        for (NBTBase base : list) {
            output.add(fromMinecraftTag(base));
        }
        return output;
    }

    @Override
    public NBTTagCompound toMinecraftCompound(NbtCompound compound) {
        NBTTagCompound output = new NBTTagCompound();
        if (compound.isEmpty()) {
            return output;
        }
        Set<String> keys = compound.getKeys();
        for (String key : keys) {
            output.set(key, toMinecraftTag(compound.get(key)));
        }
        return output;
    }

    @Override
    public NbtCompound fromMinecraftCompound(Object raw) {
        if (!(raw instanceof NBTTagCompound)) {
            return null;
        }
        NBTTagCompound compound = (NBTTagCompound) raw;
        NbtCompound output = new NbtCompound();
        if (compound.isEmpty()) {
            return output;
        }
        Set<String> keys = compound.getKeys();
        for (String key : keys) {
            output.set(key, fromMinecraftTag(compound.get(key)));
        }
        return output;
    }

    @Override
    public org.bukkit.inventory.ItemStack itemFromCompound(NbtCompound compound) {
        return CraftItemStack.asBukkitCopy(ItemStack.a(toMinecraftCompound(compound)));
    }

    @Override
    public NbtCompound itemToCompound(org.bukkit.inventory.ItemStack itemStack) {
        return fromMinecraftCompound(CraftItemStack.asNMSCopy(itemStack).save(new NBTTagCompound()));
    }

    @Override
    public WrappedContext<IDataAdapterContext> createContext(IDataAdapterContext context) {
        return new BukkitContext1_16_R3(context);
    }

    @Override
    public <P, C> WrapType<P, C> wrap(IDataType<P, C> dataType) {
        return new BukkitType1_16_R3<>(dataType);
    }

}