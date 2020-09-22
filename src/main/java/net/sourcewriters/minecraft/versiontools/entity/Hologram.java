package net.sourcewriters.minecraft.versiontools.entity;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;

import net.sourcewriters.minecraft.versiontools.entity.handler.CustomEntity;
import net.sourcewriters.minecraft.versiontools.entity.handler.DefaultEntityType;
import net.sourcewriters.minecraft.versiontools.entity.handler.EntityBuilder;
import net.sourcewriters.minecraft.versiontools.entity.handler.EntityType;
import net.sourcewriters.minecraft.versiontools.reflection.EntityTools;

public class Hologram extends CustomEntity {

	public static final EntityBuilder<Hologram> BUILDER = new HologramBuilder();

	private final ArrayList<Object> entities = new ArrayList<>();
	private final ArrayList<String> lines = new ArrayList<>();

	private Location location;
	private boolean alive = false;
	private boolean progress = false;
	private double offset = 0.25D;

	private Hologram(UUID uniqueId) {
		super(uniqueId);
	}

	/*
	 * CustomEntity handle
	 */

	@Override
	public boolean spawn() {
		if (alive || progress || !isSpawnable() || hasLines())
			return false;
		progress = true;
		synchronized (lines) {
			for (String line : lines) {
				spawnEntity(line);
			}
		}
		alive = true;
		progress = false;
		return true;
	}

	private void spawnEntity(String line) {
		// TODO: Entity reflections
	}

	@Override
	public boolean kill() {
		if (!alive || progress)
			return false;
		progress = true;
		synchronized (entities) {
			for (Object entity : entities) {
				EntityTools.kill(entity);
			}
			entities.clear();
		}
		alive = false;
		progress = false;
		return true;
	}

	private void killEntity(int index) {
		// TODO: Entity reflections
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public EntityType getType() {
		return DefaultEntityType.HOLOGRAM;
	}

	@Override
	public org.bukkit.entity.EntityType getBukkitType() {
		return org.bukkit.entity.EntityType.ARMOR_STAND;
	}

	@Override
	public void setGravity(boolean gravity) {

	}

	@Override
	public void setInvisible(boolean invisible) {

	}

	@Override
	public void setInvulnerable(boolean invulnerable) {

	}

	@Override
	public void teleport(Location location) {
		// TODO: teleport math
		// TODO: Entity reflections
	}

	/*
	 * Hologram handle
	 */

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public double getOffset() {
		return offset;
	}

	public Object[] getEntities() {
		synchronized (entities) {
			return entities.toArray();
		}
	}

	public Hologram addLine(String line) {
		synchronized (lines) {
			lines.add(line);
		}
		if (alive)
			spawnEntity(line);
		return this;
	}

	public Hologram removeLine(String line) {
		int index;
		synchronized (lines) {
			index = lines.indexOf(line);
			lines.remove(line);
		}
		if (alive)
			killEntity(index);
		return this;
	}

	public String[] getLines() {
		synchronized (lines) {
			return lines.toArray(new String[0]);
		}
	}

	public boolean hasLines() {
		synchronized (lines) {
			return lines.isEmpty();
		}
	}

	/*
	 * EntityBuilder class
	 */

	private static class HologramBuilder extends EntityBuilder<Hologram> {

		private HologramBuilder() {
			super(Hologram.class);
		}

		@Override
		public EntityType getType() {
			return DefaultEntityType.HOLOGRAM;
		}

		@Override
		protected Hologram build(UUID uniqueId, Object... arguments) {
			return new Hologram(uniqueId);
		}

	}

}
