package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;
import static net.sourcewriters.minecraft.versiontools.utils.java.ArrayTools.filter;
import static net.sourcewriters.minecraft.versiontools.reflection.PacketTools.sendPacket;

import java.util.Optional;

import org.bukkit.entity.Player;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class TitleTools {

	public static void sendTimes(int fadeIn, int stay, int fadeOut, Player... players) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsPacketPlayOutTitle");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEnumTitleAction");

		if (!checkPresence(optional0, optional1)) {
			return;
		}

		Object action = filter(optional1.get().getOwner().getEnumConstants(), "TIMES");

		Object packet = optional0.get().init("construct1", action, null, fadeIn, stay, fadeOut);

		sendPacket(packet, players);

	}
	
	public static void sendTitle(String title, Player... players) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsPacketPlayOutTitle");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEnumTitleAction");

		if (!checkPresence(optional0, optional1)) {
			return;
		}

		Object action = filter(optional1.get().getOwner().getEnumConstants(), "TITLE");
		Object component = MessageTools.toComponents(title);

		Object packet = optional0.get().init("construct0", action, component);

		sendPacket(packet, players);

	}
	
	public static void sendSubtitle(String subtitle, Player... players) {

		Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsPacketPlayOutTitle");
		Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("nmsEnumTitleAction");

		if (!checkPresence(optional0, optional1)) {
			return;
		}

		Object action = filter(optional1.get().getOwner().getEnumConstants(), "SUBTITLE");
		Object component = MessageTools.toComponents(subtitle);

		Object packet = optional0.get().init("construct0", action, component);

		sendPacket(packet, players);

	}

}
