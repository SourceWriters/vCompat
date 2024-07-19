package net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4;

import java.util.EnumMap;
import java.util.function.Function;

import org.bukkit.craftbukkit.v1_20_R4.CraftWorld;

import net.minecraft.world.level.Level;
import net.sourcewriters.minecraft.vcompat.provider.EntityProvider;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntity;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsEntityType;
import net.sourcewriters.minecraft.vcompat.provider.impl.v1_20_R4.utils.EntityConstructors1_20_R4;

public class EntityProvider1_20_R4 extends EntityProvider<VersionControl1_20_R4> {

    private final EnumMap<NmsEntityType, Function<Level, NmsEntity>> entityMap = new EnumMap<>(NmsEntityType.class);

    protected EntityProvider1_20_R4(VersionControl1_20_R4 versionControl) {
        super(versionControl);
    }

    @SuppressWarnings("unchecked")
    private final Function<Level, NmsEntity> searchConstructor(NmsEntityType type) {
        try {
            return (Function<Level, NmsEntity>) EntityConstructors1_20_R4.class.getField(type.name()).get(null);
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