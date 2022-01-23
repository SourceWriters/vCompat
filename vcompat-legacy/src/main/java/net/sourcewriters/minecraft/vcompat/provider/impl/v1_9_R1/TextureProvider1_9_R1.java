package net.sourcewriters.minecraft.vcompat.provider.impl.v1_9_R1;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_9_R1.block.CraftSkull;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

import net.minecraft.server.v1_9_R1.GameProfileSerializer;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.sourcewriters.minecraft.vcompat.provider.TextureProvider;

@SuppressWarnings("deprecation")
public class TextureProvider1_9_R1 extends TextureProvider<VersionControl1_9_R1> {

    private final ClassLookup craftItemStackRef;
    private final ClassLookup craftMetaSkullRef;
    private final Material skullMaterial = Material.valueOf("SKULL");

    protected TextureProvider1_9_R1(VersionControl1_9_R1 versionControl) {
        super(versionControl);
        ClassLookupProvider provider = versionControl.getLookupProvider();
        craftItemStackRef = provider.createLookup("CraftItemStack", CraftItemStack.class).searchField("handle", "handle");
        craftMetaSkullRef = provider.createCBLookup("CraftMetaSkull", "inventory.CraftMetaSkull")
            .searchField("serialized", "serializedProfile").searchField("profile", "profile");
    }

    @Override
    public GameProfile profileFromBlock(Block block) {
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return null;
        }
        return ((CraftSkull) state).getTileEntity().getGameProfile();
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
                NBTTagCompound stackTag;
                if (!stack.hasTag()) {
                    stackTag = stack.save(new NBTTagCompound());
                } else {
                    stackTag = stack.getTag();
                }
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
        org.bukkit.inventory.ItemStack craftStack = CraftItemStack.asCraftCopy(new MaterialData(skullMaterial, (byte) 3).toItemStack(1));
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
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return false;
        }
        ((CraftSkull) state).getTileEntity().setGameProfile(profile);
        return true;
    }

}