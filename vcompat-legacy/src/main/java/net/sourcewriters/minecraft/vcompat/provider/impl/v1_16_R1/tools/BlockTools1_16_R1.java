package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R1.tools;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_16_R1.block.CraftSkull;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

import net.minecraft.server.v1_16_R1.TileEntitySkull;
import net.sourcewriters.minecraft.vcompat.provider.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.util.constants.MinecraftConstants;

public class BlockTools1_16_R1 extends BlockTools {

    private final ClassLookup craftEntityStateRef;

    public BlockTools1_16_R1() {
        craftEntityStateRef = VersionCompatProvider.get().getLookupProvider().createLookup("CraftSkull", CraftSkull.class)
            .searchField("tileEntity", "tileEntity");
    }

    @Override
    public void setHeadTexture(Block block, String texture) {
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue(state, "tileEntity");
        PropertyMap map = entitySkull.gameProfile.getProperties();
        map.removeAll("textures");
        map.put("textures", new Property("textures", MinecraftConstants.TEXTURE_SIGNATURE, texture));
    }

    @Override
    public String getHeadTexture(Block block) {
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return null;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue(state, "tileEntity");
        return entitySkull.gameProfile.getProperties().get("textures").iterator().next().getValue();
    }

}