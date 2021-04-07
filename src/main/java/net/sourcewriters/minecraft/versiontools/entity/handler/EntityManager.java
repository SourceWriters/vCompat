package net.sourcewriters.minecraft.versiontools.entity.handler;

import static net.sourcewriters.minecraft.versiontools.entity.handler.EntityRegistry.REGISTRY;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class EntityManager {

    private final ArrayList<CustomEntity> entities = new ArrayList<>();

    public CustomEntity create(EntityType type) {
        return create(REGISTRY.getBuilderOrNull(type));
    }

    public <E extends CustomEntity> E create(EntityBuilder<E> builder, Object... arguments) {
        return builder == null ? null : hook(builder.build(generateId(), arguments));
    }

    public Optional<CustomEntity> getById(UUID uniqueId) {
        synchronized (entities) {
            return entities.stream().filter(entity -> entity.getUniqueId().equals(uniqueId)).findFirst();
        }
    }

    public CustomEntity getByIdOrNull(UUID uniqueId) {
        return getById(uniqueId).orElse(null);
    }

    public CustomEntity[] getByType(EntityType type) {
        synchronized (entities) {
            return entities.stream().filter(entity -> entity.getType().equals(type)).toArray(CustomEntity[]::new);
        }
    }

    public CustomEntity[] getByBukkitType(org.bukkit.entity.EntityType type) {
        synchronized (entities) {
            return entities.stream().filter(entity -> entity.getBukkitType() == type).toArray(CustomEntity[]::new);
        }
    }

    public CustomEntity[] getAll() {
        synchronized (entities) {
            return entities.toArray(new CustomEntity[0]);
        }
    }

    protected <E extends CustomEntity> E hook(E entity) {
        entity.hook(this);
        synchronized (entities) {
            entities.add(entity);
        }
        return entity;
    }

    protected void unhook(CustomEntity entity) {
        synchronized (entities) {
            entities.remove(entity);
        }
    }

    protected UUID generateId() {
        UUID current = UUID.randomUUID();
        while (getById(current).isPresent()) {
            current = UUID.randomUUID();
        }
        return current;
    }

}