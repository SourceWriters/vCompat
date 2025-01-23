package net.sourcewriters.minecraft.vcompat.version;

import org.bukkit.Bukkit;

final class SpigotVersion implements IVersion {

    public static final SpigotVersion INSTANCE = new SpigotVersion();

    final static IVersion create() {
        if (exists("com.destroystokyo.paper.PaperConfig") || exists("io.papermc.paper.configuration.Configuration")) {
            if (MINECRAFT_VERSION.compareTo(MinecraftVersion.of(1, 20, 2)) >= 1) {
                return PaperVersion.INSTANCE;
            }
        }
        return INSTANCE;
    }

    private static boolean exists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public final String packageVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    public final String craftbukkitPackage = Bukkit.getServer().getClass().getPackage().getName() + ".%s";
    public final String minecraftPackage;

    private SpigotVersion() {
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
