package bukkit.fr.arnaud.chestry.hub.pets;

import bukkit.fr.arnaud.chestry.hub.versions.VersionType;

import java.util.HashMap;

public class Pet {

    private final String name;
    private final String display_name;
    private final String texture;
    private final VersionType versionType;
    private final HashMap<Integer, String> skins;

    public Pet(String name, String display_name, String texture, VersionType versionType, HashMap<Integer, String> skins) {
        this.name = name;
        this.display_name = display_name;
        this.texture = texture;
        this.versionType = versionType;
        this.skins = skins;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getTexture() {
        return texture;
    }

    public VersionType getVersionType() {
        return versionType;
    }

    public HashMap<Integer, String> getSkins() {
        return skins;
    }
}
