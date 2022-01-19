package net.sourcewriters.minecraft.vcompat.provider.impl.v1_12_R1.tools;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftSkull;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.minecraft.server.v1_12_R1.TileEntitySkull;
import net.sourcewriters.minecraft.vcompat.reflection.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.utils.constants.MinecraftConstants;

public class BlockTools1_12_R1 extends BlockTools {

    private final AbstractReflect craftEntityStateRef = new Reflect(CraftSkull.class).searchField("tileEntity", "tileEntity");

    @Override
    public void setHeadTexture(Block block, String texture) {
        if (!(block instanceof CraftSkull)) {
            return;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
        PropertyMap map = entitySkull.getGameProfile().getProperties();
        map.removeAll("textures");
        map.put("textures", new Property("textures", MinecraftConstants.TEXTURE_SIGNATURE, texture));
    }

    @Override
    public String getHeadTexture(Block block) {
        if (!(block instanceof CraftSkull)) {
            return null;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
        return entitySkull.getGameProfile().getProperties().get("textures").iterator().next().getValue();
    }

}