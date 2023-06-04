package bukkit.fr.arnaud.chestry.hub.events;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.guis.VersionGUI;
import bukkit.fr.arnaud.chestry.hub.versions.VersionType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.PlayerInventory;

public class UtilsEvent implements Listener {

    @EventHandler
    public void onFoodLeveChange(FoodLevelChangeEvent e) {

        if(!(e.getEntity() instanceof Player)) return;

        ((Player) e.getEntity()).setFoodLevel(20);
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if(!(e.getInventory() instanceof PlayerInventory)) e.setCancelled(true);
    }

    @EventHandler
    public void AsyncPlayerChat(AsyncPlayerChatEvent e) {

        if(!e.getMessage().contains("1.")) return;

        VersionType versionType = ChestryBukkit.getInstance().getVersionsManager().getVersionTypesFromName("Vanilla", "Stable");
        new VersionGUI(e.getPlayer(), versionType, e.getMessage()).open();
    }
}
