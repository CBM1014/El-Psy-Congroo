package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Utils.SubtitleManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class Init extends JavaPlugin {
    LanguageManager languageManager;
    FileConfiguration config;
    SubtitleManager subtitleManager = new SubtitleManager();

    @Override
    public void onEnable(){
        Long timestart = System.currentTimeMillis();
        config = this.getConfig();
        languageManager = new LanguageManager(this.config.getString("language"));
        //setup plugin
        this.getLogger().info(languageManager.getLang("plugin.load.completed",(System.currentTimeMillis()-timestart)/1000));
    }
}
