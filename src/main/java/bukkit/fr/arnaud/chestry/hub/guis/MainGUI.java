package bukkit.fr.arnaud.chestry.hub.guis;

import bukkit.fr.arnaud.chestry.hub.utils.AbstractGUI;
import bukkit.fr.arnaud.chestry.hub.utils.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainGUI extends AbstractGUI {

    public MainGUI(Player p) {
        super(p, "Menu Principal", 9*3);
    }

    @Override
    public void createItems(Player player, Inventory inventory) {
        
        ItemStackUtils shopping_bag = new ItemStackUtils(Material.SKULL_ITEM, "§rMon Panier", 1);
        shopping_bag.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk1ZGE0YjQyNGM2NjJmOTEwNTEwMzgyZGQxMTdmNTY5OTFjYThlY2ZiN2UxOTI3MjhhOWI4NmM5M2FhZmFiZSJ9fX0=");

        addItem(8, shopping_bag.buildSkull());

        ItemStackUtils my_servers = new ItemStackUtils(Material.SKULL_ITEM, "§rMes Serveurs", 1);
        my_servers.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTgyMDg2ZDE1NDVhZTg4OGFhNzY2ZjhlZDljNjZlNDc1NWI0MmVkM2E3YmU0ZTBjZmEwNjhkN2Y2NzZkNmRmIn19fQ=");

        addItem(11, my_servers.buildSkull());

        ItemStackUtils order_a_server = new ItemStackUtils(Material.SKULL_ITEM, "§rCommander", 1);
        order_a_server.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMzZmM5YTQ1YmUxM2NhNTdhNzhiMjE3NjJjNmUxMjYyZGFlNDExZjEzMDQ4Yjk2M2Q5NzJhMjllMDcwOTZhYiJ9fX0=");

        addItem(13, order_a_server.buildSkull(), (e) -> new OrderGUI(getPlayer()).open());

        ItemStackUtils my_account = new ItemStackUtils(Material.SKULL_ITEM, "§rMon Compte", 1);
        my_account.setOwner(getPlayer());

        addItem(15, my_account.buildSkull());
    }
}
