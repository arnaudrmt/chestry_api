package bukkit.fr.arnaud.chestry.hub.versions;

import bukkit.fr.arnaud.chestry.ChestryBukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VersionsManager {

    List<VersionType> versionTypes = new ArrayList<>();
    List<MainVersion> mainVersions = new ArrayList<>();
    List<SubVersion> subVersions = new ArrayList<>();

    public VersionType getVersionTypeFromName(String name) {

        return versionTypes.stream().filter(t -> t.getName().contains(name)).findFirst().orElse(null);
    }

    public VersionType getVersionTypesFromName(String name, String sub_type) {

        return versionTypes.stream().filter(t -> t.getName().equalsIgnoreCase(name) && t.getSubVersionType().equals(sub_type)).findFirst().orElse(null);
    }

    public List<VersionType> getVersionTypeListFromMainVersion(MainVersion main_version) {

        return versionTypes.stream().filter(vt -> vt.getName().equals(main_version.getVersionType().getName())).collect(Collectors.toList());
    }

    public List<MainVersion> getMainVersions() {

        return mainVersions;
    }

    public MainVersion getMainVersionFromName(String name) {

        return mainVersions.stream().filter(v -> v.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<SubVersion> getSubVersions() {
        return subVersions;
    }


    public void importVersionsFromMongoDB() {

        versionTypes.clear();
        mainVersions.clear();
        subVersions.clear();

        versionTypes.addAll(ChestryBukkit.getInstance().getMongoDB().getVersionTypes());
        mainVersions.addAll(ChestryBukkit.getInstance().getMongoDB().getMainVersion());
        subVersions.addAll(ChestryBukkit.getInstance().getMongoDB().getSubVersion());
    }
}
