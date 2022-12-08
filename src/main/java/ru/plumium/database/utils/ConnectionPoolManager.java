package ru.plumium.database.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.plumium.database.PLMDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private final PLMDatabase plugin;

    private HikariDataSource dataSource;
    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;
    private int minimumConnections;
    private int maximumConnections;
    private int connectionTimeout;
    private int maxLifetime;

    public ConnectionPoolManager(PLMDatabase plugin) {
        this.plugin = plugin;
        initVariables();
        setupPool();
    }

    private void initVariables() {
        hostname = plugin.getConfigUtils().getConfigString("SQL.HOSTNAME");
        port = plugin.getConfigUtils().getConfigString("SQL.PORT");
        database = plugin.getConfigUtils().getConfigString("SQL.DATABASE");
        username = plugin.getConfigUtils().getConfigString("SQL.USERNAME");
        password = plugin.getConfigUtils().getConfigString("SQL.PASSWORD");
        minimumConnections = plugin.getConfigUtils().getConfigInt("SQL.CONNECTIONS_MIN");
        maximumConnections = plugin.getConfigUtils().getConfigInt("SQL.CONNECTIONS_MAX");
        connectionTimeout = plugin.getConfigUtils().getConfigInt("SQL.CONNECTIONS_TIMEOUT");
        maxLifetime = plugin.getConfigUtils().getConfigInt("SQL.MAX_LIFE_TIME");
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        hostname + ":" + port + "/" + database +
                        "?serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=utf8"
        );
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(minimumConnections);
        config.setMaximumPoolSize(maximumConnections);
        config.setMaxLifetime(maxLifetime);
        config.setConnectionTimeout(connectionTimeout);
        config.setConnectionTestQuery("SELECT 1;");
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
