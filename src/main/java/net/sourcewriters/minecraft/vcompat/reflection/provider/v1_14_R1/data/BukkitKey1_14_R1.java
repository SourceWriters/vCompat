package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_14_R1.data;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedKey;

public final class BukkitKey1_14_R1 extends WrappedKey<NamespacedKey> {

    private final NamespacedKey key;

    public BukkitKey1_14_R1(Plugin plugin, String key) {
        this.key = new NamespacedKey(plugin, key);
    }

    @SuppressWarnings("deprecation")
    public BukkitKey1_14_R1(String name, String key) {
        this.key = new NamespacedKey(name, key);
    }

    public BukkitKey1_14_R1(NamespacedKey key) {
        this.key = key;
    }

    @Override
    public NamespacedKey getHandle() {
        return key;
    }

    @Override
    public String getName() {
        return key.getNamespace();
    }

    @Override
    public String getKey() {
        return key.getKey();
    }

    @Override
    public String toString() {
        return key.toString();
    }

    public static NamespacedKey asBukkit(WrappedKey<?> key) {
        if (key.getHandle() instanceof NamespacedKey) {
            return (NamespacedKey) key.getHandle();
        }
        return new BukkitKey1_14_R1(key.getName(), key.getKey()).getHandle();
    }

}