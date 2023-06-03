package bukkit.fr.arnaud.chestry.hub.packets;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.guis.VersionGUI;
import bukkit.fr.arnaud.chestry.hub.versions.VersionType;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class PacketListener implements Listener {

    ProtocolManager protocolManager = ChestryBukkit.getInstance().getProtocolManager();

    public void listenMovement() {

        protocolManager.addPacketListener(new PacketAdapter(
                ChestryBukkit.getInstance(),
                PacketType.Play.Client.UPDATE_SIGN
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {

                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                WrappedChatComponent[] chat = packet.getChatComponentArrays().read(0);

                VersionType versionType = ChestryBukkit.getInstance().getVersionsManager().getVersionTypesFromName("Vanilla", "Stable");
                Arrays.stream(chat).filter(c -> !c.getJson().equalsIgnoreCase("")).forEach(c ->  {
                        System.out.println(c.getJson());
                        new VersionGUI(player, versionType, c.getJson()).open();
                }
                );
            }
        });
    }
}
