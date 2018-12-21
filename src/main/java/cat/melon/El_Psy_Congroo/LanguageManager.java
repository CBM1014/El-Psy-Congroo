package cat.melon.El_Psy_Congroo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cat.melon.El_Psy_Congroo.Utils.lib.LocaleAPI;
import cat.melon.El_Psy_Congroo.Utils.lib.LocaleAPI.Opcodes;

public class LanguageManager {
    private static final String DEFAULT_LOCALE = "en_US";
    private LocaleAPI.LocaleData defaultLang;
    private LocaleAPI.LocaleData lang;
    FileConfiguration languageconfig;
    
    public String getLang(String languagePath) {
        return LocaleAPI.localizeAt(languagePath, lang, defaultLang);
    }

    public LanguageManager(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        
        // Setup default language
        this.defaultLang = LocaleAPI.localizeDataFrom(plugin, DEFAULT_LOCALE);

        // Apply desired language
        String desiredLanguage = config.getString("config.language");
        this.lang = LocaleAPI.localizeDataFrom(plugin, desiredLanguage, file -> {
            if (file.exists())
                return Opcodes.CONTINUE;

            Bukkit.getLogger().warning("Language " + desiredLanguage + " is not supported, use default " + DEFAULT_LOCALE);
            return LocaleAPI.localizeDataFrom(plugin, DEFAULT_LOCALE);
        });

    }

    public LanguageManager(String language) {
    }

    public String getLang(String langPath, Object... placeHolders /* 这是做什么的qwq */) {
        return "";
    }

}
