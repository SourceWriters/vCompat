package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_9_R2;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.block.CraftSkull;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import com.mojang.authlib.GameProfile;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.minecraft.server.v1_9_R2.GameProfileSerializer;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import net.sourcewriters.minecraft.versiontools.reflection.TextureProvider;

@SuppressWarnings("deprecation")
public class TextureProvider1_9_R2 extends TextureProvider<VersionControl1_9_R2> {

     private final AbstractReflect craftItemStackRef = new Reflect(CraftItemStack.class).searchField("handle", "handle");
     private final AbstractReflect craftMetaSkullRef = new Reflect("org.bukkit.craftbukkit.v1_9_R2.inventory.CraftMetaSkull")
          .searchField("serialized", "serializedProfile").searchField("profile", "profile");
     private final Material skullMaterial = Material.valueOf("SKULL");

     protected TextureProvider1_9_R2(VersionControl1_9_R2 versionControl) {
          super(versionControl);
     }

     @Override
     public GameProfile profileFromBlock(Block block) {
          if (!(block instanceof CraftSkull)) {
               return null;
          }
          return ((CraftSkull) block).getTileEntity().getGameProfile();
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
          if (!(block instanceof CraftSkull)) {
               return false;
          }
          ((CraftSkull) block).getTileEntity().setGameProfile(profile);
          return true;
     }

}