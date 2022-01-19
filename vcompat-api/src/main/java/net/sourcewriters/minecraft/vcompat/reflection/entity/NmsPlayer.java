package net.sourcewriters.minecraft.vcompat.reflection.entity;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.utils.minecraft.Skin;

public interface NmsPlayer extends NmsEntityLiving {

    Player getBukkitPlayer();

    WrappedContainer getDataAdapter();

    void setSkin(Skin skin);

    Skin getSkin();

    Skin getRealSkin();

    void setName(String name);

    String getName();

    String getRealName();

    void setPlayerListHeader(String text);

    String getPlayerListHeader();

    void setPlayerListFooter(String text);

    String getPlayerListFooter();

    int getPing();

    void setTitleTimes(int fadeIn, int stay, int fadeOut);

    void setPlayerListHeaderAndFooter(String header, String footer);

    void sendSubtitle(String text);

    void sendTitle(String text);

    void sendActionBar(String text);

    void fakeRespawn();

    void respawn();

    void update();

}