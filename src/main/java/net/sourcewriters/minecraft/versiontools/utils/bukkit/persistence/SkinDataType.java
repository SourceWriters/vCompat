package net.sourcewriters.minecraft.versiontools.utils.bukkit.persistence;

import static net.sourcewriters.minecraft.versiontools.utils.bukkit.KeyCache.KEYS;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.sourcewriters.minecraft.versiontools.skin.Skin;

public final class SkinDataType implements PersistentDataType<PersistentDataContainer, Skin> {
	
	public static final SkinDataType INSTANCE = new SkinDataType();
	
	private SkinDataType() {
		
	}

	@Override
	public Class<PersistentDataContainer> getPrimitiveType() {
		return PersistentDataContainer.class;
	}

	@Override
	public Class<Skin> getComplexType() {
		return Skin.class;
	}

	@Override
	public PersistentDataContainer toPrimitive(Skin skin, PersistentDataAdapterContext context) {
		PersistentDataContainer dataContainer = context.newPersistentDataContainer();
		dataContainer.set(KEYS.get("name"), PersistentDataType.STRING, skin.getName());
		dataContainer.set(KEYS.get("value"), PersistentDataType.STRING, skin.getValue());
		dataContainer.set(KEYS.get("signature"), PersistentDataType.STRING, skin.getSignature());
		return dataContainer;
	}

	@Override
	public Skin fromPrimitive(PersistentDataContainer container, PersistentDataAdapterContext context) {
		String name = container.get(KEYS.get("name"), PersistentDataType.STRING);
		String value = container.get(KEYS.get("value"), PersistentDataType.STRING);
		String signature = container.get(KEYS.get("signature"), PersistentDataType.STRING);
		return new Skin(name, value, signature, false);
	}

}
