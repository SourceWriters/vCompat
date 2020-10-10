package net.sourcewriters.minecraft.versiontools.reflection.data.wrap;

import static net.sourcewriters.minecraft.versiontools.utils.constants.DefaultConstants.NAMESPACE_STRING;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.data.key.DataKey;
import com.syntaxphoenix.syntaxapi.data.key.Namespace;
import com.syntaxphoenix.syntaxapi.data.key.NamespacedKey;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedKey;

public final class SyntaxKey extends WrappedKey<NamespacedKey> {

	private final NamespacedKey key;

	public SyntaxKey(Plugin plugin, String key) {
		this.key = new Namespace(plugin.getName().toLowerCase()).createKey(key);
	}

	public SyntaxKey(String name, String key) {
		this.key = new Namespace(name).createKey(key);
	}

	public SyntaxKey(NamespacedKey key) {
		this.key = key;
	}

	public SyntaxKey(DataKey key) {
		this.key = NamespacedKey.fromStringOrDefault(key.toString(), NAMESPACE_STRING);
	}

	@Override
	public NamespacedKey getHandle() {
		return key;
	}
	
	@Override
	public Namespace getNamespace() {
		return key.getNamespace();
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public String getName() {
		return key.getNamespace().getName();
	}

	@Override
	public String getKey() {
		return key.getKey();
	}

	@Override
	public String toString() {
		return key.toString();
	}

}
