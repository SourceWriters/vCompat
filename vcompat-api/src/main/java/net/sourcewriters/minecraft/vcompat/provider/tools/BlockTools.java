package net.sourcewriters.minecraft.vcompat.provider.tools;

import org.bukkit.block.Block;

public abstract class BlockTools {

    public abstract void setHeadTexture(Block block, String texture);

    public abstract String getHeadTexture(Block block);

}