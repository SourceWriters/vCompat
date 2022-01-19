package net.sourcewriters.minecraft.vcompat.provider;

public abstract class VersionControl {

    public static final String CLASSPATH = "net.sourcewriters.minecraft.vcompat.reflection.provider.v$version.VersionControl$version";

    protected final DataProvider dataProvider = new DataProvider(this);

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public abstract ToolProvider<?> getToolProvider();

    public abstract EntityProvider<?> getEntityProvider();

    public abstract PlayerProvider<?> getPlayerProvider();

    public abstract TextureProvider<?> getTextureProvider();

    public abstract BukkitConversion<?> getBukkitConversion();
    
    public void shutdown() {}

}