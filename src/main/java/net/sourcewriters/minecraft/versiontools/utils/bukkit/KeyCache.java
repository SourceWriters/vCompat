package net.sourcewriters.minecraft.versiontools.utils.bukkit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;

public final class KeyCache {

	private static final Map<String, KeyCache> KEYS = Collections.synchronizedMap(new HashMap<>());

	public static final KeyCache versionUtils() {
		return cache("versionUtils");
	}

	public static final KeyCache cache(String namespace) {
		return KEYS.computeIfAbsent(namespace, name -> new KeyCache(name));
	}

	public static final NamespacedKey key(String key) {
		if (!key.contains(":")) {
			return versionUtils().get(key);
		}
		String[] keys = key.split(":", 2);
		return cache(keys[0]).get(keys[1]);
	}

	private final String namespace;
	private final Map<String, NamespacedKey> keys = Collections.synchronizedMap(new HashMap<>());

	private KeyCache(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}

	@SuppressWarnings("deprecation")
	public NamespacedKey get(String key) {
		if (keys.containsKey(key)) {
			return keys.get(key);
		}
		NamespacedKey output = new NamespacedKey(namespace, key);
		keys.put(key, output);
		return output;
	}

	public NamespacedKey remove(String key) {
		return keys.remove(key);
	}
}
