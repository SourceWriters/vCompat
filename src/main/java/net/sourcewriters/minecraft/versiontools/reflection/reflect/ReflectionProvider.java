package net.sourcewriters.minecraft.versiontools.reflection.reflect;

import static net.sourcewriters.minecraft.versiontools.reflection.reflect.FakeReflect.FAKE;

import java.util.Optional;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.reflection.ClassCache;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;
import com.syntaxphoenix.syntaxapi.reflection.ReflectCache;

import net.sourcewriters.minecraft.versiontools.version.ServerVersion;
import net.sourcewriters.minecraft.versiontools.version.Versions;

public class ReflectionProvider {

	public static final String CB_PATH_FORMAT = "org.bukkit.craftbukkit.%s.%s";
	public static final String NMS_PATH_FORMAT = "net.minecraft.server.%s.%s";

	public static final ReflectionProvider DEFAULT = new ReflectionProvider(
		provider -> Reflections.globalSetup(provider));

	protected final ReflectCache cache;

	protected final String cbPath;
	protected final String nmsPath;

	protected final ServerVersion version;

	private boolean skip = false;

	public ReflectionProvider() {
		this((Consumer<ReflectionProvider>) null);
	}

	public ReflectionProvider(Consumer<ReflectionProvider> setup) {
		this(new ReflectCache(), setup);
	}

	public ReflectionProvider(ReflectCache cache) {
		this(cache, null);
	}

	public ReflectionProvider(ReflectCache cache, Consumer<ReflectionProvider> setup) {
		this.cache = cache;
		this.version = Versions.getServer();
		this.cbPath = String.format(CB_PATH_FORMAT, Versions.getServerAsString(), "%s");
		this.nmsPath = String.format(NMS_PATH_FORMAT, Versions.getServerAsString(), "%s");
		setup.accept(this);
	}

	public ServerVersion getVersion() {
		return version;
	}

	/*
	 * Skip
	 */

	public ReflectionProvider require(boolean skip) {
		this.skip = !skip;
		return this;
	}

	public ReflectionProvider skip(boolean skip) {
		this.skip = skip;
		return this;
	}

	public boolean skip() {
		return skip;
	}

	/*
	 * Reflection
	 */

	public ReflectCache getReflection() {
		return cache;
	}

	public String getNmsPath() {
		return nmsPath;
	}

	public String getCbPath() {
		return cbPath;
	}

	public Reflect createNMSReflect(String name, String path) {
		return skip ? FAKE : cache.create(name, getNMSClass(path));
	}

	public Reflect createCBReflect(String name, String path) {
		return skip ? FAKE : cache.create(name, getCBClass(path));
	}

	public Reflect createReflect(String name, String path) {
		return skip ? FAKE : cache.create(name, getClass(path));
	}

	public Reflect createReflect(String name, Class<?> clazz) {
		return skip ? FAKE : cache.create(name, clazz);
	}

	public Optional<Reflect> getOptionalReflect(String name) {
		return cache.get(name);
	}

	public Reflect getReflect(String name) {
		return cache.get(name).orElse(null);
	}

	public Class<?> getNMSClass(String path) {
		return getClass(String.format(nmsPath, path));
	}

	public Class<?> getCBClass(String path) {
		return getClass(String.format(cbPath, path));
	}

	public Class<?> getClass(String path) {
		return ClassCache.getClass(path);
	}

}
