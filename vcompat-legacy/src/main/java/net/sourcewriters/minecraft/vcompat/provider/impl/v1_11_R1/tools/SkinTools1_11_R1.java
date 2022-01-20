package net.sourcewriters.minecraft.vcompat.provider.impl.v1_11_R1.tools;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.provider.tools.SkinTools;
import net.sourcewriters.minecraft.vcompat.util.minecraft.Skin;

public class SkinTools1_11_R1 extends SkinTools {

    @Override
    public Skin skinFromPlayer(Player player) {
        return skinFromGameProfile(((CraftPlayer) player).getHandle().getProfile());
    }

}