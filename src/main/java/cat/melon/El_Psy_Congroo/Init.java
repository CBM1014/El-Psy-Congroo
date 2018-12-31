package cat.melon.El_Psy_Congroo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Init extends JavaPlugin {
    private int status = 0;
    LanguageManager languageManager;
    FileConfiguration config;
    SubtitleManager subtitleManager = new SubtitleManager();

    @Override
    public void onEnable() {
        Long timestart = System.currentTimeMillis();
        config = this.getConfig();
        languageManager = new LanguageManager(this, this.config.getString("language"));
        //setup plugin
        this.getLogger().info(languageManager.getLang("plugin.load.completed", (System.currentTimeMillis() - timestart) / 1000));
    }

    public FileConfiguration getBukkitFileConfiguration() {
        return config;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
