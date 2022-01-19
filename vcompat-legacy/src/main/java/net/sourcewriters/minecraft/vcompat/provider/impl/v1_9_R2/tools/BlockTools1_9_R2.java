package net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R2.tools;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.block.CraftSkull;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

import net.minecraft.server.v1_9_R2.TileEntitySkull;
import net.sourcewriters.minecraft.vcompat.provider.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.util.constants.MinecraftConstants;

public class BlockTools1_9_R2 extends BlockTools {

    private final ClassLookup craftEntityStateRef = ClassLookup.of(CraftSkull.class).searchField("tileEntity", "tileEntity");

    @Override
    public void setHeadTexture(Block block, String texture) {
        if (!(block instanceof CraftSkull)) {
            return;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue(block, "tileEntity");
        PropertyMap map = entitySkull.getGameProfile().getProperties();
        map.removeAll("textures");
        map.put("textures", new Property("textures", MinecraftConstants.TEXTURE_SIGNATURE, texture));
    }

    @Override
    public String getHeadTexture(Block block) {
        if (!(block instanceof CraftSkull)) {
            return null;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue(block, "tileEntity");
        return entitySkull.getGameProfile().getProperties().get("textures").iterator().next().getValue();
    }

}