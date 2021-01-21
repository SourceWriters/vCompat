package net.sourcewriters.minecraft.versiontools.entity.handler;

import java.util.UUID;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class EntityBuilder<E extends CustomEntity> {

    protected final Class<E> owner;
    protected final Reflect reference;

    public EntityBuilder(Class<E> owner) {
        this.owner = owner;
        this.reference = new Reflect(owner);
    }

    public final Class<E> getOwner() {
        return owner;
    }

    public final Reflect getReference() {
        return reference;
    }

    public abstract EntityType getType();

    protected abstract E build(UUID uniqueId, Object... arguments);

}
