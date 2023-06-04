package bukkit.fr.arnaud.chestry.hub.commands;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.pets.Pet;
import bukkit.fr.arnaud.chestry.hub.pets.PetsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitCommand implements CommandExecutor {

    PetsManager petsManager = ChestryBukkit.getInstance().getPetsManager();;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(args[0].equalsIgnoreCase("pet")) {

            Pet pet = petsManager.getPetFromVersionTypeName(args[1]);

            petsManager.spawn(player, pet);
        }

        return false;
    }
}
