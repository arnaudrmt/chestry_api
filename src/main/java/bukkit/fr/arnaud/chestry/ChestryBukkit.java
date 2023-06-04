package bukkit.fr.arnaud.chestry;

import bukkit.fr.arnaud.chestry.hub.commands.BukkitCommand;
import bukkit.fr.arnaud.chestry.hub.events.PetsEvent;
import bukkit.fr.arnaud.chestry.hub.events.UtilsEvent;
import bukkit.fr.arnaud.chestry.hub.pets.PetsManager;
import bukkit.fr.arnaud.chestry.hub.versions.VersionsManager;
import bukkit.fr.arnaud.chestry.pluginmessage.BukkitPluginMessageHandler;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import commons.mongodb.MongoDB;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestryBukkit extends JavaPlugin {

    private static ChestryBukkit instance;
    private final VersionsManager versionsManager = new VersionsManager();
    private final PetsManager petsManager = new PetsManager();
    private final MongoDB mongoDB = new MongoDB();
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {

        instance = this;

        getServer().getMessenger().registerIncomingPluginChannel( this, "chestry:bukkit", new BukkitPluginMessageHandler());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "chestry:bungee");

        getCommand("bukkit").setExecutor(new BukkitCommand());

        getServer().getPluginManager().registerEvents(new UtilsEvent(), this);
        getServer().getPluginManager().registerEvents(new PetsEvent(), this);

        getMongoDB().connect();

        getVersionsManager().importVersionsFromMongoDB();
        getPetsManager().addAllToPetList();
    }

    @Override
    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public static ChestryBukkit getInstance() {
        return instance;
    }

    public VersionsManager getVersionsManager() {
        return versionsManager;
    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public PetsManager getPetsManager() {
        return petsManager;
    }
}
