package net.sourcewriters.minecraft.versiontools.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.entity.handler.CustomEntity;
import net.sourcewriters.minecraft.versiontools.entity.handler.DefaultEntityType;
import net.sourcewriters.minecraft.versiontools.entity.handler.EntityBuilder;
import net.sourcewriters.minecraft.versiontools.entity.handler.EntityType;
import net.sourcewriters.minecraft.versiontools.reflection.EntityLivingTools;
import net.sourcewriters.minecraft.versiontools.reflection.EntityTools;
import net.sourcewriters.minecraft.versiontools.reflection.entity.ArmorStandTools;

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
		if (alive || progress || !isSpawnable() || !hasLines() || location == null) {
			return false;
		}
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

	@Override
	public boolean kill() {
		if (!alive || progress) {
			return false;
		}
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

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public Location getLocation() {
		return location.clone();
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
		this.location = location;
		if (!alive) {
			return;
		}
		int amount;
		synchronized (entities) {
			amount = entities.size();
		}
		for (int index = 0; index < amount; index++) {
			Object entity;
			synchronized (entities) {
				entity = entities.get(index);
			}
			entity = EntityTools
				.teleport(entity, new Location(location.getWorld(), location.getX(),
					location.getY() + (this.offset * index), location.getZ()));
			synchronized (entities) {
				entities.set(index, entity);
			}
		}
	}

	/*
	 * Entity handle
	 */

	public Hologram show(Player... players) {
		EntityLivingTools.showAllFor(getEntities(), players);
		return this;
	}

	public Hologram hide(Player... players) {
		EntityLivingTools.hideAllFor(getEntities(), players);
		return this;
	}

	private void updateText(int amount) {
		int entityAmount;
		synchronized (entities) {
			entityAmount = entities.size();
		}
		for (int index = 0; index < amount; index++) {
			String line;
			synchronized (lines) {
				line = lines.get(index);
			}
			if (index >= entityAmount) {
				spawnEntity(line);
				continue;
			}
			updateEntity(index, line);
		}
	}

	private void updateOrder(int offset) {
		int amount;
		synchronized (entities) {
			amount = entities.size();
		}
		if (amount == offset) {
			return;
		}
		for (int index = offset; index < amount; index++) {
			Object entity;
			synchronized (entities) {
				entity = entities.get(index);
			}
			EntityTools.setPosition(entity, location.getX(), location.getY() + (this.offset * index), location.getZ());
		}
	}

	private void spawnEntity(String line) {

		Object entity = ArmorStandTools.createArmorStand(location.getWorld());

		EntityTools.setCustomName(entity, line);
		EntityTools.setCustomNameVisible(entity, true);
		EntityTools.setInvisible(entity, true);
		EntityTools.setInvulnerable(entity, true);
		EntityTools.setGravity(entity, false);
		ArmorStandTools.setSmall(entity, true);

		int position;
		synchronized (entities) {
			position = entities.size();
			entities.add(entity);
		}

		EntityTools.setPosition(entity, location.getX(), location.getY() + (offset * position), location.getZ());

	}

	private void updateEntity(int index, String line) {
		Object entity;
		synchronized (entities) {
			entity = entities.get(index);
		}
		EntityTools.setCustomName(entity, line);
	}

	private void killEntity(int index) {
		Object entity;
		synchronized (entities) {
			entity = entities.remove(index);
		}
		EntityTools.kill(entity);
	}

	/*
	 * Text handle
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

	public int lineCount() {
		synchronized (lines) {
			return lines.size();
		}
	}

	public int indexOfLine(String line) {
		synchronized (lines) {
			return lines.indexOf(line);
		}
	}

	public boolean containsLine(String line) {
		synchronized (lines) {
			return lines.contains(line);
		}
	}

	public boolean containsLine(int index) {
		return index >= 0 && index < lineCount();
	}

	public Hologram setLines(String... lines) {
		return setLines(Arrays.asList(Objects.requireNonNull(lines)));
	}

	public Hologram setLines(Collection<String> lines) {
		Objects.requireNonNull(lines);
		synchronized (this.lines) {
			this.lines.clear();
			this.lines.addAll(lines);
		}
		if (alive) {
			updateText(lines.size());
		}
		return this;
	}

	public Hologram setLine(int index, String line) {
		Objects.requireNonNull(line);
		if (!containsLine(index)) {
			return this;
		}
		synchronized (lines) {
			lines.set(index, line);
		}
		if (alive) {
			updateEntity(index, line);
		}
		return this;
	}

	public Hologram addLine(String line) {
		Objects.requireNonNull(line);
		synchronized (lines) {
			lines.add(line);
		}
		if (alive) {
			spawnEntity(line);
		}
		return this;
	}

	public Hologram addLine(int index, String line) {
		Objects.requireNonNull(line);
		int size = lineCount();
		index = index < 0 ? 0 : (index >= size ? size - 1 : index);
		synchronized (lines) {
			lines.add(index, line);
		}
		if (alive) {
			spawnEntity(line);
		}
		return this;
	}

	public boolean removeLine(int index) {
		if (!containsLine(index)) {
			return false;
		}
		synchronized (lines) {
			lines.remove(index);
		}
		if (alive) {
			killEntity(index);
			updateOrder(index);
		}
		return true;
	}

	public boolean removeLine(String line) {
		return removeLine(indexOfLine(line));
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
