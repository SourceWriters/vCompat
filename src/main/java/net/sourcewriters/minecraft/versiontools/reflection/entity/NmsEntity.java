package net.sourcewriters.minecraft.versiontools.reflection.entity;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.reflection.utils.NmsBoundingBox;

public interface NmsEntity {

	Object getHandle();

	int getId();

	UUID getUniqueId();

	void setCustomName(String name);

	String getCustomName();

	void setGravity(boolean gravity);

	boolean hasGravity();

	void setCustomNameVisible(boolean visible);

	boolean isCustomNameVisible();

	boolean isInteractable();

	boolean isCollidable();

	void setInvisible(boolean invisible);

	boolean isInvisible();

	void setInvulnerable(boolean invulnerable);

	boolean isInvulnerable();

	void setLocation(Location location);

	Location getLocation();

	boolean isShown(Player player);

	void hide(Player... players);

	void show(Player... players);

	UUID[] getVisible();

	Player[] getVisibleAsPlayer();

	void kill();
	
	NmsBoundingBox getBoundingBox();

}
