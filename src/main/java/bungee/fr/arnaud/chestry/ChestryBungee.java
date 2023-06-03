package bungee.fr.arnaud.chestry;

import bungee.fr.arnaud.chestry.backend.docker.pluginmessage.BungeePluginMessageHandler;
import bungee.fr.arnaud.chestry.backend.mongodb.MongoDB;
import bungee.fr.arnaud.chestry.backend.multithreading.MultiThreading;
import bungee.fr.arnaud.chestry.frontend.commands.BungeeDockerCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class ChestryBungee extends Plugin {

    private static ChestryBungee instance;
    private MultiThreading multiThreading = new MultiThreading();
    private MongoDB mongoDB = new MongoDB();

    @Override
    public void onEnable() {

        instance = this;

        getProxy().registerChannel( "chestry:bungee" );
        getProxy().getPluginManager().registerListener(this, new BungeePluginMessageHandler());
        getProxy().getPluginManager().registerCommand(this, new BungeeDockerCommand());

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
}
