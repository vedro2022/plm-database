package ru.plumium.hikari;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PLMHikari extends JavaPlugin {
    private static ConnectionPoolManager hikariPool;

    @Override
    public void onEnable() {
        loadConfig();
        hikariPool = new ConnectionPoolManager(this);
    }

    @Override
    public void onDisable() {
        hikariPool.closePool();
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder() + File.separator + "config.yml");
        if (!configFile.exists()) {
            Bukkit.getLogger().warning("Файл конфигурации не обнаружен, создаём дефолтный.");
            this.saveDefaultConfig();
            YamlConfiguration.loadConfiguration(configFile);
        }
        YamlConfiguration.loadConfiguration(configFile);
    }

    public static Connection getConnection() throws SQLException {
        return hikariPool.getConnection();
    }

    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet res) {
        hikariPool.close(conn, ps, res);
    }
}
