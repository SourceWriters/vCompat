package net.sourcewriters.minecraft.vcompat.provider;

import java.io.File;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.bukkit.Bukkit;

import net.sourcewriters.minecraft.vcompat.data.nbt.NbtAdapterRegistry;
import net.sourcewriters.minecraft.vcompat.data.nbt.NbtContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.WrappedContainer;
import net.sourcewriters.minecraft.vcompat.provider.data.persistence.DataDistributor;
import net.sourcewriters.minecraft.vcompat.provider.data.wrap.SimpleSyntaxContainer;

public class DataProvider extends VersionHandler<VersionControl> {

    public static final Function<UUID, String> DEFAULT_NAMING = UUID::toString;
    public static final Supplier<UUID> DEFAULT_RANDOM = UUID::randomUUID;

    protected final NbtAdapterRegistry registry = new NbtAdapterRegistry();
    private DataDistributor<UUID> defaultDistributor;

    protected DataProvider(VersionControl versionControl) {
        super(versionControl);
    }

    public final NbtAdapterRegistry getRegistry() {
        return registry;
    }

    public WrappedContainer createContainer() {
        return new SimpleSyntaxContainer<>(new NbtContainer(registry));
    }

    public WrappedContainer createPersistentContainer() {
        return new SimpleSyntaxContainer<>(getDefaultDistributor().create());
    }

    public DataDistributor<UUID> getDefaultDistributor() {
        if (defaultDistributor == null) {
            return defaultDistributor = createDistributor(new File(Bukkit.getWorlds().get(0).getWorldFolder(), "pluginData"));
        }
        return defaultDistributor;
    }

    public DataDistributor<UUID> createDistributor(File location) {
        return new DataDistributor<>(this, location, DEFAULT_NAMING, DEFAULT_RANDOM);
    }

    public <K> DataDistributor<K> createDistributor(File location, Function<K, String> namingFunction, Supplier<K> randomFunction) {
        return new DataDistributor<>(this, location, namingFunction, randomFunction);
    }

}