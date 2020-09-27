package net.sourcewriters.minecraft.versiontools.reflection;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.minecraft.versiontools.version.Versions;

public abstract class VersionControl {

	public static final String CLASSPATH = "net.sourcewriters.minecraft.versiontools.reflection.provider.$version.VersionControl$version";
	public static Container<VersionControl> CURRENT = Container.of();

	public static VersionControl get() {
		if (CURRENT.isPresent())
			return CURRENT.get();
		Reflect reflect = new Reflect(CLASSPATH.replace("$version", Versions.getServerAsString()));
		if (reflect.getOwner() == null || !reflect.getOwner().isAssignableFrom(VersionControl.class))
			return null;
		return CURRENT.replace((VersionControl) reflect.searchMethod("init", "init").run("init")).lock().get();
	}
	
	public abstract ToolProvider<?> getToolProvider();
	public abstract EntityProvider<?> getEntityProvider();
	public abstract PlayerProvider<?> getPlayerProvider();
	public abstract TextureProvider<?> getTextureProvider();
	
	public abstract PacketHandler<?> getPacketHandler();

	public abstract BukkitConversion<?> getBukkitConversion();

}
