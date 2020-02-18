package com.holidaymaker;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Application {
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Connection connect = null;

    public Application() {
        connect();
        new MainMenu(connect, statement);

    }

/*
    private void searchByFirstName(String firstName) {
        try {
            statement = connect.prepareStatement("SELECT * FROM guests WHERE first_name = ?");
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printSearchResult() {
        try {
            while (resultSet.next()) {
                String row = "id: " + resultSet.getString("id")
                        + ", first name: " + resultSet.getString("first_name")
                        + ", last name: " + resultSet.getString("last_name")
                        + ", country: " + resultSet.getString("phone_number") + ".";
                System.out.println(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
*/
    private void connect() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
