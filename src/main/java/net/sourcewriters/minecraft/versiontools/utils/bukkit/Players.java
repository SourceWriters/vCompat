package net.sourcewriters.minecraft.versiontools.utils.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.IntFunction;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class Players {

     public static final IntFunction<OfflinePlayer[]> OFFLINE_FUNCTION = size -> new OfflinePlayer[size];
     public static final IntFunction<Player[]> ONLINE_FUNCTION = size -> new Player[size];

     /*
     * 
     */

     public static Player[] getOnline() {
          return Bukkit.getOnlinePlayers().stream().toArray(ONLINE_FUNCTION);
     }

     public static Player[] getOnlineWith(UUID... uniqueIds) {
          return filter(getOnline(), uniqueIds, false).stream().toArray(ONLINE_FUNCTION);
     }

     public static Player[] getOnlineWithout(UUID... uniqueIds) {
          return filter(getOnline(), uniqueIds, true).stream().toArray(ONLINE_FUNCTION);
     }

     /*
     * 
     */

     public static OfflinePlayer[] getOffline() {
          return Bukkit.getOfflinePlayers();
     }

     public static OfflinePlayer[] getOfflineWith(UUID... uniqueIds) {
          return filter(getOffline(), uniqueIds, false).stream().toArray(ONLINE_FUNCTION);
     }

     public static OfflinePlayer[] getOfflineWithout(UUID... uniqueIds) {
          return filter(getOffline(), uniqueIds, true).stream().toArray(OFFLINE_FUNCTION);
     }

     /*
     * 
     */

     public static <E extends OfflinePlayer> List<E> filter(E[] players, UUID[] uniqueIds, boolean blacklist) {
          ArrayList<E> output = new ArrayList<>();
          List<UUID> filter = Arrays.asList(uniqueIds);
          for (E player : players) {
               if (blacklist ? !filter.contains(player.getUniqueId()) : filter.contains(player.getUniqueId())) {
                    output.add(player);
               }
          }
          return output;
     }

}
