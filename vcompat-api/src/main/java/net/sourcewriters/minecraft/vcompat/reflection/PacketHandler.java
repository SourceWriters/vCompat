package net.sourcewriters.minecraft.vcompat.reflection;

import com.syntaxphoenix.syntaxapi.command.ArgumentMap;
import com.syntaxphoenix.syntaxapi.utils.key.IKey;

import net.sourcewriters.minecraft.vcompat.reflection.packet.NmsPacket;

public abstract class PacketHandler<V extends VersionControl> extends VersionHandler<V> {

    protected PacketHandler(V versionControl) {
        super(versionControl);
    }

    public NmsPacket<?> buildPacket(IKey key, ArgumentMap data) {
        return null;
    }

}