package net.sourcewriters.minecraft.versiontools.reflection.translate;

import java.util.Collection;
import java.util.Optional;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public abstract class BlockTools {
	
	public static void setHeadDeprecated(Block block, String texture) {

		if (texture == null)
			return;

		Optional<Reflect> option0 = REFLECTION.get("nms_entity_skull");
		if (!option0.isPresent())
			return;

		Optional<Reflect> option1 = REFLECTION.get("cb_block_entityState");
		if (!option1.isPresent())
			return;

		Reflect nmsSkull = option0.get();
		Reflect cbEntityState = option1.get();

		BlockState state = block.getState();
		if (!(state instanceof Skull))
			return;

		GameProfile gameProfile = Heads.toBlockProfile(texture);

		Object tileEntity = cbEntityState.run(state, "entity");
		nmsSkull.execute(tileEntity, "profile", gameProfile);

	}

	/*
	 * 
	 */

	public static String tryGetTextureValueDeprecated(BlockState state) {

		Optional<Reflect> option0 = REFLECTION.get("cb_block_skull");
		if (!option0.isPresent())
			return "";

		Reflect skull = option0.get();

		if (!(state instanceof Skull))
			return "";

		GameProfile gameProfile = (GameProfile) skull.getFieldValue("profile", state);

		if (gameProfile == null)
			return "";

		Collection<Property> property = gameProfile.getProperties().get("textures");
		if (property.size() == 0)
			return "";

		return Heads.shortTexture(property.iterator().next().getValue());

	}

}
