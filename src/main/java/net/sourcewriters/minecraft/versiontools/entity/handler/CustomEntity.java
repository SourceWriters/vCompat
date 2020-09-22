package net.sourcewriters.minecraft.versiontools.entity.handler;

import java.util.UUID;

import org.bukkit.Location;

public abstract class CustomEntity {

	private EntityManager hook;

	protected final UUID uniqueId;
	protected Location location;
	protected boolean deleted = false;

	public CustomEntity(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}

	/*
	 * Hook / Unhook
	 */

	protected final void hook(EntityManager manager) {
		this.hook = manager;
	}

	public final boolean isSpawnable() {
		return hook != null;
	}

	public final void remove() {
		kill();
		hook.unhook(this);
		hook = null;
	}

	/*
	 * Getter
	 */

	public UUID getUniqueId() {
		return uniqueId;
	}

	public Location getLocation() {
		return location;
	}

	/*
	 * Handle
	 */

	public abstract boolean spawn();

	public abstract boolean kill();

	public abstract boolean isAlive();

	public abstract EntityType getType();

	public abstract org.bukkit.entity.EntityType getBukkitType();

	public abstract void setGravity(boolean gravity);

	public abstract void setInvisible(boolean invisible);

	public abstract void setInvulnerable(boolean invulnerable);

	public abstract void teleport(Location location);

}
