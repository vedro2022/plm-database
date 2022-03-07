package ru.plumium.hikari;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlumiumHikariAPI {
    Connection getConnection() throws SQLException;

    void closeConnection();

    String getTestString();
}
