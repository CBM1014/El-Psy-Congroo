package cat.melon.El_Psy_Congroo.Utils.lib;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

public class LocalizationAPI {
    private static final Map<String, YamlConfiguration> caches = Maps.newHashMap();
    
    public static class LocaleData implements Serializable {
        private static final long serialVersionUID = 1L;

        public final String locale;
        public final transient YamlConfiguration source;

        public LocaleData(String locale, File sourceFile) {
            this.locale = locale;
            YamlConfiguration yaml = caches.get(locale);
            if (yaml == null)
                // Potential issue: a empty config will be returned if the given locale do not exist
                caches.put(locale, (yaml = YamlConfiguration.loadConfiguration(sourceFile)));
            this.source = yaml;
        }
        
        public LocaleData(String locale, String bedrock) {
            this.locale = locale;
            YamlConfiguration yaml = caches.get(locale);
            if (yaml == null)
                // Potential issue: is that bedrock exist?
                caches.put(locale, (yaml = caches.get(bedrock)));
            this.source = yaml;
        }

        private LocaleData(String locale, YamlConfiguration source) {
            this.locale = locale;
            this.source = source;
        }
    }

    public static String localizeAt(String where, LocaleData desired, LocaleData bedrock) {
        String localizedText = desired.source.getString(where);
        if (localizedText != null)
            return localizedText;

        assert desired.locale.equalsIgnoreCase(bedrock.locale)
                : "American English is not allowed";

        return bedrock.source.getString(where);
    }

    public static LocaleData localizeDataFrom(Plugin from, String locale) {
        return localizeDataFrom(from, locale, null /* high self-confidence */);
    }
    
    public static class Opcodes {
        public static final LocaleData CONTINUE = new LocaleData(null, (YamlConfiguration) null);
    }

    public static LocaleData localizeDataFrom(Plugin from, String locale, @Nullable Function<File, LocaleData> injector) {
        // place files
        from.saveResource("lang/" + locale + ".yml", false); // inside jar -> lang/`lang`.yml
        File languageFile = new File(from.getDataFolder(), locale + ".yml");

        // accept consumer
        if (injector != null) {
            LocaleData injected = injector.apply(languageFile);
            if (injected != Opcodes.CONTINUE)
                return injected;
        }

        return new LocaleData(locale, languageFile);
    }
}