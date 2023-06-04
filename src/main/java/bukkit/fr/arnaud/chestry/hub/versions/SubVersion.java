package bukkit.fr.arnaud.chestry.hub.versions;

public class SubVersion {

    private final String name;
    private final MainVersion main_version;
    private final VersionType version_type;

    public SubVersion(String name, MainVersion main_version, VersionType version_type) {
        this.name = name;
        this.main_version = main_version;
        this.version_type = version_type;
        main_version.addSubVersion(this);
    }

    public String getName() {
        return name;
    }

    public MainVersion getMainVersion() {
        return main_version;
    }

    public VersionType getVersionType() {
        return version_type;
    }
}
