package bukkit.fr.arnaud.chestry.hub.utils;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.versions.VersionType;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AbstractSign {

    ProtocolManager protocolManager = ChestryBukkit.getInstance().getProtocolManager();
    Player player;

    Map<Player, BlockPosition> signs = new HashMap<>();
    Map<Player, VersionType> vt = new HashMap<>();

    public AbstractSign(Player p, VersionType versionType) {
        this.player = p;
        this.vt.put(p, versionType);
        listening();
        open();
    }

    public void open() {

        CraftPlayer craftPlayer = (CraftPlayer) player;
        BlockPosition blockPosition = new BlockPosition(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(blockPosition);
        craftPlayer.getHandle().playerConnection.sendPacket(packet);

        signs.put(player, blockPosition);
    }

    public void listening() {

        protocolManager.addPacketListener(new PacketAdapter(
                ChestryBukkit.getInstance(),
                PacketType.Play.Client.UPDATE_SIGN
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {

                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                com.comphenix.protocol.wrappers.BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
                WrappedChatComponent[] chat = packet.getChatComponentArrays().read(0);

                System.out.printf("here");

                if(signs.get(player).equals(blockPosition)) {

                    System.out.printf("here");
                }
            }
        });
    }
}
