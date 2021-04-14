package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_16_R3.tools;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.reflection.tools.SkinTools;
import net.sourcewriters.minecraft.vcompat.utils.minecraft.Skin;

public class SkinTools1_16_R3 extends SkinTools {

    @Override
    public Skin skinFromPlayer(Player player) {
        return skinFromGameProfile(((CraftPlayer) player).getHandle().getProfile());
    }

}