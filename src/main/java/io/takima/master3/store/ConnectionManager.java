package io.takima.master3.store;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

import java.sql.SQLException;

public enum ConnectionManager {
    INSTANCE;
    private final HikariDataSource hikariDataSource;

    ConnectionManager() {
        HikariConfig config = new HikariConfig("/application.yml");
        this.hikariDataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return this.hikariDataSource.getConnection();
    }
}
