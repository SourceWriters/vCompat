package net.sourcewriters.minecraft.versiontools.reflection.utils;

import static org.bukkit.util.NumberConversions.checkFinite;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import net.sourcewriters.minecraft.versiontools.utils.validation.ValidationHelper;

public class NmsBoundingBox {

	public static NmsBoundingBox of(Vector min, Vector max) {
		Objects.requireNonNull(min, "min is null!");
		Objects.requireNonNull(max, "max is null!");
		return new NmsBoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
	}

	public static NmsBoundingBox of(Location min, Location max) {
		Objects.requireNonNull(min, "min is null!");
		Objects.requireNonNull(max, "max is null!");
		ValidationHelper.isTrue(Objects.equals(min.getWorld(), max.getWorld()), "Locations from different worlds!");
		return new NmsBoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
	}

	public static NmsBoundingBox of(Block min, Block max) {
		Objects.requireNonNull(min, "min is null!");
		Objects.requireNonNull(max, "max is null!");
		ValidationHelper.isTrue(Objects.equals(min.getWorld(), max.getWorld()), "Blocks from different worlds!");
		int x1 = min.getX();
		int y1 = min.getY();
		int z1 = min.getZ();
		int x2 = max.getX();
		int y2 = max.getY();
		int z2 = max.getZ();
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		int minZ = Math.min(z1, z2);
		int maxX = Math.max(x1, x2) + 1;
		int maxY = Math.max(y1, y2) + 1;
		int maxZ = Math.max(z1, z2) + 1;
		return new NmsBoundingBox((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ);
	}

	public static NmsBoundingBox of(Block block) {
		Objects.requireNonNull(block, "Block is null!");
		return new NmsBoundingBox((double) block.getX(), (double) block.getY(), (double) block.getZ(), (double) (block.getX() + 1), (double) (block.getY() + 1),
			(double) (block.getZ() + 1));
	}

	public static NmsBoundingBox of(Vector center, double x, double y, double z) {
		Objects.requireNonNull(center, "Center is null!");
		return new NmsBoundingBox(center.getX() - x, center.getY() - y, center.getZ() - z, center.getX() + x, center.getY() + y, center.getZ() + z);
	}

	public static NmsBoundingBox of(Location center, double x, double y, double z) {
		Objects.requireNonNull(center, "Center is null!");
		return new NmsBoundingBox(center.getX() - x, center.getY() - y, center.getZ() - z, center.getX() + x, center.getY() + y, center.getZ() + z);
	}

	private double minX, minY, minZ;
	private double maxX, maxY, maxZ;

	public NmsBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		resize(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMinZ() {
		return minZ;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public Vector getMin() {
		return new Vector(getMinX(), getMinY(), getMinZ());
	}

	public Vector getMax() {
		return new Vector(getMaxX(), getMaxY(), getMaxZ());
	}

	public NmsBoundingBox resize(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		checkFinite(minX, "minX not finite");
		checkFinite(minY, "minY not finite");
		checkFinite(minZ, "minZ not finite");
		checkFinite(maxX, "maxX not finite");
		checkFinite(maxY, "maxY not finite");
		checkFinite(maxZ, "maxZ not finite");
		this.minX = Math.min(minX, maxX);
		this.minY = Math.min(minY, maxY);
		this.minZ = Math.min(minZ, maxZ);
		this.maxX = Math.max(minX, maxX);
		this.maxY = Math.max(minY, maxY);
		this.maxZ = Math.max(minZ, maxZ);
		return this;
	}

	public double getWidth() {
		return getMaxX() - getMinX();
	}

	public double getDepth() {
		return getMaxZ() - getMinZ();
	}

	public double getHeight() {
		return getMaxY() - getMinY();
	}

	public double getVolume() {
		return getHeight() * getWidth() * getDepth();
	}

	public double getCenterX() {
		return getMinX() + getWidth() * 0.5D;
	}

	public double getCenterY() {
		return getMinY() + getHeight() * 0.5D;
	}

	public double getCenterZ() {
		return getMinZ() + getDepth() * 0.5D;
	}

	public Vector getCenter() {
		return new Vector(getCenterX(), getCenterY(), getCenterZ());
	}

	public NmsBoundingBox copy(NmsBoundingBox other) {
		Objects.requireNonNull(other, "Other bounding box is null!");
		return resize(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
	}

	public NmsBoundingBox expand(double negativeX, double negativeY, double negativeZ, double positiveX, double positiveY, double positiveZ) {
		if (negativeX == 0.0D && negativeY == 0.0D && negativeZ == 0.0D && positiveX == 0.0D && positiveY == 0.0D && positiveZ == 0.0D) {
			return this;
		} else {
			double newMinX = getMinX() - negativeX;
			double newMinY = getMinY() - negativeY;
			double newMinZ = getMinZ() - negativeZ;
			double newMaxX = getMaxX() + positiveX;
			double newMaxY = getMaxY() + positiveY;
			double newMaxZ = getMaxZ() + positiveZ;
			double centerZ;
			if (newMinX > newMaxX) {
				centerZ = getCenterX();
				if (newMaxX >= centerZ) {
					newMinX = newMaxX;
				} else if (newMinX <= centerZ) {
					newMaxX = newMinX;
				} else {
					newMinX = centerZ;
					newMaxX = centerZ;
				}
			}

			if (newMinY > newMaxY) {
				centerZ = getCenterY();
				if (newMaxY >= centerZ) {
					newMinY = newMaxY;
				} else if (newMinY <= centerZ) {
					newMaxY = newMinY;
				} else {
					newMinY = centerZ;
					newMaxY = centerZ;
				}
			}

			if (newMinZ > newMaxZ) {
				centerZ = getCenterZ();
				if (newMaxZ >= centerZ) {
					newMinZ = newMaxZ;
				} else if (newMinZ <= centerZ) {
					newMaxZ = newMinZ;
				} else {
					newMinZ = centerZ;
					newMaxZ = centerZ;
				}
			}

			return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
		}
	}

	public NmsBoundingBox expand(double x, double y, double z) {
		return expand(x, y, z, x, y, z);
	}

	public NmsBoundingBox expand(Vector expansion) {
		Objects.requireNonNull(expansion, "Expansion is null!");
		return expand(expansion.getX(), expansion.getY(), expansion.getZ());
	}

	public NmsBoundingBox expand(double expansion) {
		return expand(expansion, expansion, expansion, expansion, expansion, expansion);
	}

	public NmsBoundingBox expand(double dirX, double dirY, double dirZ, double expansion) {
		if (expansion == 0.0D) {
			return this;
		} else if (dirX == 0.0D && dirY == 0.0D && dirZ == 0.0D) {
			return this;
		} else {
			double negativeX = dirX < 0.0D ? -dirX * expansion : 0.0D;
			double negativeY = dirY < 0.0D ? -dirY * expansion : 0.0D;
			double negativeZ = dirZ < 0.0D ? -dirZ * expansion : 0.0D;
			double positiveX = dirX > 0.0D ? dirX * expansion : 0.0D;
			double positiveY = dirY > 0.0D ? dirY * expansion : 0.0D;
			double positiveZ = dirZ > 0.0D ? dirZ * expansion : 0.0D;
			return expand(negativeX, negativeY, negativeZ, positiveX, positiveY, positiveZ);
		}
	}

	public NmsBoundingBox expand(Vector direction, double expansion) {
		Objects.requireNonNull(direction, "Direction is null!");
		return expand(direction.getX(), direction.getY(), direction.getZ(), expansion);
	}

	public NmsBoundingBox expand(BlockFace blockFace, double expansion) {
		Objects.requireNonNull(blockFace, "Block face is null!");
		return blockFace == BlockFace.SELF ? this : expand(blockFace.getDirection(), expansion);
	}

	public NmsBoundingBox expandDirectional(double dirX, double dirY, double dirZ) {
		return expand(dirX, dirY, dirZ, 1.0D);
	}

	public NmsBoundingBox expandDirectional(Vector direction) {
		Objects.requireNonNull(direction, "Expansion is null!");
		return expand(direction.getX(), direction.getY(), direction.getZ(), 1.0D);
	}

	public NmsBoundingBox union(double posX, double posY, double posZ) {
		double newMinX = Math.min(getMinX(), posX);
		double newMinY = Math.min(getMinY(), posY);
		double newMinZ = Math.min(getMinZ(), posZ);
		double newMaxX = Math.max(getMaxX(), posX);
		double newMaxY = Math.max(getMaxY(), posY);
		double newMaxZ = Math.max(getMaxZ(), posZ);
		return newMinX == getMinX() && newMinY == getMinY() && newMinZ == getMinZ() && newMaxX == getMaxX() && newMaxY == getMaxY() && newMaxZ == getMaxZ()
			? this
			: resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
	}

	public NmsBoundingBox union(Vector position) {
		Objects.requireNonNull(position, "Position is null!");
		return union(position.getX(), position.getY(), position.getZ());
	}

	public NmsBoundingBox union(Location position) {
		Objects.requireNonNull(position, "Position is null!");
		return union(position.getX(), position.getY(), position.getZ());
	}

	public NmsBoundingBox union(NmsBoundingBox other) {
		Objects.requireNonNull(other, "Other bounding box is null!");
		if (contains(other)) {
			return this;
		} else {
			double newMinX = Math.min(getMinX(), other.getMinX());
			double newMinY = Math.min(getMinY(), other.getMinY());
			double newMinZ = Math.min(getMinZ(), other.getMinZ());
			double newMaxX = Math.max(getMaxX(), other.getMaxX());
			double newMaxY = Math.max(getMaxY(), other.getMaxY());
			double newMaxZ = Math.max(getMaxZ(), other.getMaxZ());
			return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
		}
	}

	public NmsBoundingBox intersection(NmsBoundingBox other) {
		Objects.requireNonNull(other, "Other bounding box is null!");
		ValidationHelper.isTrue(overlaps(other), "The bounding boxes do not overlap!");
		double newMinX = Math.max(getMinX(), other.getMinX());
		double newMinY = Math.max(getMinY(), other.getMinY());
		double newMinZ = Math.max(getMinZ(), other.getMinZ());
		double newMaxX = Math.min(getMaxX(), other.getMaxX());
		double newMaxY = Math.min(getMaxY(), other.getMaxY());
		double newMaxZ = Math.min(getMaxZ(), other.getMaxZ());
		return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
	}

	public NmsBoundingBox shift(double shiftX, double shiftY, double shiftZ) {
		return shiftX == 0.0D && shiftY == 0.0D && shiftZ == 0.0D ? this
			: resize(getMinX() + shiftX, getMinY() + shiftY, getMinZ() + shiftZ, getMaxX() + shiftX, getMaxY() + shiftY, getMaxZ() + shiftZ);
	}

	public NmsBoundingBox shift(Vector shift) {
		Objects.requireNonNull(shift, "Shift is null!");
		return shift(shift.getX(), shift.getY(), shift.getZ());
	}

	public NmsBoundingBox shift(Location shift) {
		Objects.requireNonNull(shift, "Shift is null!");
		return shift(shift.getX(), shift.getY(), shift.getZ());
	}

	private boolean overlaps(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return getMinX() < maxX && getMaxX() > minX && getMinY() < maxY && getMaxY() > minY && getMinZ() < maxZ && getMaxZ() > minZ;
	}

	public boolean overlaps(NmsBoundingBox other) {
		Objects.requireNonNull(other, "Other bounding box is null!");
		return overlaps(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
	}

	public boolean overlaps(Vector min, Vector max) {
		Objects.requireNonNull(min, "Min is null!");
		Objects.requireNonNull(max, "Max is null!");
		double x1 = min.getX();
		double y1 = min.getY();
		double z1 = min.getZ();
		double x2 = max.getX();
		double y2 = max.getY();
		double z2 = max.getZ();
		return overlaps(x1, y1, z1, x2, y2, z2);
	}

	public boolean contains(double x, double y, double z) {
		return x >= getMinX() && x < getMaxX() && y >= getMinY() && y < getMaxY() && z >= getMinZ() && z < getMaxZ();
	}

	public boolean contains(Vector position) {
		Objects.requireNonNull(position, "Position is null!");
		return contains(position.getX(), position.getY(), position.getZ());
	}

	private boolean contains(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return getMinX() <= minX && getMaxX() >= maxX && getMinY() <= minY && getMaxY() >= maxY && getMinZ() <= minZ && getMaxZ() >= maxZ;
	}

	public boolean contains(NmsBoundingBox other) {
		Objects.requireNonNull(other, "Other bounding box is null!");
		return contains(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
	}

	public boolean contains(Vector min, Vector max) {
		Objects.requireNonNull(min, "Min is null!");
		Objects.requireNonNull(max, "Max is null!");
		double x1 = min.getX();
		double y1 = min.getY();
		double z1 = min.getZ();
		double x2 = max.getX();
		double y2 = max.getY();
		double z2 = max.getZ();
		return contains(x1, y1, z1, x2, y2, z2);
	}

	public RayTraceResult rayTrace(Vector start, Vector direction, double maxDistance) {
		Objects.requireNonNull(start, "Start is null!");
		start.checkFinite();
		Objects.requireNonNull(direction, "Direction is null!");
		direction.checkFinite();
		ValidationHelper.isTrue(direction.lengthSquared() > 0.0D, "Direction's magnitude is 0!");
		if (maxDistance < 0.0D) {
			return null;
		} else {
			double startX = start.getX();
			double startY = start.getY();
			double startZ = start.getZ();
			Vector dir = direction.clone();
			dir.setX(dir.getX() == -0.0D ? 0.0D : dir.getX());
			dir.setY(dir.getY() == -0.0D ? 0.0D : dir.getY());
			dir.setZ(dir.getZ() == -0.0D ? 0.0D : dir.getZ());
			double dirX = dir.getX();
			double dirY = dir.getY();
			double dirZ = dir.getZ();
			double divX = 1.0D / dirX;
			double divY = 1.0D / dirY;
			double divZ = 1.0D / dirZ;
			double tMin;
			double tMax;
			BlockFace hitBlockFaceMin;
			BlockFace hitBlockFaceMax;
			if (dirX >= 0.0D) {
				tMin = (getMinX() - startX) * divX;
				tMax = (getMaxX() - startX) * divX;
				hitBlockFaceMin = BlockFace.WEST;
				hitBlockFaceMax = BlockFace.EAST;
			} else {
				tMin = (getMaxX() - startX) * divX;
				tMax = (getMinX() - startX) * divX;
				hitBlockFaceMin = BlockFace.EAST;
				hitBlockFaceMax = BlockFace.WEST;
			}

			double tyMin;
			double tyMax;
			BlockFace hitBlockFaceYMin;
			BlockFace hitBlockFaceYMax;
			if (dirY >= 0.0D) {
				tyMin = (getMinY() - startY) * divY;
				tyMax = (getMaxY() - startY) * divY;
				hitBlockFaceYMin = BlockFace.DOWN;
				hitBlockFaceYMax = BlockFace.UP;
			} else {
				tyMin = (getMaxY() - startY) * divY;
				tyMax = (getMinY() - startY) * divY;
				hitBlockFaceYMin = BlockFace.UP;
				hitBlockFaceYMax = BlockFace.DOWN;
			}

			if (tMin <= tyMax && tMax >= tyMin) {
				if (tyMin > tMin) {
					tMin = tyMin;
					hitBlockFaceMin = hitBlockFaceYMin;
				}

				if (tyMax < tMax) {
					tMax = tyMax;
					hitBlockFaceMax = hitBlockFaceYMax;
				}

				double tzMin;
				double tzMax;
				BlockFace hitBlockFaceZMin;
				BlockFace hitBlockFaceZMax;
				if (dirZ >= 0.0D) {
					tzMin = (getMinZ() - startZ) * divZ;
					tzMax = (getMaxZ() - startZ) * divZ;
					hitBlockFaceZMin = BlockFace.NORTH;
					hitBlockFaceZMax = BlockFace.SOUTH;
				} else {
					tzMin = (getMaxZ() - startZ) * divZ;
					tzMax = (getMinZ() - startZ) * divZ;
					hitBlockFaceZMin = BlockFace.SOUTH;
					hitBlockFaceZMax = BlockFace.NORTH;
				}

				if (tMin <= tzMax && tMax >= tzMin) {
					if (tzMin > tMin) {
						tMin = tzMin;
						hitBlockFaceMin = hitBlockFaceZMin;
					}

					if (tzMax < tMax) {
						tMax = tzMax;
						hitBlockFaceMax = hitBlockFaceZMax;
					}

					if (tMax < 0.0D) {
						return null;
					} else if (tMin > maxDistance) {
						return null;
					} else {
						double t;
						BlockFace hitBlockFace;
						if (tMin < 0.0D) {
							t = tMax;
							hitBlockFace = hitBlockFaceMax;
						} else {
							t = tMin;
							hitBlockFace = hitBlockFaceMin;
						}

						Vector hitPosition = dir.multiply(t).add(start);
						return new RayTraceResult(hitPosition, hitBlockFace);
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	public int hashCode() {
		int result = 1;
		long temp = Double.doubleToLongBits(getMaxX());
		result = 31 * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(getMaxY());
		result = 31 * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(getMaxZ());
		result = 31 * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(getMinX());
		result = 31 * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(getMinY());
		result = 31 * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(getMinZ());
		result = 31 * result + (int) (temp ^ temp >>> 32);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof NmsBoundingBox)) {
			return false;
		} else {
			NmsBoundingBox other = (NmsBoundingBox) obj;
			if (Double.doubleToLongBits(getMaxX()) != Double.doubleToLongBits(other.getMaxX())) {
				return false;
			} else if (Double.doubleToLongBits(getMaxY()) != Double.doubleToLongBits(other.getMaxY())) {
				return false;
			} else if (Double.doubleToLongBits(getMaxZ()) != Double.doubleToLongBits(other.getMaxZ())) {
				return false;
			} else if (Double.doubleToLongBits(getMinX()) != Double.doubleToLongBits(other.getMinX())) {
				return false;
			} else if (Double.doubleToLongBits(getMinY()) != Double.doubleToLongBits(other.getMinY())) {
				return false;
			} else {
				return Double.doubleToLongBits(getMinZ()) == Double.doubleToLongBits(other.getMinZ());
			}
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NmsBoundingBox [minX=");
		builder.append(getMinX());
		builder.append(", minY=");
		builder.append(getMinY());
		builder.append(", minZ=");
		builder.append(getMinZ());
		builder.append(", maxX=");
		builder.append(getMaxX());
		builder.append(", maxY=");
		builder.append(getMaxY());
		builder.append(", maxZ=");
		builder.append(getMaxZ());
		builder.append("]");
		return builder.toString();
	}

	public NmsBoundingBox clone() {
		try {
			return (NmsBoundingBox) super.clone();
		} catch (CloneNotSupportedException var2) {
			throw new Error(var2);
		}
	}

}
