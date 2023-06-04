package bukkit.fr.arnaud.chestry.hub.events;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.pets.PetsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PetsEvent implements Listener {

    PetsManager petsManager = ChestryBukkit.getInstance().getPetsManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        petsManager.getPlayerEntityMap().forEach((key, value) -> {
            petsManager.spawn(player, key);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        petsManager.despawn(player);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        petsManager.teleport(player);
    }
}
