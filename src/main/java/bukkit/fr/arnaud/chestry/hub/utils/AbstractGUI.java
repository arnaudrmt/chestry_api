package bukkit.fr.arnaud.chestry.hub.utils;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class AbstractGUI implements Listener {

    private final Player p;
    private final String name;
    private final int size;
    private final Map<UUID, Inventory> playerInventories;
    private final Map<Integer, Consumer<InventoryClickEvent>> itemActions;

    public AbstractGUI(Player p, String name, int size) {
        this.p = p;
        this.name = name;
        this.size = size;
        this.playerInventories = new HashMap<>();
        this.itemActions = new HashMap<>();
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, ChestryBukkit.getInstance()); // Replace YourPluginClass with your main plugin class
    }

    public abstract void createItems(Player player, Inventory inventory);

    public void addItem(int slot, ItemStack itemStack) {
        addItem(slot, itemStack, null);
    }

    public void addItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> clickAction) {
        itemActions.put(slot, clickAction);
        getInventory(p.getUniqueId()).setItem(slot, itemStack);
    }

    public void open() {
        Inventory inventory = Bukkit.createInventory(null, size, name);
        playerInventories.put(getPlayer().getUniqueId(), inventory);
        createItems(getPlayer(), inventory);
        init();
        getPlayer().openInventory(inventory);
    }

    public Inventory getInventory(UUID playerUUID) {
        return playerInventories.get(playerUUID);
    }

    public Player getPlayer() {
        return p;
    }
}
