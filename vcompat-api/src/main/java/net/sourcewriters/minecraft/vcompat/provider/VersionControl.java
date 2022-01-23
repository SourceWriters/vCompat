package net.sourcewriters.minecraft.vcompat.provider;

import net.sourcewriters.minecraft.vcompat.VersionCompatProvider;
import net.sourcewriters.minecraft.vcompat.provider.lookup.ClassLookupProvider;

public abstract class VersionControl {

    protected final DataProvider dataProvider = new DataProvider(this);
    protected final ClassLookupProvider lookupProvider = VersionCompatProvider.get().getLookupProvider();

    public DataProvider getDataProvider() {
        return dataProvider;
    }
    
    public final ClassLookupProvider getLookupProvider() {
        return lookupProvider;
    }

    public abstract ToolProvider<?> getToolProvider();

    public abstract EntityProvider<?> getEntityProvider();

    public abstract PlayerProvider<?> getPlayerProvider();

    public abstract TextureProvider<?> getTextureProvider();

    public abstract BukkitConversion<?> getBukkitConversion();
    
    public void shutdown() {}

}