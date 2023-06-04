package bungee.fr.arnaud.chestry.backend.pluginmessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

public class BungeePluginMessageHandler implements Listener {

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent e) {

        if (!e.getTag().equalsIgnoreCase( "chestry:bungee")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());

        String subChannel = in.readUTF();
    }

    public <T> void sendMessage(String sub, T... data) {

        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();

        if (networkPlayers == null || networkPlayers.isEmpty()) return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(sub);
        Arrays.stream(data).forEach(a -> out.writeUTF((String) a));

        networkPlayers.stream().findAny().get().getServer().getInfo().sendData( "chestry:bukkit", out.toByteArray() );
    }

    private String[] getArgs(ByteArrayDataInput in, int length) {

        String[] data = new String[length];
        IntStream.range(0, length).forEach(i -> data[i] = (in.readUTF()));
        return data;
    }
}
