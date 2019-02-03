package cat.melon.el_psy_congroo.utils.lib;

import java.io.File;
import java.util.Map;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import com.google.common.collect.Maps;

public class LocalizationAPI {
    /**
     * Provides language files (inside jar) and default data folder
     */
    private final Plugin sourcePlugin;
    /**
     * Provides an opportunity for user to hack the initialization of languages
     */
    private final @Nullable BiFunction<String, File, YamlConfiguration> initaliseInjector;
    /**
     * A cache system for language data in order to reduce file I/O
     */
    private final Map<String, YamlConfiguration> caches = Maps.newHashMap();

    public LocalizationAPI(Plugin sourcePlugin, @Nullable BiFunction<String, File, YamlConfiguration> initaliseInjector) {
        this.sourcePlugin = sourcePlugin;
        this.initaliseInjector = initaliseInjector;
    }

    public String localizeAt(String where, String desired) {
        YamlConfiguration data = initaliseOrAcquireLanguageData(desired);
        return data.getString(where); // The bedrock has already been integrated within that injector
    }

    public YamlConfiguration initaliseOrAcquireLanguageData(String locale) {
        YamlConfiguration cached = caches.get(locale);
        if (cached == null)
            caches.put(locale, cached = initaliseLanguage(locale));
        return cached;
    }

    /**
     * Open-programming style signals to allow users to control processes
     */
    public static class Opcodes {
        public static final YamlConfiguration CONTINUE = new YamlConfiguration();
    }

    @Deprecated
    private YamlConfiguration initaliseLanguage(String locale) {
        // place required file from jar
        File mayPlacedFile = new File(sourcePlugin.getDataFolder(), "lang" + File.separator + locale + ".yml");
        if (!mayPlacedFile.exists())
            sourcePlugin.saveResource("lang" + File.separator + locale + ".yml", false); // inside jar -> lang/`lang`.yml

        // accept consumer
        if (initaliseInjector != null) {
            YamlConfiguration injected = initaliseInjector.apply(locale, mayPlacedFile);
            if (injected != Opcodes.CONTINUE)
                return injected;
        }

        return YamlConfiguration.loadConfiguration(mayPlacedFile);
    }
}