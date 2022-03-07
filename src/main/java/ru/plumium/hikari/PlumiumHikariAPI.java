package ru.plumium.hikari;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlumiumHikariAPI {
    static Connection getConnection() throws SQLException {
        return null;
    }

    static void closeConnection() { }

    static String getTestString() {
        return null;
    }
}
