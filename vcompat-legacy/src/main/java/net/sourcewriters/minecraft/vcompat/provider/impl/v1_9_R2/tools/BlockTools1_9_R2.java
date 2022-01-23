package net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R2.tools;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_9_R2.block.CraftSkull;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.server.v1_9_R2.TileEntitySkull;
import net.sourcewriters.minecraft.vcompat.provider.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.util.constants.MinecraftConstants;

public class BlockTools1_9_R2 extends BlockTools {

    @Override
    public void setHeadTexture(Block block, String texture) {
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return;
        }
        TileEntitySkull entitySkull = ((CraftSkull) state).getTileEntity();
        PropertyMap map = entitySkull.getGameProfile().getProperties();
        map.removeAll("textures");
        map.put("textures", new Property("textures", MinecraftConstants.TEXTURE_SIGNATURE, texture));
    }

    @Override
    public String getHeadTexture(Block block) {
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return null;
        }
        TileEntitySkull entitySkull = ((CraftSkull) state).getTileEntity();
        return entitySkull.getGameProfile().getProperties().get("textures").iterator().next().getValue();
    }

}