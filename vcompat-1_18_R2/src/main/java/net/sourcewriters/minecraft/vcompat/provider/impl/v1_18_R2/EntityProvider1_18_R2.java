package net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2;

import java.util.EnumMap;
import java.util.function.Function;

import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.EntityProvider;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntity;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityType;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_18_R2.utils.EntityConstructors1_18_R2;

public class EntityProvider1_18_R2 extends EntityProvider<VersionControl1_18_R2> {

    private final EnumMap<NmsEntityType, Function<Level, NmsEntity>> entityMap = new EnumMap<>(NmsEntityType.class);

    protected EntityProvider1_18_R2(VersionControl1_18_R2 versionControl) {
        super(versionControl);
    }

    @SuppressWarnings("unchecked")
    private final Function<Level, NmsEntity> searchConstructor(NmsEntityType type) {
        try {
            return (Function<Level, NmsEntity>) EntityConstructors1_18_R2.class.getField(type.name()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ignore) {
            return null;
        }
    }

    private final Function<Level, NmsEntity> getConstructor(NmsEntityType type) {
        return entityMap.computeIfAbsent(type, (key -> searchConstructor(key)));
    }

    @Override
    public NmsEntity createEntity(org.bukkit.World world, NmsEntityType type) {
        if (!(world instanceof CraftWorld)) {
            return null;
        }
        Function<Level, NmsEntity> function;
        if ((function = getConstructor(type)) == null) {
            return null;
        }
        return function.apply(((CraftWorld) world).getHandle());
    }

}