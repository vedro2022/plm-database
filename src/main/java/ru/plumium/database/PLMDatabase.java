package ru.plumium.database;

import org.bukkit.plugin.java.JavaPlugin;
import ru.plumium.database.utils.ConfigUtils;
import ru.plumium.database.utils.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PLMDatabase extends JavaPlugin {

    private ConfigUtils configUtils;
    private ConnectionPoolManager hikariPool;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        saveDefaultConfig();
        configUtils = new ConfigUtils(this);
        hikariPool = new ConnectionPoolManager(this);

        getLogger().info("Плагин успешно загружен за " + (System.currentTimeMillis()-startTime) + " мс.");
    }

    @Override
    public void onDisable() {
        hikariPool.closePool();
    }

    public ConfigUtils getConfigUtils() {
        return configUtils;
    }

    public Connection getConnection() throws SQLException {
        return hikariPool.getConnection();
    }

    public void closeConnection(Connection conn, PreparedStatement ps, ResultSet res) {
        hikariPool.close(conn, ps, res);
    }
}
