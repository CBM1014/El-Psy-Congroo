package cat.melon.El_Psy_Congroo;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import cat.melon.El_Psy_Congroo.Utils.lib.LocalizationAPI;
import cat.melon.El_Psy_Congroo.Utils.lib.LocalizationAPI.Opcodes;
import moe.kira.personal.PersonalAPI;

public class LanguageManager {
    private static final String BEDROCK_LOCALE = "en_UK";
    private String defaultLang;
    private LocalizationAPI langManager;

    public LanguageManager(Plugin plugin, String defaultLang) {
        langManager = new LocalizationAPI(plugin, (desired, file) -> {
            if (file.exists())
                return Opcodes.CONTINUE;

            Bukkit.getLogger().warning("Language " + desired + " is not supported, use default " + BEDROCK_LOCALE);
            return langManager.initaliseOrAcquireLanguageData(BEDROCK_LOCALE);
        });
        this.defaultLang = defaultLang;
    }
    
    public String getLang(String languagePath, Object... placeHolders) {
        return getLang(languagePath, defaultLang, placeHolders);
    }
    
    public String getLang(String languagePath, CommandSender player, Object... placeHolders) {
        return getLang(languagePath, PersonalAPI.of(player).getString("el_psy_congroo.locale"), placeHolders);
    }

    //return texts with placeholders
    public String getLang(String languagePath, String lang, Object... placeHolders) {
        String translated = langManager.localizeAt(languagePath, lang);
        for (int i = 0; i < placeHolders.length; i++) {
            translated = StringUtils.replace(translated, "\\{" + i + "\\}", placeHolders[i].toString());
        }
        return translated;
    }

    public String getLang(String languagePath) {
        return getLang(languagePath, defaultLang);
    }
    
    public String getLang(String languagePath, CommandSender player) {
        return getLang(languagePath, PersonalAPI.of(player).getString("el_psy_congroo.locale"));
    }
    
    //if no placeholders in texts, using this method to skip the placeholder loop
    public String getLang(String languagePath, String lang) {
        return langManager.localizeAt(languagePath, lang);
    }
}
