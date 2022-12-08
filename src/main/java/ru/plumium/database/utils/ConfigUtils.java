package ru.plumium.database.utils;

import org.bukkit.configuration.file.FileConfiguration;
import ru.plumium.database.PLMDatabase;

import static java.util.Objects.requireNonNull;

public class ConfigUtils {

    private final PLMDatabase plugin;
    private FileConfiguration config;

    public ConfigUtils(PLMDatabase plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public  String getConfigString(String path) {
        if (config.contains(path)) {
            return requireNonNull(config.getString(path));
        } else {
            throw new NullPointerException("В 'config.yml' не определена String переменная " + path);
        }
    }

    public Integer getConfigInt(String path) {
        if (config.contains(path)) {
            return config.getInt(path);
        } else {
            throw new NullPointerException("В 'config.yml' не определена Int переменная " + path);
        }
    }
}
