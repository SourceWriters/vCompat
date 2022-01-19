package net.sourcewriters.minecraft.vcompat.entity.handler;

import static net.sourcewriters.minecraft.vcompat.entity.handler.EntityRegistry.REGISTRY;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.vcompat.listener.PlayerListener;
import net.sourcewriters.minecraft.vcompat.listener.handler.IPlayerHandler;
import net.sourcewriters.minecraft.vcompat.provider.entity.NmsPlayer;

public class EntityManager implements IPlayerHandler {

    private final ArrayList<CustomEntity> entities = new ArrayList<>();

    private boolean registered = false;

    public EntityManager() {
        start();
    }

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

    /*
     * Listening
     */

    public void start() {
        if (registered) {
            return;
        }
        registered = true;
        PlayerListener.registerHandler(this);
    }

    public void stop() {
        if (!registered) {
            return;
        }
        registered = false;
        PlayerListener.unregisterHandler(this);
    }

    @Override
    public void onJoin(NmsPlayer player) {
        Player bukkitPlayer = player.getBukkitPlayer();
        CustomEntity[] entities = getAll();
        for (CustomEntity entity : entities) {
            if (!entity.isShown(bukkitPlayer)) {
                continue;
            }
            entity.hide(bukkitPlayer).show(bukkitPlayer);
        }
    }

}