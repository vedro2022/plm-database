package ru.plumium.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {
    private final Plugin plugin;

    private static HikariDataSource dataSource;

    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;
    private int minimumConnections;
    private int maximumConnections;
    private int connectionTimeout;
    private int maxLifetime;
    private String testQuery;

    public ConnectionPoolManager(Plugin plugin) {
        this.plugin = plugin;
        init();
        setupPool();
    }

    private void init() {
        hostname = plugin.getConfig().getString("sql.hostname");
        port = plugin.getConfig().getString("sql.port");
        database = plugin.getConfig().getString("sql.database");
        username = plugin.getConfig().getString("sql.username");
        password = plugin.getConfig().getString("sql.password");
        minimumConnections = plugin.getConfig().getInt("sql.connectionsMin");
        maximumConnections = plugin.getConfig().getInt("sql.connectionsMax");
        connectionTimeout = plugin.getConfig().getInt("sql.connectionsTimeout");
        maxLifetime = plugin.getConfig().getInt("sql.maxLifetime");
        testQuery = "SELECT 1;";
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        hostname +
                        ":" +
                        port +
                        "/" +
                        database +
                        "?serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=utf8"
        );
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(minimumConnections);
        config.setMaximumPoolSize(maximumConnections);
        config.setMaxLifetime(maxLifetime);
        config.setConnectionTimeout(connectionTimeout);
        config.setConnectionTestQuery(testQuery);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
