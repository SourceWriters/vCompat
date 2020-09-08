package net.sourcewriters.minecraft.versiontools.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.sourcewriters.minecraft.versiontools.VersionUtils;
import net.sourcewriters.minecraft.versiontools.reflection.helper.ReflectionHelper;
import net.sourcewriters.minecraft.versiontools.reflection.helper.Reflector;
import net.sourcewriters.minecraft.versiontools.utils.tasks.ObjectTask;
import net.sourcewriters.minecraft.versiontools.utils.tasks.PlayerTask;
import net.sourcewriters.minecraft.versiontools.utils.tasks.TaskUtils;

public class Hologram {
 
    public static HashMap<Integer, ArrayList<Object>> globalEntities = new HashMap<>();
 
    public static void sendHoloPacket(Player play, int id, Constructor<?> packet, boolean entity)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException, InstantiationException {
        ArrayList<Object> armorstands = globalEntities.get(id);
        if (armorstands != null) {
            if (entity) {
                TaskUtils.runForAll(new ObjectTask() {
                    @Override
                    protected void run(Object o) {
                        try {
                            ReflectionHelper.sendPacket(play, packet.newInstance(o));
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                                | NoSuchMethodException | SecurityException | InstantiationException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }, id, globalEntities.get(id));
            } else {
                Class<?> entityClass = Reflector.getNMSClass("Entity");
                Method getIdMethod = entityClass.getDeclaredMethod("getId");
                TaskUtils.runForAll(new ObjectTask() {
                    @Override
                    protected void run(Object o) {
                        try {
                            int uid = (int) getIdMethod.invoke(o);
                            ReflectionHelper.sendPacket(play, packet.newInstance(uid));
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                                | NoSuchMethodException | SecurityException | InstantiationException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }, id, globalEntities.get(id));
            }
        }
    }
 
    public static void HoloshowPlayer(Player p, int id) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
        Class<?> ppoed = Reflector.getNMSClass("PacketPlayOutSpawnEntityLiving");
        sendHoloPacket(p, id, ppoed.getConstructor(Reflector.getNMSClass("EntityLiving")), true);
    }
 
    public static void HolohidePlayer(Player p, int id) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
        Class<?> ppoed = Reflector.getNMSClass("PacketPlayOutEntityDestroy");
        sendHoloPacket(p, id, ppoed.getConstructor(int.class), false);
    }
 
    public static void HoloshowAll(int id) {
    	TaskUtils.runForAll(new PlayerTask() {
            @Override
            protected void run(Player p) {
                try {
                    HoloshowPlayer(p, id);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    public static void HolohideAll(int id) {
    	TaskUtils.runForAll(new PlayerTask() {
            @Override
            protected void run(Player p) {
                try {
                    HolohidePlayer(p, id);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    public static Object configureEntity(Object entity, String name, boolean legacy) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Class<?> entityClass = Reflector.getNMSClass("Entity");
        Method setCustomName;
        if(legacy) {
            setCustomName = entityClass.getDeclaredMethod("setCustomName", String.class);
            setCustomName.invoke(entity, name);
        } else {
            Class<?> iChatBaseComponentClass = Reflector.getNMSClass("IChatBaseComponent");
            setCustomName = entityClass.getDeclaredMethod("setCustomName", iChatBaseComponentClass);
            setCustomName.invoke(entity, ReflectionHelper.createChatComponent(name));
        }
        Method setCustomNameVisible = entityClass.getDeclaredMethod("setCustomNameVisible", boolean.class);        
        Method setInvisible = entityClass.getDeclaredMethod("setInvisible", boolean.class);

        setCustomNameVisible.invoke(entity, true);
        setInvisible.invoke(entity, true);
        
        Method setNoGravity; 
        
        Class<?> armorStandClass = Reflector.getNMSClass("EntityArmorStand");
        
		Object armorStand = armorStandClass.cast(entity);
        
		String[] splittedVersion = VersionUtils.getMinecraftVersion().replace("v", "").split("_");
		if(splittedVersion[0].equalsIgnoreCase("1")) {
			int subVersion = Integer.valueOf(splittedVersion[1]);
			if(subVersion > 9) {
				setNoGravity = entityClass.getDeclaredMethod("setNoGravity", boolean.class);
		        setNoGravity.invoke(armorStand, true);
		        return armorStand;
			} else {
				setNoGravity = armorStandClass.getDeclaredMethod("setGravity", boolean.class);
		        setNoGravity.invoke(armorStand, false);
			}
		}
		
        return armorStand;
    }
 
    public static void create(String[] txt, Location loc, double distance, int id, boolean legacy) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<?> entityArmorStandClass = Reflector.getNMSClass("EntityArmorStand");
        Class<?> worldClass = Reflector.getNMSClass("World");
        Constructor<?> c = entityArmorStandClass.getConstructor(worldClass, double.class, double.class, double.class);
       
        ArrayList<Object> entities;
        if (globalEntities.containsKey(id)) {
            entities = globalEntities.get(id);
        } else {
            entities = new ArrayList<>();
        }
        Object server = ReflectionHelper.getWorldServer(loc.getWorld());
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
       
        for (String in : txt) {
            Object entity = c.newInstance(server, x, y, z);
            entities.add(configureEntity(entity, in, legacy));
            y -= distance;
        }
        globalEntities.put(id, entities);
    }
}
