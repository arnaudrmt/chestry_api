package bukkit.fr.arnaud.chestry.hub.versions;

import java.util.ArrayList;
import java.util.List;

public class MainVersion {

    private final String name;
    private final String description;
    private final VersionType version_type;
    private final String texture;
    private final List<SubVersion> sub_versions = new ArrayList<>();

    public MainVersion(String name, String description, VersionType version_type, String texture) {
        this.name = name;
        this.description = description;
        this.version_type = version_type;
        this.texture = texture;
        version_type.addMainVersion(this);
    }

    public VersionType getVersionType() {
        return version_type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<SubVersion> getSubVersions() {
        return sub_versions;
    }

    public String getTexture() {
        return texture;
    }

    public void addSubVersion(SubVersion subVersions) {
        this.sub_versions.add(subVersions);
    }

    public int getSubSize() {
        return sub_versions.size();
    }
}
