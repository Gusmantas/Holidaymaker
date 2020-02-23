package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class GuestSettingsHelper {
    private Scanner scanner = new Scanner(System.in);

    public void registerUser(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        try {
            statement = connect.prepareStatement("INSERT INTO guests SET first_name = ?, last_name = ?, phone_number = ? ");
            statement.setString(1, firstName.toLowerCase());
            statement.setString(2, lastName.toLowerCase());
            statement.setString(3, phoneNumber);
            statement.executeUpdate();
            String fullName = firstName + " " + lastName;
            System.out.println(fullName + " registered successfully! ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Connection connect, PreparedStatement statement) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        try {
            statement = connect.prepareStatement("DELETE FROM guests WHERE first_name = ? AND last_name = ?");
            statement.setString(1, firstName.toLowerCase());
            statement.setString(2, lastName.toLowerCase());
            statement.executeUpdate();
            String fullName = firstName + " " + lastName;
            System.out.println(fullName + " successfully deleted! ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findGuestBookings(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        while(true) {
            System.out.println("Enter guest's first name:");
            String firstName = scanner.nextLine();
            System.out.println("Enter guest's phone number: ");
            String phoneNumber = scanner.nextLine();
            checkIfGuestIsPresent(connect, statement, resultSet, firstName, phoneNumber);
            try {
                statement = connect.prepareStatement("SELECT * FROM guest_bookings WHERE guest_id = (SELECT id FROM guests WHERE first_name = ? AND phone_number = ?)");
                statement.setString(1, firstName.toLowerCase());
                statement.setString(2, phoneNumber);
                resultSet = statement.executeQuery();
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No reservations were found");
                    break;
                }
                while (resultSet.next()) {
                    String row = "Booking ID: " + resultSet.getString("booking_id") + "\n"
                            + " Order Date: " + resultSet.getString("order_datetime") + "\n"
                            + " Check-In Date: " + resultSet.getString("checkin_date") + "\n"
                            + " Check-Out Date: " + resultSet.getString("checkout_date") + "\n"
                            + " Room Type: " + resultSet.getString("type") + "\n"
                            + " Room Price: " + resultSet.getString("room_price") + "SEK";
                    System.out.println(row);
                    System.out.println("------------------------------------------------------------");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            break;
        }
    }

    private void checkIfGuestIsPresent(Connection connect, PreparedStatement statement, ResultSet resultSet, String firstName, String phoneNumber) {
        try {
            statement = connect.prepareStatement("SELECT id FROM guests WHERE first_name = ? AND phone_number = ?");
            statement.setString(1, firstName.toLowerCase());
            statement.setString(2, phoneNumber);
            resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Guest was not found.");
                findGuestBookings(connect, statement, resultSet);
            }
        } catch (Exception ex) {
            System.out.println("There was an error, try again.");
        }
    }

    public void printAllRegisteredUsers(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        try {
            statement = connect.prepareStatement("SELECT first_name, last_name, phone_number FROM guests");
            resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("There are no registered guests yet. ");
            }
            while (resultSet.next()) {
                String row = "First Name: " + resultSet.getString("first_name")
                        + " | Last Name: " + resultSet.getString("last_name")
                        + " | Phone Number: " + resultSet.getString("phone_number");
                System.out.println(row);
                System.out.println("------------------------------------------------------------");
            }
        } catch (Exception ex) {
            System.out.println("There was an error, try again.");
        }
    }
}
