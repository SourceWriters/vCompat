package net.sourcewriters.minecraft.vcompat.version;

import org.bukkit.Bukkit;

public interface IVersion {

    String NMS_PATH_FORMAT_LEGACY = "net.minecraft.server.%s.%s";
    String NMS_PATH_FORMAT_REMAP = "net.minecraft.%s";
    
    MinecraftVersion MINECRAFT_VERSION = MinecraftVersion.ofBukkit(Bukkit.getBukkitVersion());
    
    IVersion VERSION = SpigotVersion.create();
    
    String packageVersion();
    
    default String coreVersion() {
        return packageVersion().substring(1);
    }
    
    String minecraftClassPath(String path);
    
    String craftClassPath(String path);

}
