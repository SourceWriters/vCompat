package net.sourcewriters.minecraft.versiontools.reflection.reflections;

import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;

import net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.reflection.setup.Reflections;

public class CraftbukkitReflections extends Reflections {

	@Override
	public void setup(ReflectionProvider provider) {

		//
		//
		// Needed classes to create Reflects
		//

		//
		// From ClassCache

		Class<?> nmsIChatBaseComponent = provider.getNMSClass("IChatBaseComponent");

		//
		// From existing reflect

		Class<?> nmsItemStack = provider.getOptionalReflect("nmsItemStack").map(AbstractReflect::getOwner).orElse(null);

		//
		//
		// Create Reflects
		//

		provider.createCBReflect("cbCraftServer", "CraftServer").searchField("minecraftServer", "console");
		provider.createCBReflect("cbCraftWorld", "CraftWorld").searchMethod("handle", "getHandle");
		provider
			.createCBReflect("cbCraftChatMessage", "util.CraftChatMessage")
			.searchMethod("fromString0", "fromString", String.class)
			.searchMethod("fromString1", "fromString", String.class, boolean.class)
			.searchMethod("toString", "fromComponent", nmsIChatBaseComponent);
		provider.createCBReflect("cbCraftPlayer", "entity.CraftPlayer").searchMethod("handle", "getHandle");
		provider
			.createCBReflect("cbItemStack", "inventory.CraftItemStack")
			.searchMethod("nms", "asNMSCopy", ItemStack.class)
			.searchMethod("bukkit", "asBukkitCopy", nmsItemStack);

	}

	@Override
	public int priority() {
		return 20;
	}

}
