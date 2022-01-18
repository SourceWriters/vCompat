package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1;

import java.util.Set;

import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.nbt.NbtByte;
import com.syntaxphoenix.syntaxapi.nbt.NbtByteArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtDouble;
import com.syntaxphoenix.syntaxapi.nbt.NbtEnd;
import com.syntaxphoenix.syntaxapi.nbt.NbtFloat;
import com.syntaxphoenix.syntaxapi.nbt.NbtInt;
import com.syntaxphoenix.syntaxapi.nbt.NbtIntArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtLong;
import com.syntaxphoenix.syntaxapi.nbt.NbtLongArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtShort;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTNumber;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.item.ItemStack;
import net.sourcewriters.minecraft.vcompat.reflection.BukkitConversion;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrapType;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContext;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntityType;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.data.BukkitContext1_18_R1;
import net.sourcewriters.minecraft.vcompat.reflection.provider.v1_18_R1.data.BukkitType1_18_R1;

public class BukkitConversion1_18_R1 extends BukkitConversion<VersionControl1_18_R1> {

    protected BukkitConversion1_18_R1(VersionControl1_18_R1 versionControl) {
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
        NbtType type = NbtType.getById(tag.a());
        switch (type) {
        case BYTE:
            return new NbtByte(((NBTNumber) tag).h());
        case SHORT:
            return new NbtShort(((NBTNumber) tag).g());
        case INT:
            return new NbtInt(((NBTNumber) tag).f());
        case LONG:
            return new NbtLong(((NBTNumber) tag).e());
        case FLOAT:
            return new NbtFloat(((NBTNumber) tag).j());
        case DOUBLE:
            return new NbtDouble(((NBTNumber) tag).i());
        case STRING:
            return new NbtString(((NBTTagString) tag).e_());
        case BYTE_ARRAY:
            return new NbtByteArray(((NBTTagByteArray) tag).d());
        case INT_ARRAY:
            return new NbtIntArray(((NBTTagIntArray) tag).f());
        case LONG_ARRAY:
            return new NbtLongArray(((NBTTagLongArray) tag).f());
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
        NbtList<NbtTag> output = new NbtList<>(NbtType.getById(list.e()));
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
            output.a(key, toMinecraftTag(compound.get(key)));
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
        if (compound.f()) {
            return output;
        }
        Set<String> keys = compound.d();
        for (String key : keys) {
            output.set(key, fromMinecraftTag(compound.c(key)));
        }
        return output;
    }

    @Override
    public org.bukkit.inventory.ItemStack itemFromCompound(NbtCompound compound) {
        return CraftItemStack.asBukkitCopy(ItemStack.a(toMinecraftCompound(compound)));
    }

    @Override
    public NbtCompound itemToCompound(org.bukkit.inventory.ItemStack itemStack) {
        return fromMinecraftCompound(CraftItemStack.asNMSCopy(itemStack).b(new NBTTagCompound()));
    }

    @Override
    public WrappedContext<DataAdapterContext> createContext(DataAdapterContext context) {
        return new BukkitContext1_18_R1(context);
    }

    @Override
    public <P, C> WrapType<P, C> wrap(DataType<P, C> dataType) {
        return new BukkitType1_18_R1<>(dataType);
    }

}