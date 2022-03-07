package ru.plumium.hikari;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractPlumiumHikari implements PlumiumHikariAPI {
    @Override
    public Connection getConnection() throws SQLException {
        return getConnection();
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public String getTestString() {
        return getTestString();
    }
}
