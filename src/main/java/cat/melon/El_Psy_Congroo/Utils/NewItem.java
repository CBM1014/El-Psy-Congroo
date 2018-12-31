package cat.melon.El_Psy_Congroo.Utils;

import cat.melon.El_Psy_Congroo.Init;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import sun.plugin2.main.server.Plugin;

import java.util.List;

public class NewItem implements Listener {
    private Material type;
    private short damage;
    private String displayname;
    private List<String> lore;

    public NewItem(Init instance,Material type, String displayname, short damage, List<String> lore) {
        this.type = type;
        this.damage = damage;
        this.displayname = displayname;
        this.lore = lore;
        Bukkit.getServer().getPluginManager().registerEvents(this,instance);
    }

    public ItemStack getItem(int amount){
        ItemStack tmpis = new ItemStack(type,amount,damage);
        tmpis.setLore(lore);
        tmpis.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        tmpis.getItemMeta().setDisplayName("");
        //TODO set item name here
        return tmpis;
    }

    public int getDamage() {
        return damage;
    }

    public Material getType() {
        return type;
    }
}
