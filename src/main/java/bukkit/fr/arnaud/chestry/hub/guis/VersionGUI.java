package bukkit.fr.arnaud.chestry.hub.guis;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.utils.AbstractGUI;
import bukkit.fr.arnaud.chestry.hub.utils.AbstractSign;
import bukkit.fr.arnaud.chestry.hub.utils.ItemStackUtils;
import bukkit.fr.arnaud.chestry.hub.versions.MainVersion;
import bukkit.fr.arnaud.chestry.hub.versions.SubVersion;
import bukkit.fr.arnaud.chestry.hub.versions.VersionType;
import bukkit.fr.arnaud.chestry.hub.versions.VersionsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class VersionGUI extends AbstractGUI {

    private final VersionType versionType;
    private final VersionsManager versionsManager = ChestryBukkit.getInstance().getVersionsManager();
    private String searchInput = "";

    public VersionGUI(Player p, VersionType versionType) {
        super(p, "Choisis ta Version", 9*6);
        this.versionType = versionType;
    }

    public VersionGUI(Player p, VersionType versionType, String search) {
        super(p, "Choisis ta Version", 9*6);
        this.versionType = versionType;
        this.searchInput = search;
    }

    @Override
    public void createItems(Player p, Inventory inventory) {

        if(versionType.getMainVersions().isEmpty()) {
            p.sendMessage("§cCe type de version n'est pas encore disponible!");
            p.closeInventory();
            return;
        }

        List<MainVersion> mainVersionList = versionType.getMainVersions();
        List<VersionType> versionTypesList = versionsManager.getVersionTypeListFromMainVersion(mainVersionList.get(0));

        List<Integer> versionTypesSlot = Arrays.asList(2, 3, 5, 6);
        List<Integer> mainVersionSlot = Arrays.asList(9, 18, 27, 36);

        String search = parseVersion(searchInput);

        buildVersionType(mainVersionSlot, versionTypesSlot, versionTypesList);

        if(Objects.equals(search, "")) {

            mainVersionList.sort(Comparator.comparing((MainVersion v) ->
                    Integer.parseInt(v.getName().substring(v.getName().indexOf(".") + 1))).reversed());

            buildVersionGUI(mainVersionSlot, mainVersionList, 0, false);

        } else {

            searchGUI(search, mainVersionSlot, mainVersionList, versionTypesSlot, versionTypesList);
        }
    }

    public void buildVersionGUI(List<Integer> mainVersionSlot, List<MainVersion> mainVersionList, int startingAt, boolean clean) {

        mainVersionList.sort(Comparator.comparing((MainVersion v) ->
                Integer.parseInt(v.getName().substring(v.getName().indexOf(".") + 1))).reversed());

        ItemStack searchItem = new ItemStackUtils(Material.SIGN, "§rRecharcher", 1).build();
        addItem(49, searchItem, (e) -> {
            System.out.println(getPlayer());
            System.out.println(versionType.getName());
            new AbstractSign(getPlayer(), versionType);
        });

        if (clean) IntStream.range(9, 53).forEach(slot -> getInventory(getPlayer().getUniqueId()).setItem(slot, new ItemStack(Material.AIR)));

        IntStream.range(mainVersionSlot.get(0), mainVersionSlot.get(mainVersionSlot.size() - 1) + 1).forEach(slot -> {

            if(mainVersionSlot.contains(slot) && mainVersionList.size() > mainVersionSlot.indexOf(slot)) {

                MainVersion currentMainVersion = mainVersionList.get(mainVersionSlot.indexOf(slot) + startingAt);
                List<SubVersion> currentSubVersionsList = currentMainVersion.getSubVersions();

                addItem(slot, buildHead(currentMainVersion.getName(), currentMainVersion.getTexture()));

                buildSubVersionsLine(slot, currentSubVersionsList, clean);

                ItemStack left_arrow_item = buildHead("§rGauche", currentSubVersionsList.size() > 6 ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiNWU5ZDVhZmFjMTgzZjFmNTcwYzFiNmVmNTE1NmMxMjFjMWVmYmQ4NTUyN2Q4ZDc5ZDBhZGVlYjY3MjQ4NSJ9fX0=" : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI3MmFkODM2OWViNmNkODk5MGNlYzFmNTRkMTc3ODQ0MmExMDhiMDE4NjYyMmM1OTE4ZWI4NTE1OWUyZmI5ZSJ9fX0=");
                ItemStack right_arrow_item = buildHead("§rDroite", currentSubVersionsList.size() > 6 ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjcxNmNhMzk1MTFhOTY3MjBjMzM3OWU3NzE5NjNiZWZlMjI0YjYwZWNjZWQ5ZTY5MzQ5NTk3NWVkYTgxZGU3MiJ9fX0=" : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI3MmFkODM2OWViNmNkODk5MGNlYzFmNTRkMTc3ODQ0MmExMDhiMDE4NjYyMmM1OTE4ZWI4NTE1OWUyZmI5ZSJ9fX0=");

                addItem(slot + 1, left_arrow_item, currentSubVersionsList.size() > 6 ? (e) -> moveSideways(slot, currentSubVersionsList, false) : null);
                addItem(slot + 8, right_arrow_item, currentSubVersionsList.size() > 6 ? (e) -> moveSideways(slot, currentSubVersionsList, true) : null);

                ItemStack arrow_up = buildHead("§rRemonter", mainVersionList.size() > 4 ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM1MTRmMjNkNmIwOWUxODQwY2RlYzdjMGQ2OTEyZGNkMzBmODIxMTA4NThjMTMzYTdmNzc3OGM3Mjg1NjZkZCJ9fX0=": "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI3MmFkODM2OWViNmNkODk5MGNlYzFmNTRkMTc3ODQ0MmExMDhiMDE4NjYyMmM1OTE4ZWI4NTE1OWUyZmI5ZSJ9fX0=");
                ItemStack arrow_down = buildHead("§rRedescendre", mainVersionList.size() > 4 ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNhOGZiMjA1NTNlMWU4ZGI0N2YzODgzZjdmNDkxYzFlZTZiZmQ0ZDJjYTk3NzczYThkYjk2MGViNTQzMTcwZSJ9fX0=": "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI3MmFkODM2OWViNmNkODk5MGNlYzFmNTRkMTc3ODQ0MmExMDhiMDE4NjYyMmM1OTE4ZWI4NTE1OWUyZmI5ZSJ9fX0=");


                addItem(48, arrow_up, mainVersionList.size() > 4 ? (e) -> moveUpright(mainVersionSlot, mainVersionList, startingAt, true) : null);
                addItem(50, arrow_down, mainVersionList.size() > 4 ? (e) -> moveUpright(mainVersionSlot, mainVersionList, startingAt, false) : null);

            } else if(mainVersionSlot.contains(slot) && slot == mainVersionSlot.get(mainVersionSlot.size() - 1)) {

                addItem(slot + 4, buildHead("§rÀ Venir...", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ0NTJiZmNkYzNlYTE0ZjllNjEyYjFjOTZhYmVmOTdjMTBlOTZjNzExNmVhMmE0YjFhNWRmOGQ0YWExNzJmOSJ9fX0="));
            }
        });
    }

    public void buildSubVersionsLine(Integer mainVersionsSlot, List<SubVersion> currentSubVersionsList, boolean clean) {

        currentSubVersionsList.sort(Comparator.comparing((SubVersion subVersion) ->
                                Integer.parseInt(subVersion.getName().substring(
                                        subVersion.getName().lastIndexOf(".") + 1))).reversed());

        IntStream.range(mainVersionsSlot + 2, mainVersionsSlot + 8).forEach(slot -> {

            if(clean) getInventory(getPlayer().getUniqueId()).setItem(slot, new ItemStack(Material.AIR));

            if(currentSubVersionsList.size() > (slot - mainVersionsSlot) - 2) {

                SubVersion currentSubVersion = currentSubVersionsList.get((slot - mainVersionsSlot) - 2);

                addItem(slot, new ItemStackUtils(Material.NAME_TAG, currentSubVersion.getName(), 1).build());
            }
        });
    }

    public void buildVersionType(List<Integer> mainVersionsSlot, List<Integer> versionTypesSlot, List<VersionType> versionTypesList) {

        IntStream.range(versionTypesSlot.get(0), versionTypesSlot.get(versionTypesSlot.size() - 1) + 1).forEach(slot -> {

            if(versionTypesSlot.contains(slot) && versionTypesList.size() > versionTypesSlot.indexOf(slot) && versionTypesList.size() > 1) {

                VersionType currentVersionType = versionTypesList.get(versionTypesSlot.indexOf(slot));

                addItem(slot, buildHead("§r" + currentVersionType.getSubVersionType(), currentVersionType.getTexture()), (e) -> {
                    changeSubVersion(mainVersionsSlot, currentVersionType);
                });
            }
        });
    }

    public void changeSubVersion(List<Integer> mainVersionSlot, VersionType versionType) {

        List<MainVersion> mainVersionList = versionType.getMainVersions();

        mainVersionList.sort(Comparator.comparing((MainVersion v) ->
                Integer.parseInt(v.getName().substring(v.getName().indexOf(".") + 1))).reversed());

        buildVersionGUI(mainVersionSlot, mainVersionList, 0, true);
    }

    public void moveUpright(List<Integer> mainVersionSlot, List<MainVersion> mainVersionList, int previouslyStartedAt, boolean isMovingUp) {

        if(isMovingUp && previouslyStartedAt != 0) buildVersionGUI(mainVersionSlot, mainVersionList, previouslyStartedAt - 1, true);
        else if(!isMovingUp && previouslyStartedAt != mainVersionList.size() - 5) buildVersionGUI(mainVersionSlot, mainVersionList, previouslyStartedAt + 1, true);
    }

    public void moveSideways(Integer mainVersionSlot, List<SubVersion> currentSubVersionsList, boolean isRight) {

        List<SubVersion> finalSubVersionsList = new ArrayList<>(currentSubVersionsList);

        if(isRight) finalSubVersionsList.remove(0);
        else finalSubVersionsList.remove(currentSubVersionsList.size() - 1);

        buildSubVersionsLine(mainVersionSlot, finalSubVersionsList, true);
    }

    public ItemStack buildHead(String name, String texture) {

        ItemStackUtils item = new ItemStackUtils(Material.SKULL_ITEM, name, 1);
        item.setTexture(texture);

        return item.buildSkull();
    }

    public void searchGUI(String search, List<Integer> mainVersionSlots, List<MainVersion> mainVersionList, List<Integer> versionTypeSlot, List<VersionType> versionTypeList) {

        boolean versionExist = versionsManager.getSubVersions().stream().anyMatch(sv -> sv.getName().equals(search)
                || sv.getMainVersion().getName().equals(search));

        if(versionExist) {

            SubVersion subVersion = versionsManager.getSubVersions().stream().filter(sv -> sv.getName().equals(search)
                    ).findFirst().orElse(null);

            MainVersion mainVersion = versionsManager.getMainVersions().stream().filter(mv -> mv.getName().equals(search))
                    .findFirst().orElse(null);

            if(subVersion == null) {

                List<SubVersion> subVersionList = mainVersion.getSubVersions();
                List<Integer> availableSlots = Arrays.asList(20, 21, 22, 23, 24, 30, 31, 32, 40);
                AtomicInteger currentSelectedVersion = new AtomicInteger();

                IntStream.range(0, subVersionList.size()).forEach(slot -> {

                    ItemStack subVersionItem = buildHead(subVersionList.get(currentSelectedVersion.get()).getName(), subVersionList.get(currentSelectedVersion.get()).getMainVersion().getTexture());

                    addItem(availableSlots.get(currentSelectedVersion.get()), subVersionItem);

                    currentSelectedVersion.getAndIncrement();
                });

            } else {

                ItemStack error = buildHead("§r" + subVersion.getName(), subVersion.getMainVersion().getTexture());

                getInventory(getPlayer().getUniqueId()).setItem(31, error);
            }


        } else {

            buildVersionType(mainVersionSlots, versionTypeSlot, versionTypeList);

            ItemStack error = buildHead("§cAucune version trouvée", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWY0ZTE4ZDIxZjY0NDdmNzQ0MmVlYWE1NTYyZjhmNjFjZDdiNjM1ZjM4MzJkNGFiNmNlODBkYzJlNWEzMTJmMSJ9fX0=");
            ItemStack reset = buildHead("§2Re-Initialiser", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JlY2YzYWFmZDc4YTg0Mzk0YWQzNzc1ZTZhOTg3M2YwMGViOTZlZGMyNjljZmNiMmVkOWNjNzgxM2FmM2EyYyJ9fX0=");

            addItem(22, error);
            addItem(31, reset, (e) -> buildVersionGUI(mainVersionSlots, mainVersionList, 0, true));
        }
    }

    public static String parseVersion(String input) {
        // Remove any non-digit characters and assemble the remaining numbers with dots
        String cleanedInput = input.replaceAll("\\D", ".");

        // Ensure there is only one dot between the numbers
        cleanedInput = cleanedInput.replaceAll("\\.{2,}", ".");

        // Remove dots at the start and end of the string
        cleanedInput = cleanedInput.replaceAll("^\\.|\\.$", "");

        System.out.println(cleanedInput);

        return cleanedInput;
    }
}
