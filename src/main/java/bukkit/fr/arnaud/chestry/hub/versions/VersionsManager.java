package bukkit.fr.arnaud.chestry.hub.versions;

import bukkit.fr.arnaud.chestry.ChestryBukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VersionsManager {

    List<VersionType> versionTypes = new ArrayList<>();
    List<MainVersion> mainVersions = new ArrayList<>();
    List<SubVersion> subVersions = new ArrayList<>();

    public List<VersionType> getVersionTypes() {
        return versionTypes;
    }

    public VersionType getVersionTypesFromName(String name) {

        return versionTypes.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public VersionType getVersionTypesFromName(String name, String sub_type) {

        return versionTypes.stream().filter(t -> t.getName().equalsIgnoreCase(name) && t.getSubVersionType().equals(sub_type)).findFirst().orElse(null);
    }

    public VersionType getVersionTypesFromSubVersionType(String name) {

        return versionTypes.stream().filter(t -> t.getSubVersionType().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<MainVersion> getMainVersions() {

        return mainVersions;
    }

    public List<VersionType> getVersionTypeListFromMainVersion(MainVersion main_version) {

        return versionTypes.stream().filter(vt -> vt.getName().equals(main_version.getVersionType().getName())).collect(Collectors.toList());
    }

    public List<SubVersion> getSubVersionsFromMainVersion(MainVersion main_version) {

        return subVersions.stream().filter(v -> v.getMainVersion().equals(main_version)).collect(Collectors.toList());
    }

    public MainVersion getMainVersionFromName(String name) {

        return mainVersions.stream().filter(v -> v.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public VersionType getVersionTypeFromName(String name) {

        return versionTypes.stream().filter(vt -> vt.getName().contains(name)).findFirst().orElse(null);
    }

    public List<SubVersion> getSubVersions() {
        return subVersions;
    }

    public SubVersion getSubVersionFromMainVersion(MainVersion main_version) {

        return subVersions.stream()
                .filter(version -> version.getMainVersion().equals(main_version))
                .findFirst().orElse(null);
    }

    public void addMainVersion(MainVersion version) {

        mainVersions.add(version);
    }

    public void removeMainVersion(MainVersion version) {

        mainVersions.remove(version);
    }

    public void addSubVersion(SubVersion version) {

        subVersions.add(version);
    }

    public void removeSubVersion(SubVersion version) {

        subVersions.remove(version);
    }

    public void addVersionType(VersionType type) {

        versionTypes.add(type);
    }

    public void removeVersionType(VersionType type) {

        versionTypes.remove(type);
    }

    public void importVersionsFromMongoDB() {

        versionTypes.clear();
        mainVersions.clear();
        subVersions.clear();

        ChestryBukkit.getInstance().getMongoDB().getVersionTypes().forEach(this::addVersionType);
        ChestryBukkit.getInstance().getMongoDB().getMainVersion().forEach(this::addMainVersion);
        ChestryBukkit.getInstance().getMongoDB().getSubVersion().forEach(this::addSubVersion);
    }
}
