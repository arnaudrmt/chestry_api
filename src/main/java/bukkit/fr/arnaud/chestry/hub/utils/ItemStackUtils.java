package bukkit.fr.arnaud.chestry.hub.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class ItemStackUtils {

    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private SkullMeta skullMeta;

    public ItemStackUtils(Material mat, String name, Integer amount) {

        itemStack = new ItemStack(mat, amount);

        if(mat.equals(Material.SKULL_ITEM)) {
            skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setDisplayName(name);
        } else {
            itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
        }
    }

    public void setUnbreakable() {

        if(skullMeta == null) itemMeta.spigot().setUnbreakable(true);
        else skullMeta.spigot().setUnbreakable(true);
    }

    public void setDisplayName(String name) {

        if(skullMeta == null) itemMeta.setDisplayName(name);
        else skullMeta.setDisplayName(name);
    }

    public void setLore(String... args) {

        if(skullMeta == null) itemMeta.setLore(Arrays.asList(args));
        else skullMeta.setLore(Arrays.asList(args));
    }

    public void setOwner(Player owner) {

        skullMeta.setOwner(owner.getName());
    }

    public void addFlag(ItemFlag itemFlag) {

        if(skullMeta == null) itemMeta.addItemFlags(itemFlag);
        else skullMeta.addItemFlags(itemFlag);
    }

    public void setMeta(Short meta) {

        itemStack.setDurability(meta);
    }

    public void setTexture(String texture) {

        setMeta((short) SkullType.PLAYER.ordinal());

        // Set the texture of the head item
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void adEnchantment(Enchantment enchantment, int level) {

        if(skullMeta == null) itemMeta.addEnchant(enchantment, level, true);
        else skullMeta.addEnchant(enchantment, level, true);
    }

    public ItemStack build() {

        this.itemStack.setItemMeta(itemMeta);

        return this.itemStack;
    }

    public ItemStack buildSkull() {

        this.itemStack.setItemMeta(skullMeta);

        return itemStack;
    }
}
