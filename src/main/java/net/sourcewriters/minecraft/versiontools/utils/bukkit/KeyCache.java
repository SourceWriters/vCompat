package net.sourcewriters.minecraft.versiontools.utils.bukkit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;

public final class KeyCache {

	public static final KeyCache KEYS = new KeyCache("verionUtils");

	private final String namespace;
	private final Map<String, NamespacedKey> keys = Collections.synchronizedMap(new HashMap<>());

	public KeyCache(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}

	@SuppressWarnings("deprecation")
	public NamespacedKey get(String key) {
		if (keys.containsKey(key))
			return keys.get(key);
		NamespacedKey output = new NamespacedKey(namespace, key);
		keys.put(key, output);
		return output;
	}

	public NamespacedKey remove(String key) {
		return keys.remove(key);
	}

}
