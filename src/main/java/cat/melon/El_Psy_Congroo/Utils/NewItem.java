package cat.melon.El_Psy_Congroo.Utils;

import cat.melon.El_Psy_Congroo.Init;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class NewItem implements Listener {
    private Material type;
    private short damage;
    private String displayName;
    private List<String> lore;

    public NewItem(Init instance, Material type, String displayname, short damage, List<String> lore) {
        this.type = type;
        this.damage = damage;
        this.displayName = displayname;
        this.lore = lore;
        Bukkit.getServer().getPluginManager().registerEvents(this, instance);
    }

    public ItemStack getItem(int amount) {
        ItemStack tmpis = new ItemStack(type, amount, damage);
        tmpis.setLore(lore);
        tmpis.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        tmpis.getItemMeta().setUnbreakable(true);
        tmpis.getItemMeta().setDisplayName("");
        //TODO set item name here
        NBTItem tmpni = new NBTItem(tmpis);
        tmpni.setBoolean("ElNewItem", true);
        return tmpni.getItem();
    }

    public Material getType() {
        return type;
    }

    public short getDamage() {
        return damage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }
}
