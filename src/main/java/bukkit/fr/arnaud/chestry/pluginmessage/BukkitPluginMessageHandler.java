package bukkit.fr.arnaud.chestry.pluginmessage;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

public class BukkitPluginMessageHandler implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {

        if (!channel.equalsIgnoreCase( "chestry:bukkit")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );

        String subChannel = in.readUTF();
    }

    public <T> void sendPluginMessage(String sub, T... data) {

        Collection<? extends Player> onlinePlayers = ChestryBukkit.getInstance().getServer().getOnlinePlayers();

        if(onlinePlayers.isEmpty()) return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(sub);
        Arrays.stream(data).forEach(a -> out.writeUTF((String) a));

        onlinePlayers.stream().findAny().get().sendPluginMessage(ChestryBukkit.getInstance(), "chestry:bungee", out.toByteArray());
    }

    private String[] getArgs(ByteArrayDataInput in, int length) {

        String[] data = new String[length];
        IntStream.range(0, length).forEach(i -> data[i] = (in.readUTF()));
        return data;
    }
}