package ru.plumium.database.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.plumium.database.PLMDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private final HikariDataSource dataSource;

    public ConnectionPoolManager(PLMDatabase plugin) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        plugin.getConfigUtils().getConfigString("SQL.HOSTNAME") +
                        ":" +
                        plugin.getConfigUtils().getConfigString("SQL.PORT") +
                        "/" +
                        plugin.getConfigUtils().getConfigString("SQL.DATABASE") +
                        "?serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=utf8"
        );
        config.setUsername(plugin.getConfigUtils().getConfigString("SQL.USERNAME"));
        config.setPassword(plugin.getConfigUtils().getConfigString("SQL.PASSWORD"));
        config.setMaximumPoolSize(plugin.getConfigUtils().getConfigInt("SQL.CONNECTIONS_MAX"));
        config.setConnectionTimeout(plugin.getConfigUtils().getConfigInt("SQL.CONNECTIONS_TIMEOUT"));
        config.setMaxLifetime(plugin.getConfigUtils().getConfigInt("SQL.MAX_LIFE_TIME"));
        config.setIdleTimeout(plugin.getConfigUtils().getConfigInt("SQL.IDLE_TIMEOUT"));
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
