package com.syntaxphoenix.versionutils.utils.tasks;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TaskUtils {
 
    public static void runForAll(PlayerTask task) {
        Collection<? extends Player> col = Bukkit.getOnlinePlayers();
        if (!col.isEmpty()) {
            for (Player p : col) {
                task.run(p);
            }
        }
    }
 
    public static void runForAll(ObjectTask task, int id, List<Object> objects) {
        if (objects == null) {
            return;
        }
        if (!objects.isEmpty()) {
            for (Object p : objects) {
                task.run(p);
            }
        }
    }
 
    public static void runForAll(ObjectTask task, List<Integer> numbers, List<Object> objects) {
        if (numbers == null) {
            return;
        }
        if (!numbers.isEmpty()) {
            for (Integer i : numbers) {
                runForAll(task, i, objects);
            }
        }
    }

}
