package net.sourcewriters.minecraft.versiontools.entity.handler;

import java.util.HashMap;
import java.util.Optional;

public final class EntityRegistry {

     public static final EntityRegistry REGISTRY = new EntityRegistry();

     private final HashMap<EntityType, EntityBuilder<?>> builders = new HashMap<>();

     private EntityRegistry() {}

     public EntityType[] getTypes() {
          synchronized (builders) {
               return builders.keySet().toArray(new EntityType[0]);
          }
     }

     public EntityBuilder<?>[] getBuilders() {
          synchronized (builders) {
               return builders.values().toArray(new EntityBuilder<?>[0]);
          }
     }

     public Optional<EntityType> getType(String name) {
          synchronized (builders) {
               return builders.keySet().stream().filter(type -> type.getName().equals(name)).findFirst();
          }
     }

     public EntityType getTypeOrNull(String name) {
          return getType(name).orElse(null);
     }

     public Optional<EntityBuilder<?>> getBuilder(EntityType type) {
          return Optional.ofNullable(getBuilderOrNull(type));
     }

     public EntityBuilder<?> getBuilderOrNull(EntityType type) {
          synchronized (builders) {
               return builders.get(type);
          }
     }

     public boolean register(EntityBuilder<?> builder) {
          if (builder == null) {
               return false;
          }
          synchronized (builders) {
               if (builders.containsKey(builder.getType())) {
                    return false;
               }
               builders.put(builder.getType(), builder);
               return true;
          }
     }

     public boolean unregister(EntityBuilder<?> builder) {
          synchronized (builders) {
               return builder != null && builders.remove(builder.getType(), builder);
          }
     }

     public boolean unregister(EntityType type) {
          synchronized (builders) {
               return type != null && builders.remove(type) != null;
          }
     }

}
