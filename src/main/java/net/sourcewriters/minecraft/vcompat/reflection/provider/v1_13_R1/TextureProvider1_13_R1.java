package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_13_R1;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R1.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_13_R1.block.CraftSkull;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.minecraft.server.v1_13_R1.GameProfileSerializer;
import net.minecraft.server.v1_13_R1.ItemStack;
import net.minecraft.server.v1_13_R1.NBTTagCompound;
import net.minecraft.server.v1_13_R1.TileEntitySkull;
import net.sourcewriters.minecraft.vcompat.reflection.TextureProvider;

public class TextureProvider1_13_R1 extends TextureProvider<VersionControl1_13_R1> {

    private final AbstractReflect craftEntityStateRef = new Reflect(CraftBlockEntityState.class).searchField("tileEntity", "tileEntity");
    private final AbstractReflect craftItemStackRef = new Reflect(CraftItemStack.class).searchField("handle", "handle");
    private final AbstractReflect craftMetaSkullRef = new Reflect("org.bukkit.craftbukkit.v1_13_R1.inventory.CraftMetaSkull")
        .searchField("serialized", "serializedProfile").searchField("profile", "profile");

    protected TextureProvider1_13_R1(VersionControl1_13_R1 versionControl) {
        super(versionControl);
    }

    @Override
    public GameProfile profileFromBlock(Block block) {
        if (!(block instanceof CraftSkull)) {
            return null;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
        return entitySkull.getGameProfile();
    }

    @Override
    public GameProfile profileFromItem(org.bukkit.inventory.ItemStack itemStack) {
        if (!(itemStack.getItemMeta() instanceof SkullMeta)) {
            return null;
        }
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = (GameProfile) craftMetaSkullRef.getFieldValue("profile", meta);
        if (profile == null) {
            NBTTagCompound compound = (NBTTagCompound) craftMetaSkullRef.getFieldValue("serialized", meta);
            if (compound == null) {
                ItemStack stack = null;
                if (itemStack instanceof CraftItemStack) {
                    stack = (ItemStack) craftItemStackRef.getFieldValue("handle", itemStack);
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
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
        entitySkull.setGameProfile(profile);
        return true;
    }

}