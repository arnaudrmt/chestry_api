package bungee.fr.arnaud.chestry;

import bungee.fr.arnaud.chestry.backend.docker.DockerUtils;
import bungee.fr.arnaud.chestry.backend.pluginmessage.BungeePluginMessageHandler;
import commons.multithreading.MultiThreading;
import bungee.fr.arnaud.chestry.frontend.commands.BungeeCommand;
import commons.mongodb.MongoDB;
import commons.utils.ServerUtils;
import net.md_5.bungee.api.plugin.Plugin;

public class ChestryBungee extends Plugin {

    private static ChestryBungee instance;
    private final MultiThreading multiThreading = new MultiThreading();
    private final MongoDB mongoDB = new MongoDB();
    private final DockerUtils dockerUtils = new DockerUtils();
    private final ServerUtils serverUtils = new ServerUtils();

    @Override
    public void onEnable() {

        instance = this;

        getProxy().registerChannel( System.getenv("pm_bungee_channel"));
        getProxy().getPluginManager().registerListener(this, new BungeePluginMessageHandler());
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand());

        mongoDB.connect();
        multiThreading.enableMultiThreading();
    }

    @Override
    public void onDisable() {
        multiThreading.disableMultiThreading();
    }

    public static ChestryBungee getInstance() {
        return instance;
    }

    public MultiThreading getMultiThreading() {
        return multiThreading;
    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }

    public DockerUtils getDockerUtils() {
        return dockerUtils;
    }

    public ServerUtils getServerUtils() {
        return serverUtils;
    }
}
