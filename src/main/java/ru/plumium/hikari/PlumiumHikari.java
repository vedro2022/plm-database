package ru.plumium.hikari;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlumiumHikari extends JavaPlugin {
    private FileConfiguration config;

    public @NotNull FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void onEnable() {
        loadConfig();

        try {
            getServer().getServicesManager()
                    .register(PlumiumHikariAPI.class,
                            PH.class.getConstructor(PlumiumHikari.class).newInstance(this),
                            this,
                            ServicePriority.Normal);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {

    }

    private void loadConfig() {
        File configFile = new File(getDataFolder() + File.separator + "config.yml");
        if (!configFile.exists()) {
            Bukkit.getLogger().warning("Файл конфигурации не обнаружен, создаём дефолтный.");
            this.saveDefaultConfig();
            config = YamlConfiguration.loadConfiguration(configFile);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

//    public static Connection getConnection() throws SQLException {
//        return hikariPool.getConnection();
//    }
//
//    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet res) {
//        hikariPool.close(conn, ps, res);
//    }
//
//    public static String getTestString() {
//        return testString;
//    }
}
