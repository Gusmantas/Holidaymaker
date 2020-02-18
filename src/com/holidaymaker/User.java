package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class User {
    private Scanner scanner = new Scanner(System.in);

    public void registerUser(Connection connect, PreparedStatement statement) {
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        try {
            statement = connect.prepareStatement("INSERT INTO guests SET first_name = ?, last_name = ?, phone_number = ? ");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.executeUpdate();
            String fullname = firstName + " " + lastName;
            System.out.println(fullname + " registered successfully! ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Connection connect, PreparedStatement statement){
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        try {
            statement = connect.prepareStatement("DELETE FROM guests WHERE first_name = ? AND last_name = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.executeUpdate();
            String fullname = firstName + " " + lastName;
            System.out.println(fullname + " successfully deleted! ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
