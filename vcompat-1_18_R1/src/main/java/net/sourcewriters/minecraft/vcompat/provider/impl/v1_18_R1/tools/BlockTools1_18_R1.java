package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R1.tools;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftSkull;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;
import net.sourcewriters.minecraft.vcompat.provider.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.util.constants.MinecraftConstants;

public class BlockTools1_18_R1 extends BlockTools {

    private final ClassLookup craftEntityStateRef = ClassLookup.of(CraftSkull.class).searchField("tileEntity", "tileEntity");

    @Override
    public void setHeadTexture(Block block, String texture) {
        if (!(block instanceof CraftSkull)) {
            return;
        }
        SkullBlockEntity entitySkull = (SkullBlockEntity) craftEntityStateRef.getFieldValue(block, "tileEntity");
        PropertyMap map = entitySkull.owner.getProperties();
        map.removeAll("textures");
        map.put("textures", new Property("textures", MinecraftConstants.TEXTURE_SIGNATURE, texture));
    }

    @Override
    public String getHeadTexture(Block block) {
        if (!(block instanceof CraftSkull)) {
            return null;
        }
        SkullBlockEntity entitySkull = (SkullBlockEntity) craftEntityStateRef.getFieldValue(block, "tileEntity");
        return entitySkull.owner.getProperties().get("textures").iterator().next().getValue();
    }

}