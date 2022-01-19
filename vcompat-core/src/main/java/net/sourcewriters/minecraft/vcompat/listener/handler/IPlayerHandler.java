package net.sourcewriters.minecraft.vcompat.listener.handler;

import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsPlayer;

public interface IPlayerHandler {

    default void onJoin(NmsPlayer player) {}

    default void onLeave(NmsPlayer player) {}
    
}
