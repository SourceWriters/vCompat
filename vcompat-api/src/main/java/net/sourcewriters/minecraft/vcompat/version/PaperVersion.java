package net.sourcewriters.minecraft.vcompat.version;

import org.bukkit.Bukkit;

final class PaperVersion implements IVersion {
    
    public static final PaperVersion INSTANCE = new PaperVersion();

    public final String packageVersion = MinecraftToPackageVersion.getPackageVersion(MINECRAFT_VERSION);
    public final String craftbukkitPackage = Bukkit.getServer().getClass().getPackage().getName() + ".%s";
    public final String minecraftPackage;
    
    private PaperVersion() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException();
        }
        minecraftPackage = MINECRAFT_VERSION.minor() >= 17 ? NMS_PATH_FORMAT_REMAP
            : String.format(NMS_PATH_FORMAT_LEGACY, packageVersion, "%s");
    }

    @Override
    public String packageVersion() {
        return packageVersion;
    }

    @Override
    public String minecraftClassPath(String path) {
        return String.format(minecraftPackage, path);
    }

    @Override
    public String craftClassPath(String path) {
        return String.format(craftbukkitPackage, path);
    }

}
