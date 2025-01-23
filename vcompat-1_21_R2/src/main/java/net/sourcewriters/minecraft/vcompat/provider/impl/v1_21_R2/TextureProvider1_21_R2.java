package net.sourcewriters.minecraft.vcompat.provider.impl.v1_21_R2;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_21_R2.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_21_R2.block.CraftSkull;
import org.bukkit.craftbukkit.v1_21_R2.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.sourcewriters.minecraft.vcompat.provider.TextureProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;

public class TextureProvider1_21_R2 extends TextureProvider<VersionControl1_21_R2> {

    private final ClassLookup craftEntityStateRef;
    private final ClassLookup craftItemStackRef;
    private final ClassLookup craftMetaSkullRef;

    protected TextureProvider1_21_R2(VersionControl1_21_R2 versionControl) {
        super(versionControl);
        ClassLookupProvider provider = versionControl.getLookupProvider();
        craftEntityStateRef = provider.createLookup("CraftBlockEntityState", CraftBlockEntityState.class).searchField("tileEntity",
            "tileEntity");
        craftItemStackRef = provider.createLookup("CraftItemStack", CraftItemStack.class).searchField("handle", "handle");
        craftMetaSkullRef = provider.createCBLookup("CraftMetaSkull", "inventory.CraftMetaSkull").searchField("profile", "profile");
    }

    @Override
    public GameProfile profileFromBlock(Block block) {
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return null;
        }
        SkullBlockEntity entitySkull = (SkullBlockEntity) craftEntityStateRef.getFieldValue(state, "tileEntity");
        return entitySkull.owner.gameProfile();
    }

    @Override
    public GameProfile profileFromItem(org.bukkit.inventory.ItemStack itemStack) {
        if (!(itemStack.getItemMeta() instanceof SkullMeta)) {
            return null;
        }
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = (GameProfile) craftMetaSkullRef.getFieldValue(meta, "profile");
        if (profile == null) {
            ItemStack stack = null;
            if (itemStack instanceof CraftItemStack) {
                stack = (ItemStack) craftItemStackRef.getFieldValue(itemStack, "handle");
            }
            if (stack == null) {
                stack = CraftItemStack.asNMSCopy(itemStack);
            }
            ResolvableProfile resolved = stack.get(DataComponents.PROFILE);
            if (resolved != null) {
                profile = resolved.gameProfile();
            }
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
        BlockState state = block.getState();
        if (!(state instanceof CraftSkull)) {
            return false;
        }
        SkullBlockEntity entitySkull = (SkullBlockEntity) craftEntityStateRef.getFieldValue(state, "tileEntity");
        entitySkull.setOwner(new ResolvableProfile(profile));
        return true;
    }

}
