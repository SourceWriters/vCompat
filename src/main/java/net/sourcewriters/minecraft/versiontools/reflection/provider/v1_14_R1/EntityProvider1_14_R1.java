package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1;

import java.util.EnumMap;
import java.util.function.Function;

import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;

import net.minecraft.server.v1_14_R1.World;
import net.sourcewriters.minecraft.versiontools.reflection.EntityProvider;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntity;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntityType;
import net.sourcewriters.minecraft.versiontools.reflection.provider.v1_14_R1.utils.EntityConstructors1_14_R1;

public class EntityProvider1_14_R1 extends EntityProvider<VersionControl1_14_R1> {

    private final EnumMap<NmsEntityType, Function<World, NmsEntity>> entityMap = new EnumMap<>(NmsEntityType.class);

    protected EntityProvider1_14_R1(VersionControl1_14_R1 versionControl) {
        super(versionControl);
    }

    @SuppressWarnings("unchecked")
    private final Function<World, NmsEntity> searchConstructor(NmsEntityType type) {
        try {
            return (Function<World, NmsEntity>) EntityConstructors1_14_R1.class.getField(type.name()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ignore) {
            return null;
        }
    }

    private final Function<World, NmsEntity> getConstructor(NmsEntityType type) {
        return entityMap.computeIfAbsent(type, (key -> searchConstructor(key)));
    }

    @Override
    public NmsEntity createEntity(org.bukkit.World world, NmsEntityType type) {
        if (!(world instanceof CraftWorld)) {
            return null;
        }
        Function<World, NmsEntity> function;
        if ((function = getConstructor(type)) == null) {
            return null;
        }
        return function.apply(((CraftWorld) world).getHandle());
    }

}