package cat.melon.El_Psy_Congroo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cat.melon.El_Psy_Congroo.Utils.lib.LocalizationAPI;
import cat.melon.El_Psy_Congroo.Utils.lib.LocalizationAPI.Opcodes;

public class LanguageManager {
    private static final String DEFAULT_LOCALE = "en_US";
    private LocalizationAPI.LocaleData defaultLang;
    private LocalizationAPI.LocaleData lang;
    FileConfiguration languageconfig;

    public LanguageManager(JavaPlugin plugin, String languageLocation) {
        FileConfiguration config = plugin.getConfig();

        // Setup default language
        this.defaultLang = LocalizationAPI.localizeDataFrom(plugin, DEFAULT_LOCALE);

        // Apply desired language
        String desiredLanguage = config.getString(languageLocation);
        this.lang = LocalizationAPI.localizeDataFrom(plugin, desiredLanguage, file -> {
            if (file.exists())
                return Opcodes.CONTINUE;

            Bukkit.getLogger().warning("Language " + desiredLanguage + " is not supported, use default " + DEFAULT_LOCALE);
            return LocalizationAPI.localizeDataFrom(plugin, DEFAULT_LOCALE);
        });

    }

    //return texts with placeholders
    public String getLang(String languagePath, Object... placeHolders) {
        String str = LocalizationAPI.localizeAt(languagePath, lang, defaultLang);
        for (int i = 0; i < placeHolders.length; i++) {
            str.replaceAll("\\{" + i + "\\}", placeHolders[i].toString());
        }
        return str;
    }

    //if no placeholders in texts, using this method to skip the placeholder loop
    public String getLang(String languagePath) {
        return LocalizationAPI.localizeAt(languagePath, lang, defaultLang);
    }

}
