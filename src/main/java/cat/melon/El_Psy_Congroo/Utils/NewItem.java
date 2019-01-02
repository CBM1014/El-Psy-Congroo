package cat.melon.El_Psy_Congroo.Utils;

import cat.melon.El_Psy_Congroo.Init;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class NewItem implements Listener {
    private Material type;
    private short damage;
    private String namePath;
    private List<String> lore;
    private boolean isRegistered = false;
    private Init instance;

    public NewItem(Init instance, Material type, String namePath, short damage, List<String> lore) {
        this.instance = instance;
        this.type = type;
        this.damage = damage;
        this.namePath = namePath;
        this.lore = lore;
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

    public ItemStack getItem(){
        return this.getItem(1);
    }

    public Material getType() {
        return type;
    }

    public short getDamage() {
        return damage;
    }

    public String getNamePath() {
        return namePath;
    }

    public List<String> getLore() {
        return lore;
    }

    public boolean registerEventListeners(){
        if(!isRegistered){
            Bukkit.getServer().getPluginManager().registerEvents(this, instance);
            isRegistered = true;
            return true;
        }else{
            return false;
        }

    }
}
