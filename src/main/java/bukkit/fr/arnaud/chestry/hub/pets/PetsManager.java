package bukkit.fr.arnaud.chestry.hub.pets;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.utils.ItemStackUtils;
import bukkit.fr.arnaud.chestry.hub.versions.VersionsManager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PetsManager {

    private final ChestryBukkit chestryBukkit = ChestryBukkit.getInstance();
    private final VersionsManager versionsManager = chestryBukkit.getVersionsManager();
    private final ArrayList<Pet> petList = new ArrayList<>();
    private final HashMap<Player, EntityArmorStand> playerEntityMap = new HashMap<>();
    private final HashMap<Player, Pet> playerPetMap = new HashMap<>();


    // Pet Management

    public void teleport(Player player) {

        if(!playerEntityMap.containsKey(player)) return;

        EntityArmorStand entityArmorStand = playerEntityMap.get(player);

        Bukkit.getOnlinePlayers().forEach(players -> {

            CraftPlayer craftPlayer = (CraftPlayer) players;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityArmorStand.getId());

            PlayerConnection playerConnection = entityPlayer.playerConnection;

            playerConnection.sendPacket(packetPlayOutEntityDestroy);
        });
    }

    public void despawn(Player player) {

        if(!playerEntityMap.containsKey(player)) return;

        EntityArmorStand entityArmorStand = playerEntityMap.get(player);

        Bukkit.getOnlinePlayers().forEach(players -> {

            CraftPlayer craftPlayer = (CraftPlayer) players;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityArmorStand.getId());

            PlayerConnection playerConnection = entityPlayer.playerConnection;

            playerConnection.sendPacket(packetPlayOutEntityDestroy);
        });

        playerEntityMap.remove(player);
        playerPetMap.remove(player);
    }

    public void spawn(Player player, Pet pet) {

        if(playerEntityMap.containsKey(player)) {
            despawn(player);
            playerEntityMap.remove(player);
            playerPetMap.remove(player);
        }

        World world = player.getWorld();
        CraftWorld craftWorld = (CraftWorld) world;
        WorldServer worldServer = craftWorld.getHandle();

        Location location = player.getLocation();

        EntityArmorStand entityArmorStand = new EntityArmorStand(worldServer);
        entityArmorStand.setPosition(location.getX(), location.getY(), location.getZ());

        entityArmorStand.setCustomName(pet.getDisplayName());
        entityArmorStand.setCustomNameVisible(true);

        entityArmorStand.setInvisible(true);
        entityArmorStand.setSmall(true);

        ItemStackUtils petItem = new ItemStackUtils(Material.SKULL_ITEM, "", 1);
        petItem.setTexture(pet.getTexture());

        Bukkit.getOnlinePlayers().forEach(players -> {

            CraftPlayer craftPlayer = (CraftPlayer) players;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntityLiving = new PacketPlayOutSpawnEntityLiving(entityArmorStand);
            PacketPlayOutEntityEquipment packetPlayOutEntityEquipment = new PacketPlayOutEntityEquipment(entityArmorStand.getId(), 4, CraftItemStack.asNMSCopy(petItem.buildSkull()));

            PlayerConnection playerConnection = entityPlayer.playerConnection;

            playerConnection.sendPacket(packetPlayOutSpawnEntityLiving);
            playerConnection.sendPacket(packetPlayOutEntityEquipment);
        });

        playerEntityMap.put(player, entityArmorStand);
        playerPetMap.put(player, pet);
    }

    public void spawn(Player receiver, Player petOwner) {

        EntityArmorStand entityArmorStand = playerEntityMap.get(petOwner);
        Pet pet = playerPetMap.get(petOwner);

        ItemStackUtils petItem = new ItemStackUtils(Material.SKULL_ITEM, "", 1);
        petItem.setTexture(pet.getTexture());

        CraftPlayer craftPlayer = (CraftPlayer) receiver;
        EntityPlayer entityPlayer = craftPlayer.getHandle();

        PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntityLiving = new PacketPlayOutSpawnEntityLiving(entityArmorStand);
        PacketPlayOutEntityEquipment packetPlayOutEntityEquipment = new PacketPlayOutEntityEquipment(entityArmorStand.getId(), 4, CraftItemStack.asNMSCopy(petItem.buildSkull()));

        PlayerConnection playerConnection = entityPlayer.playerConnection;

        playerConnection.sendPacket(packetPlayOutSpawnEntityLiving);
        playerConnection.sendPacket(packetPlayOutEntityEquipment);
    }

    // Getter


    public HashMap<Player, EntityArmorStand> getPlayerEntityMap() {
        return playerEntityMap;
    }

    public Pet getPetFromVersionTypeName(String name) {

        return petList.stream().filter(pet ->
            pet.getVersionType().getName().equalsIgnoreCase(name)
        ).findFirst().orElse(null);
    }

    // Add all the pets from MongoDB to the list

    public void addAllToPetList() {

        petList.addAll(chestryBukkit.getMongoDB().getPets());
    }
}
