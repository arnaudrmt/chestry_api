package bukkit.fr.arnaud.chestry.hub.versions;

import java.util.ArrayList;
import java.util.List;

public class VersionType {

    private final String name;
    private String sub_version_type;
    private final String description;
    private final String texture;
    private final double price;
    private final List<MainVersion> main_versions = new ArrayList<>();

    public VersionType(String name, String sub_version_type, String description, String texture, double price) {
        this.name = name;
        this.sub_version_type = sub_version_type;
        this.description = description;
        this.texture = texture;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSubVersionType() {
        return sub_version_type;
    }

    public void setSubVersionType(String sub_version_type) {
        this.sub_version_type = sub_version_type;
    }

    public String getDescription() {
        return description;
    }

    public String getTexture() {
        return texture;
    }

    public double getPrice() {
        return price;
    }

    public List<MainVersion> getMainVersions() {
        return main_versions;
    }

    public void addMainVersion(MainVersion mainVersions) {
        this.main_versions.add(mainVersions);
    }

    public int getMainVersionsSize() {
        return main_versions.size();
    }
}
