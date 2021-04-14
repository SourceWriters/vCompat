package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_11_R1;

import java.util.List;
import java.util.Set;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
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

import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagByteArray;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagEnd;
import net.minecraft.server.v1_11_R1.NBTTagFloat;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagIntArray;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagLong;
import net.minecraft.server.v1_11_R1.NBTTagShort;
import net.minecraft.server.v1_11_R1.NBTTagString;
import net.sourcewriters.minecraft.vcompat.reflection.BukkitConversion;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrapType;
import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContext;
import net.sourcewriters.minecraft.vcompat.reflection.data.wrap.SimpleSyntaxContext;
import net.sourcewriters.minecraft.vcompat.reflection.data.wrap.SimpleWrapType;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntityType;
import net.sourcewriters.minecraft.vcompat.reflection.reflect.ReflectionProvider;

public class BukkitConversion1_11_R1 extends BukkitConversion<VersionControl1_11_R1> {

    protected BukkitConversion1_11_R1(VersionControl1_11_R1 versionControl) {
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
            return new NBTTagByte((byte) tag.getValue());
        case SHORT:
            return new NBTTagShort((short) tag.getValue());
        case INT:
            return new NBTTagInt((int) tag.getValue());
        case LONG:
            return new NBTTagLong((long) tag.getValue());
        case FLOAT:
            return new NBTTagFloat((float) tag.getValue());
        case DOUBLE:
            return new NBTTagDouble((double) tag.getValue());
        case STRING:
            return new NBTTagString((String) tag.getValue());
        case BYTE_ARRAY:
            return new NBTTagByteArray((byte[]) tag.getValue());
        case INT_ARRAY:
            return new NBTTagIntArray((int[]) tag.getValue());
        case LONG_ARRAY:
            NBTTagList list = new NBTTagList();
            for (long value : (long[]) tag.getValue()) {
                list.add(new NBTTagLong(value));
            }
            return list;
        case LIST:
            return toMinecraftList((NbtList<?>) tag);
        case COMPOUND:
            return toMinecraftCompound((NbtCompound) tag);
        case END:
            return (NBTTagEnd) ReflectionProvider.DEFAULT.getReflect("nmsNBTTagEnd").init();
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
            return new NbtByte(((NBTTagByte) tag).g());
        case SHORT:
            return new NbtShort(((NBTTagShort) tag).f());
        case INT:
            return new NbtInt(((NBTTagInt) tag).e());
        case LONG:
            return new NbtLong(((NBTTagLong) tag).d());
        case FLOAT:
            return new NbtFloat(((NBTTagFloat) tag).i());
        case DOUBLE:
            return new NbtDouble(((NBTTagDouble) tag).asDouble());
        case STRING:
            return new NbtString(((NBTTagString) tag).c_());
        case BYTE_ARRAY:
            return new NbtByteArray(((NBTTagByteArray) tag).c());
        case INT_ARRAY:
            return new NbtIntArray(((NBTTagIntArray) tag).d());
        case LONG_ARRAY:
            return new NbtLongArray(new long[0]);
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

    @SuppressWarnings("unchecked")
    @Override
    public NbtList<NbtTag> fromMinecraftList(Object raw) {
        if (!(raw instanceof NBTTagList)) {
            return null;
        }
        NBTTagList list = (NBTTagList) raw;
        List<NBTBase> content = (List<NBTBase>) ReflectionProvider.DEFAULT.getReflect("nmsNBTTagList").getFieldValue("value", list);
        NbtList<NbtTag> output = new NbtList<>(NbtType.getById(list.getTypeId()));
        for (NBTBase base : content) {
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
        Set<String> keys = compound.c();
        for (String key : keys) {
            output.set(key, fromMinecraftTag(compound.get(key)));
        }
        return output;
    }

    @Override
    public org.bukkit.inventory.ItemStack itemFromCompound(NbtCompound compound) {
        return CraftItemStack.asBukkitCopy(new ItemStack(toMinecraftCompound(compound)));
    }

    @Override
    public NbtCompound itemToCompound(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        if (!nmsStack.hasTag()) {
            nmsStack.convertStack();
        }
        return fromMinecraftCompound(nmsStack.getTag());
    }

    @Override
    public WrappedContext<DataAdapterContext> createContext(DataAdapterContext context) {
        return new SimpleSyntaxContext(context);
    }

    @Override
    public <P, C> WrapType<P, C> wrap(DataType<P, C> dataType) {
        return new SimpleWrapType<P, C>(dataType);
    }

}