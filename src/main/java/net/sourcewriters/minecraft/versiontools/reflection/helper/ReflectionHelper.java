package net.sourcewriters.minecraft.versiontools.reflection.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Deprecated
public class ReflectionHelper {
 
    public static Class<?> craftPlayerClass = Reflector.getCBClass("entity.CraftPlayer");
    public static Class<?> entityPlayerClass = Reflector.getNMSClass("EntityPlayer");
    public static Class<?> playerConnectionClass = Reflector.getNMSClass("PlayerConnection");
    public static Class<?> packetClass = Reflector.getNMSClass("Packet");
 
    public static Object getCraftPlayer(Player player) {
        return craftPlayerClass.cast(player);
    }
 
    public static Object getEntityPlayer(Player player) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        return craftPlayerClass.getDeclaredMethod("getHandle").invoke(getCraftPlayer(player));
    }
 
    public static Object getConnection(Player player) throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException, SecurityException, InvocationTargetException, NoSuchMethodException {
        return entityPlayerClass.getDeclaredField("playerConnection").get(getEntityPlayer(player));
    }
 
    public static void sendPacket(Player player, Object packet) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        if (!packetClass.isInstance(packet)) {
            return;
        }
        playerConnectionClass.getDeclaredMethod("sendPacket", packetClass).invoke(getConnection(player), packet);
    }
 
    public static Object getEnumByName(Class<?> enumClass, String enumName)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        Object array = enumClass.getMethod("values").invoke(enumClass);
        Object[] arr = (Object[]) array;
        for (Object o : arr) {
            if (o.toString().equalsIgnoreCase(enumName)) {
                return o;
            }
        }
        return array;
    }
 
    public static Object getMinecraftServer()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Class<?> craftServerClass = Reflector.getCBClass("CraftServer");
 
        Server sv = Bukkit.getServer();
        Field f = craftServerClass.getDeclaredField("console");
        f.setAccessible(true);
        return f.get(sv);
    }
 
    public static Object getWorldServer(World w) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> craftWorldClass = Reflector.getCBClass("CraftWorld");
        Method getHandleMethod = craftWorldClass.getDeclaredMethod("getHandle");
        return getHandleMethod.invoke(w);
    }
 
    public static Object createChatComponent(String text) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<?> chatMessageClass = Reflector.getNMSClass("ChatMessage");
        Constructor<?> c = chatMessageClass.getConstructor(String.class, Object[].class);
        return c.newInstance(text, new Object[0]);
    }
    
   	public static Object getAsIChatBaseComponent(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
   			NoSuchMethodException, SecurityException {
   		Class<?> iChatBaseComponent = Reflector.getNMSClass("IChatBaseComponent");
   		Method method = iChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class);
   		return method.invoke(iChatBaseComponent, "{\"text\":\"" + name + "\"}");
   	}
}
