package net.sourcewriters.minecraft.vcompat.version;

public class MinecraftVersion implements Comparable<MinecraftVersion> {
    
    public static MinecraftVersion ofBukkit(String version) {
        int index = version.indexOf('-');
        if (index == -1) {
            return of(version);
        }
        return of(version.substring(0, index));
    }

    public static MinecraftVersion of(String version) {
        if (version.isBlank()) {
            return new MinecraftVersion(0, 0, 0);
        }
        String[] parts = version.split("\\.");
        try {
            if (parts.length == 1) {
                return new MinecraftVersion(Integer.parseInt(parts[0]), 0, 0);
            }
            if (parts.length == 2) {
                return new MinecraftVersion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), 0);
            }
            return new MinecraftVersion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    public static MinecraftVersion of(int major, int minor, int patch) {
        return new MinecraftVersion(major, minor, patch);
    }

    protected final int major, minor, patch;

    protected MinecraftVersion(int major, int minor, int patch) {
        this.major = Math.max(major, 0);
        this.minor = Math.max(minor, 0);
        this.patch = Math.max(patch, 0);
    }

    public final int major() {
        return major;
    }

    public final int minor() {
        return minor;
    }

    public final int patch() {
        return patch;
    }

    public boolean isSame(MinecraftVersion other) {
        return compareTo(other) == 0;
    }

    public boolean isBetween(MinecraftVersion min, MinecraftVersion max) {
        return !(compareTo(min) < 0 || compareTo(max) > 0);
    }

    @Override
    public int compareTo(MinecraftVersion o) {
        int diff = Integer.compare(major, o.major);
        if (diff != 0) {
            return diff;
        }
        diff = Integer.compare(minor, o.minor);
        if (diff != 0) {
            return diff;
        }
        return Integer.compare(patch, o.patch);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (major ^ major);
        hash = 19 * hash + (minor ^ minor);
        hash = 19 * hash + (patch ^ patch);
        return hash;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(major).append('.').append(minor).append('.').append(patch).toString();
    }

}
