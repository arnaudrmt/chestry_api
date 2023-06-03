package bukkit.fr.arnaud.chestry.hub.commands;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.guis.MainGUI;
import bukkit.fr.arnaud.chestry.hub.utils.AbstractSign;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(args[0].equalsIgnoreCase("mongo")) ChestryBukkit.getInstance().getVersionsManager().importVersionsFromMongoDB();
        else if(args[0].equalsIgnoreCase("search")) {



        } else {

            new MainGUI((Player) sender).open();
        }

        return false;
    }

    public static void openSignForPlayer(Player player, Sign sign) {
        // Get the NMS handle of the player
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = craftPlayer.getHandle();

        // Set the tile location of the sign for the player
        BlockPosition position = new BlockPosition(sign.getX(), sign.getY(), sign.getZ());

        // Create a new packet to open the sign for the player
        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(position);

        // Send the packet to the player
        entityPlayer.playerConnection.sendPacket(packet);
    }
}
