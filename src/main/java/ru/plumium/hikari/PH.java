package ru.plumium.hikari;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PH extends AbstractPlumiumHikari {
    private PlumiumHikari plugin = null;
    private ConnectionPoolManager hikariPool;
    private String testString = "halloy hallsdla sk djjkfcnj akjdkasd";

    public PH(PlumiumHikari plugin) {
        this.plugin = plugin;
        hikariPool = new ConnectionPoolManager(plugin);
        testString = "zzzzzzzzzzzzzzzzzzzzzzzzzzz";
    }

    public Connection getConnection() throws SQLException {
        return hikariPool.getConnection();
    }

    public void closeConnection(Connection conn, PreparedStatement ps, ResultSet res) {
        hikariPool.close(conn, ps, res);
    }

    public String getTestString() {
        return testString;
    }
}
