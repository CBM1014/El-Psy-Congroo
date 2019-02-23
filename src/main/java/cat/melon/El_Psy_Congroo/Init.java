package cat.melon.el_psy_congroo;

import cat.melon.el_psy_congroo.eventlisteners.EndUpdater;
import cat.melon.el_psy_congroo.eventlisteners.EnderCrystalRespawner;
import cat.melon.el_psy_congroo.eventlisteners.Plus1s;
import cat.melon.el_psy_congroo.eventlisteners.SeasonalBiome;
import cat.melon.el_psy_congroo.utils.lib.AgendaGlow;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import cat.melon.el_psy_congroo.commands.CommandFactory;

public class Init extends JavaPlugin {
    private int status = 0;
    private LanguageManager languageManager;
    private FileConfiguration config;
    private NewItemManager newItemManager;
    private StatusManager statusManager;
    private SubtitleManager subtitleManager = new SubtitleManager();
    private Enchantment agendaGlow;

    @Override
    public void onEnable() {
        long timestart = System.currentTimeMillis();
        config = this.getConfig();
        languageManager = new LanguageManager(this, this.config.getString("language"));
        statusManager = new StatusManager(this);
        //setup plugin
        this.getLogger().info(languageManager.getLang("plugin.load.completed", (System.currentTimeMillis() - timestart) / 1000));
        
        CommandFactory.registry(this);
        
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            agendaGlow = new AgendaGlow(new NamespacedKey(this, "agenda_glow"));
            Enchantment.registerEnchantment(agendaGlow);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        newItemManager = new NewItemManager(this);
        
        //SeasonalBiome.registerAsListener(this);
        
        Bukkit.getServer().getPluginManager().registerEvents(newItemManager,this);
        Bukkit.getServer().getPluginManager().registerEvents(statusManager,this);
        Bukkit.getServer().getPluginManager().registerEvents(new Plus1s(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new EndUpdater(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new EnderCrystalRespawner(this),this);
    }

    public SubtitleManager getSubtitleManager() {
        return subtitleManager;
    }

    public NewItemManager getNewItemManager(){
        return newItemManager;
    }
    public StatusManager getStatusManager(){
        return statusManager;
    }
    
    public Enchantment getAgendaGlow(){
        return agendaGlow;
    }

    protected FileConfiguration getBukkitFileConfiguration() {
        return config;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
