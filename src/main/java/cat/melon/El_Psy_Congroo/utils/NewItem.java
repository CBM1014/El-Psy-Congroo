package cat.melon.el_psy_congroo.utils;

import cat.melon.el_psy_congroo.Init;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NewItem implements Listener {
    private boolean basic = true;
    private Material type;
    private int modelNumber;
    private String namePath;
    private List<String> lore;
    Map<Enchantment, Integer> enchantments;
    ItemFlag[] itemFlags;

    private boolean isRegistered = false;
    private Init instance;

    public NewItem(Init instance, Material type, String namePath, int modelNumber){
        this.instance = instance;
        this.type = type;
        this.modelNumber = modelNumber;
        this.namePath = namePath;
    }

    public NewItem(Init instance, Material type, String namePath, int modelNumber, List<String> lore, Map<Enchantment, Integer> enchantments, ItemFlag... itemFlags) {
        basic = false;
        this.instance = instance;
        this.type = type;
        this.modelNumber = modelNumber;
        this.namePath = namePath;
        this.lore = lore!=null ? lore : new ArrayList<>();
        this.enchantments = enchantments != null ? enchantments : new HashMap<>();
        this.itemFlags = itemFlags != null ? itemFlags : new ItemFlag[0];
    }

    public ItemStack getItem(int amount) {
        ItemStack tmpis = new ItemStack(type, amount);
        ItemMeta meta = tmpis.getItemMeta();
        meta.setDisplayName(namePath);
        tmpis.setItemMeta(meta);
        if(!basic){
            tmpis.setLore(lore);
            tmpis.addEnchantments(enchantments);
            tmpis.addItemFlags(itemFlags);
        }
        //TODO set item name here
        NBTItem tmpni = new NBTItem(tmpis);
        tmpni.setInteger("CustomModelData", modelNumber);
        tmpni.setString("agendaItem", namePath);
        return tmpni.getItem();
    }

    public ItemStack getItem() {
        return this.getItem(1);
    }

    public Material getType() {
        return type;
    }

    public int getModelNumber() {
        return modelNumber;
    }

    public String getNamePath() {
        return namePath;
    }

    public List<String> getLore() {
        return lore;
    }

    public Init getInstance(){
        return instance;
    }

    public boolean register() {
        if (!isRegistered) {
            this.onRegister();
            Bukkit.getServer().getPluginManager().registerEvents(this, instance);
            isRegistered = true;
            return true;
        } else {
            return false;
        }

    }

    public void onRegister(){}
}
