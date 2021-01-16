package net.sourcewriters.minecraft.versiontools.reflection.data.wrap;

import static net.sourcewriters.minecraft.versiontools.utils.constants.DefaultConstants.NAMESPACE_STRING;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;
import com.syntaxphoenix.syntaxapi.utils.key.Namespace;
import com.syntaxphoenix.syntaxapi.utils.key.NamespacedKey;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedKey;

public final class SyntaxKey extends WrappedKey<NamespacedKey> {

	private final NamespacedKey key;

	public SyntaxKey(Plugin plugin, String key) {
		this.key = Namespace.of(plugin.getName().toLowerCase()).create(key);
	}

	public SyntaxKey(String name, String key) {
		this.key = Namespace.of(name).create(key);
	}

	public SyntaxKey(NamespacedKey key) {
		this.key = key;
	}

	public SyntaxKey(IKey key) {
		this.key = NamespacedKey.fromStringOrCompute(key.toString(), key0 -> Namespace.of(NAMESPACE_STRING).create(key0));
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
