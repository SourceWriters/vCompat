package net.sourcewriters.minecraft.versiontools.reflection;

import org.bukkit.World;
import org.bukkit.entity.EntityType;

import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntity;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntityType;

public abstract class EntityProvider<V extends VersionControl> extends VersionHandler<V> {

    protected EntityProvider(V versionControl) {
        super(versionControl);
    }

    public NmsEntity createEntity(World world, EntityType type) {
        return createEntity(world, versionControl.getBukkitConversion().fromEntityType(type));
    }

    public abstract NmsEntity createEntity(World world, NmsEntityType type);

}
