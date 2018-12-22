package cat.melon.El_Psy_Congroo.Utils.lib;

import java.io.File;
import java.io.Serializable;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class LocalizationAPI {
    public static class LocaleData implements Serializable {
        private static final long serialVersionUID = 1L;

        public final String locale;
        public final transient YamlConfiguration source;

        public LocaleData(String locale, File sourceFile) {
            this(locale, YamlConfiguration.loadConfiguration(sourceFile));
        }

        public LocaleData(String locale, YamlConfiguration source) {
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