package bukkit.fr.arnaud.chestry.hub.guis;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.utils.AbstractGUI;
import bukkit.fr.arnaud.chestry.hub.utils.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OrderGUI extends AbstractGUI {

    public OrderGUI(Player p) {
        super(p, "Choisis ton serveur", 3*9);
    }

    @Override
    public void createItems(Player player, Inventory inventory) {

        ItemStackUtils shopping_bag = new ItemStackUtils(Material.SKULL_ITEM, "§rMon Panier", 1);
        shopping_bag.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk1ZGE0YjQyNGM2NjJmOTEwNTEwMzgyZGQxMTdmNTY5OTFjYThlY2ZiN2UxOTI3MjhhOWI4NmM5M2FhZmFiZSJ9fX0=");

        addItem(8, shopping_bag.build());

        ItemStackUtils vanilla = new ItemStackUtils(Material.SKULL_ITEM, "§2Vanilla", 1);
        vanilla.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE5ZTM2YTg3YmFmMGFjNzYzMTQzNTJmNTlhN2Y2M2JkYjNmNGM4NmJkOWJiYTY5Mjc3NzJjMDFkNGQxIn19fQ=");

        addItem(11, vanilla.build(), (e) ->
                new VersionGUI(getPlayer(), ChestryBukkit.getInstance().getVersionsManager().getVersionTypesFromName("Vanilla", "Stable")).open()
        );

        ItemStackUtils spigot = new ItemStackUtils(Material.SKULL_ITEM, "§6Spigot", 1);
        spigot.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjcyYWZiMzFlOGY0OWZmOTBkNDkxMjdkMDU2MmMxOWZmMDdkZGMwYmJhMWY3ZDgyY2QxNTA3MThkZjIzM2YyOSJ9fX0=");

        addItem(13, spigot.build(), (e) ->
                new VersionGUI(getPlayer(), ChestryBukkit.getInstance().getVersionsManager().getVersionTypesFromName("Spigot", "Stable")).open()
        );

        ItemStackUtils modpack = new ItemStackUtils(Material.SKULL_ITEM, "§5Mod Pack", 1);
        modpack.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmEyNmJmYjA4MDhmMTIzZmViYjQ0MWU2OTEyYTA4MmMzMjZlMmM4MTU2YzE1YzljMjIzN2JhYTJlMThmYzI3MSJ9fX0=");

        addItem(15, modpack.build(), (e) ->
                new VersionGUI(getPlayer(), ChestryBukkit.getInstance().getVersionsManager().getVersionTypesFromName("ModPack", "Stable")).open()
        );
    }
}
