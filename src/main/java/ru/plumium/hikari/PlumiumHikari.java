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

public class PlumiumHikari extends JavaPlugin {
    private FileConfiguration config;
    private static ConnectionPoolManager hikariPool;

    public @NotNull FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void onLoad() {
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
            config = YamlConfiguration.loadConfiguration(configFile);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static Connection getHikariConnection() throws SQLException {
        return hikariPool.getConnection();
    }

    public static void closeHikariConnection(Connection conn, PreparedStatement ps, ResultSet res) {
        hikariPool.close(conn, ps, res);
    }
}
