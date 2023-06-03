package bukkit.fr.arnaud.chestry;

import bukkit.fr.arnaud.chestry.hub.commands.TestCommand;
import bukkit.fr.arnaud.chestry.hub.events.PreLoginEvent;
import bukkit.fr.arnaud.chestry.hub.events.UtilsEvent;
import bukkit.fr.arnaud.chestry.hub.mongodb.MongoDB;
import bukkit.fr.arnaud.chestry.hub.packets.PacketListener;
import bukkit.fr.arnaud.chestry.hub.versions.VersionsManager;
import bukkit.fr.arnaud.chestry.messaging.pluginmessage.BukkitPluginMessageHandler;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestryBukkit extends JavaPlugin {

    private static ChestryBukkit instance;
    private VersionsManager versionsAvailableManager = new VersionsManager();
    private MongoDB mongoDB = new MongoDB();
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {

        instance = this;

        // Register the custom channel
        getServer().getMessenger().registerIncomingPluginChannel( this, "chestry:bukkit", new BukkitPluginMessageHandler());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "chestry:bungee");
        getCommand("test").setExecutor(new TestCommand());

        getServer().getPluginManager().registerEvents(new UtilsEvent(), this);
        getServer().getPluginManager().registerEvents(new PreLoginEvent(), this);
        getServer().getPluginManager().registerEvents(new PacketListener(), this);

        new PacketListener().listenMovement();

        getMongoDB().connect();

        getVersionsManager().importVersionsFromMongoDB();

        Bukkit.getWorlds().forEach(w -> {
            w.setStorm(false);
            w.setThundering(false);
            w.setWeatherDuration(99999);
        });
    }

    @Override
    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public static ChestryBukkit getInstance() {
        return instance;
    }

    public VersionsManager getVersionsManager() {
        return versionsAvailableManager;
    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
