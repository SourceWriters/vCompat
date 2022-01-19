package net.sourcewriters.minecraft.vcompat.entity.handler;

public enum DefaultEntityType implements EntityType {

    HOLOGRAM,
    NPC;

    @Override
    public String getName() {
        return name();
    }

}