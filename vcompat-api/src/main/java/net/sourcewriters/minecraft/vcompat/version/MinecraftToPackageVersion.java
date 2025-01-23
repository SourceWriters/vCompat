package net.sourcewriters.minecraft.vcompat.version;

import static net.sourcewriters.minecraft.vcompat.version.MinecraftVersion.of;

public enum MinecraftToPackageVersion {

    v1_20_R2(of(1, 20, 2), of(1, 20, 3)),
    v1_20_R3(of(1, 20, 4)),
    v1_20_R4(of(1, 20, 5), of(1, 20, 6)),
    v1_21_R1(of(1, 21, 0), of(1, 21, 2)),
    v1_21_R2(of(1, 21, 3)),
    v1_21_R3(of(1, 21, 4));

    private static final MinecraftToPackageVersion[] values = MinecraftToPackageVersion.values();

    private final MinecraftVersion min, max;

    private MinecraftToPackageVersion(MinecraftVersion version) {
        this.min = version;
        this.max = null;
    }

    private MinecraftToPackageVersion(MinecraftVersion min, MinecraftVersion max) {
        this.min = min;
        this.max = max;
    }

    public String packageVersion() {
        return name();
    }

    public boolean isVersion(MinecraftVersion version) {
        if (max == null) {
            return version.isSame(min);
        }
        return version.isBetween(min, max);
    }

    public static String getPackageVersion(MinecraftVersion version) {
        for (MinecraftToPackageVersion value : values) {
            if (value.isVersion(version)) {
                return value.packageVersion();
            }
        }
        return null;
    }

}
