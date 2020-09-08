package com.syntaxphoenix.versionutils.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.syntaxphoenix.versionutils.VersionUtils;
import com.syntaxphoenix.versionutils.reflection.helper.ReflectionHelper;
import com.syntaxphoenix.versionutils.reflection.helper.Reflector;

public class GeneralReflections {
 
    public static void respawn(JavaPlugin plugin, Player player) {
    	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	        @Override
	        public void run() {
		        try {
		            Class<?> packetPlayInClientCommand = Reflector.getNMSClass("PacketPlayInClientCommand");
		
		            Class<?> ecc = null;
		            for (Class<?> cc : packetPlayInClientCommand.getClasses()) {
		                if (cc.getSimpleName().startsWith("EnumClientCommand")) {
		                    ecc = cc;
		                    break;
		                }
		            }
		            if (ecc == null) {
		                return;
		            }
		
		            Constructor<?> c = packetPlayInClientCommand.getConstructor(ecc);
		            Object packet = c.newInstance(ReflectionHelper.getEnumByName(ecc, "PERFORM_RESPAWN"));
		            ReflectionHelper.playerConnectionClass.getDeclaredMethod("a", packetPlayInClientCommand).invoke(ReflectionHelper.getConnection(player), packet);
		        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
		                | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
		            e.printStackTrace();
		        }
	        }
        }, 1);
    }
 
    public static void changeMOTD(String motd) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        Class<?> dedicatedServerClass = Reflector.getNMSClass("DedicatedServer");
        dedicatedServerClass.getMethod("setMotd", String.class).invoke(ReflectionHelper.getMinecraftServer(), motd);
    }

    public static void sendActionBar(Player player, String message) throws InstantiationException, IllegalAccessException,
    		IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
    	Object chatComponent = ReflectionHelper.getAsIChatBaseComponent(message);
        Class<?> packetPlayOutChat = Reflector.getNMSClass("PacketPlayOutChat");
        Class<?> baseComponent = Reflector.getNMSClass("IChatBaseComponent");
        Object packet;
        if (!VersionUtils.isNewerVersion(VersionUtils.getServerVersion(), 1, 12)) {
            packet = packetPlayOutChat.getConstructor(baseComponent, byte.class).newInstance(chatComponent, (byte) 2);
        } else {
        	Class<?> chatMessage = Reflector.getNMSClass("ChatMessageType");
            packet = packetPlayOutChat.getConstructor(baseComponent, chatMessage).newInstance(chatComponent, ReflectionHelper.getEnumByName(chatMessage, "GAME_INFO"));
        }
        ReflectionHelper.sendPacket(player, packet);
    }
}
