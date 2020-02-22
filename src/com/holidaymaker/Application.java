package com.holidaymaker;

import java.sql.*;

public class Application {
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Connection connect = null;

    public Application() throws SQLException {
        connect();
        new MainMenu(connect, statement, resultSet);
    }

    private void connect() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
