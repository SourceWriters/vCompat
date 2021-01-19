package net.sourcewriters.minecraft.versiontools.reflection;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.versiontools.version.Versions;

public abstract class VersionControl {

	public static final String CLASSPATH = "net.sourcewriters.minecraft.versiontools.reflection.provider.v$version.VersionControl$version";
	public static Container<VersionControl> CURRENT = Container.of();

	public static VersionControl get() {
		if (CURRENT.isPresent()) {
			return CURRENT.get();
		}
		Reflect reflect = new Reflect(CLASSPATH.replace("$version", Versions.getServerAsString().substring(1)));
		if (reflect.getOwner() == null || !VersionControl.class.isAssignableFrom(reflect.getOwner())) {
			return null;
		}
		return CURRENT.replace((VersionControl) reflect.searchMethod("init", "init").run("init")).lock().get();
	}

	protected final DataProvider dataProvider = new DataProvider(this);

	public DataProvider getDataProvider() {
		return dataProvider;
	}

	public abstract ToolProvider<?> getToolProvider();

	public abstract EntityProvider<?> getEntityProvider();

	public abstract PlayerProvider<?> getPlayerProvider();

	public abstract TextureProvider<?> getTextureProvider();

	public abstract PacketHandler<?> getPacketHandler();

	public abstract BukkitConversion<?> getBukkitConversion();

}
