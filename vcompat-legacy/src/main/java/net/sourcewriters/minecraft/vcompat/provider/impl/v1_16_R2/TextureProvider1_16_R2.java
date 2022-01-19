package net.sourcewriters.minecraft.vcompat.provider.impl.v1_16_R2;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_16_R2.block.CraftSkull;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

import net.minecraft.server.v1_16_R2.GameProfileSerializer;
import net.minecraft.server.v1_16_R2.ItemStack;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.TileEntitySkull;
import net.sourcewriters.minecraft.vcompat.provider.TextureProvider;

public class TextureProvider1_16_R2 extends TextureProvider<VersionControl1_16_R2> {

    private final ClassLookup craftEntityStateRef = ClassLookup.of(CraftBlockEntityState.class).searchField("tileEntity", "tileEntity");
    private final ClassLookup craftItemStackRef = ClassLookup.of(CraftItemStack.class).searchField("handle", "handle");
    private final ClassLookup craftMetaSkullRef = ClassLookup.of("org.bukkit.craftbukkit.v1_16_R2.inventory.CraftMetaSkull")
        .searchField("serialized", "serializedProfile").searchField("profile", "profile");

    protected TextureProvider1_16_R2(VersionControl1_16_R2 versionControl) {
        super(versionControl);
    }

    @Override
    public GameProfile profileFromBlock(Block block) {
        if (!(block instanceof CraftSkull)) {
            return null;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue(block, "tileEntity");
        return entitySkull.gameProfile;
    }

    @Override
    public GameProfile profileFromItem(org.bukkit.inventory.ItemStack itemStack) {
        if (!(itemStack.getItemMeta() instanceof SkullMeta)) {
            return null;
        }
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = (GameProfile) craftMetaSkullRef.getFieldValue(meta, "profile");
        if (profile == null) {
            NBTTagCompound compound = (NBTTagCompound) craftMetaSkullRef.getFieldValue(meta, "serialized");
            if (compound == null) {
                ItemStack stack = null;
                if (itemStack instanceof CraftItemStack) {
                    stack = (ItemStack) craftItemStackRef.getFieldValue(itemStack, "handle");
                }
                if (stack == null) {
                    stack = CraftItemStack.asNMSCopy(itemStack);
                }
                NBTTagCompound stackTag = stack.getOrCreateTag();
                if (stackTag.hasKeyOfType("SkullOwner", 10)) {
                    compound = stackTag.getCompound("SkullOwner");
                } else if (stackTag.hasKeyOfType("SkullProfile", 10)) {
                    compound = stackTag.getCompound("SkullProfile");
                }
            }
            if (compound == null) {
                return null;
            }
            profile = GameProfileSerializer.deserialize(compound);
        }
        return profile;
    }

    @Override
    public org.bukkit.inventory.ItemStack getItem(GameProfile profile) {
        org.bukkit.inventory.ItemStack craftStack = CraftItemStack.asCraftCopy(new org.bukkit.inventory.ItemStack(Material.PLAYER_HEAD));
        applyItem(craftStack, profile);
        return craftStack;
    }

    @Override
    public boolean applyItem(org.bukkit.inventory.ItemStack itemStack, GameProfile profile) {
        ItemMeta meta = itemStack.getItemMeta();
        if (!(meta instanceof SkullMeta)) {
            return false;
        }
        SkullMeta skullMeta = (SkullMeta) meta;
        craftMetaSkullRef.setFieldValue(meta, "profile", profile);
        itemStack.setItemMeta(skullMeta);
        return true;
    }

    @Override
    public boolean applyBlock(Block block, GameProfile profile) {
        if (!(block instanceof CraftSkull)) {
            return false;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue(block, "tileEntity");
        entitySkull.setGameProfile(profile);
        return true;
    }

}