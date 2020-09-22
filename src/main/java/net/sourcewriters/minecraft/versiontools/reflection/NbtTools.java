package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtDeserializer;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtSerializer;

public abstract class NbtTools {

	public static Object toMinecraftCompound(NbtCompound compound) {

		try {

			PipedInputStream input = new PipedInputStream();
			PipedOutputStream output = new PipedOutputStream(input);

			NbtSerializer.UNCOMPRESSED.toStream(new NbtNamedTag("root", compound), output);
			Object nbtCompound = DEFAULT.getOptionalReflect("nmsNBTTools").get().run("read", new DataInputStream(input));

			input.close();
			output.close();

			return nbtCompound;

		} catch (IOException exception) {
			exception.printStackTrace();
		}

		return null;

	}

	public static NbtCompound fromMinecraftCompound(Object compound) {

		try {

			PipedOutputStream output = new PipedOutputStream();
			PipedInputStream input = new PipedInputStream(output);

			DEFAULT.getOptionalReflect("nmsNBTTools").get().execute("write", compound, new DataOutputStream(output));
			NbtTag nbtTag = NbtDeserializer.UNCOMPRESSED.fromStream(input).getTag();

			if (nbtTag.getType() != NbtType.COMPOUND) {
				return new NbtCompound();
			}

			output.close();
			input.close();

			return (NbtCompound) nbtTag;

		} catch (IOException exception) {
			exception.printStackTrace();
		}

		return null;

	}

	/*
	 * 
	 */

	public static NbtCompound itemToNbtCompound(ItemStack itemStack) {
		return fromMinecraftCompound(itemToMinecraftCompound(itemStack));
	}

	public static ItemStack itemFromNbtCompound(NbtCompound compound) {
		return itemFromMinecraftCompound(toMinecraftCompound(compound));
	}

	/*
	 * 
	 */

	public static Object itemToMinecraftCompound(ItemStack itemStack) {

		Object nbtCompound = DEFAULT.getOptionalReflect("nmsNBTCompound").get().init();
		Object nmsStack = DEFAULT.getOptionalReflect("cbItemStack").get().run("nms", itemStack);

		nbtCompound = DEFAULT.getOptionalReflect("nmsItemStack").get().run(nmsStack, "save", nbtCompound);

		return nbtCompound;

	}

	public static ItemStack itemFromMinecraftCompound(Object compound) {
		
		Object nmsStack = DEFAULT.getOptionalReflect("nmsItemStack").get().run("load", compound);
		ItemStack itemStack = (ItemStack) DEFAULT.getOptionalReflect("cbItemStack").get().run("bukkit", nmsStack);
		
		return itemStack;
		
	}

}
