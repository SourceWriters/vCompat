package net.sourcewriters.minecraft.versiontools.setup.reflections;

import static net.sourcewriters.minecraft.versiontools.utils.java.ReflectionTools.*;
import static net.sourcewriters.minecraft.versiontools.version.Versions.*;

import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;

import net.sourcewriters.minecraft.versiontools.setup.ReflectionProvider;
import net.sourcewriters.minecraft.versiontools.setup.Reflections;

public class CraftbukkitReflections extends Reflections {

	@Override
	public void setup(ReflectionProvider provider) {

		//
		//
		// Needed classes to create Reflects
		//

		Class<?> nmsItemStack = provider.getOptionalReflect("nmsItemStack").map(AbstractReflect::getOwner).orElse(null);

		//
		//
		// Create Reflects
		//

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
