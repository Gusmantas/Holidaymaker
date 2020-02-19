package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reservation {
    private Scanner scanner = new Scanner(System.in);
    private Guest guest = new Guest();
    private String checkOutDate;
    private String checkInDate;

    public Reservation(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        registerBooking(connect, statement, resultSet);
        bookRoom(connect, statement, resultSet);
    }

    public void registerBooking(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("To make a reservation, enter client first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter client phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.println("Enter check-in date and time (yyyy-mm-dd hh:mm):  ");
            String checkIn = scanner.nextLine();

            if (checkIn.compareTo("2020-06-01 00:00:00") <= 0) {
                System.out.println("Reservations are available between 2020.06.01 00:00:01 - 2020-07-31 23:59:59");
                checkInDate = checkIn;
                continue;

            }
            System.out.println("Enter check-out date and time (yyyy-mm-dd hh:mm):  ");
            String checkOut = scanner.nextLine();
            if (checkOut.compareTo("2020-07-31 00:00:00") >= 0) {
                checkOutDate = checkOut;
                System.out.println("Reservations are available between 2020.06.01 00:00:01 - 2020-07-31 23:59:59");
                continue;
            }

            try {
                statement = connect.prepareStatement("SELECT id FROM guests WHERE first_name = ? AND phone_number = ?");
                statement.setString(1, firstName);
                statement.setString(2, phoneNumber);
                resultSet = statement.executeQuery();
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("Guest was not found. Register guest: ");
                    guest.registerUser(connect, statement);

                } else {
                    statement = connect.prepareStatement("INSERT INTO bookings SET guest_id = (SELECT id FROM guests WHERE first_name = ? AND phone_number = ?), checkin_date = ?, checkout_date = ?, order_datetime = NOW() ");
                    statement.setString(1, firstName);
                    statement.setString(2, phoneNumber);
                    statement.setString(3, checkIn);
                    statement.setString(4, checkOut);
                    statement.executeUpdate();
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("There was an error, try again.");

            }
        }
    }


    public void bookRoom(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        displayAvailableRooms(connect, statement, resultSet);
        System.out.println("Enter ID for a room you wish to book:");
        String roomId = scanner.nextLine();
        System.out.println("Extra assets:");
        System.out.println("Add extra bed? 'Y' or 'N' ");
        String extraBed = scanner.nextLine();
        int trueOrFalse;
        if (extraBed.toLowerCase().equals("y")) {
            trueOrFalse = 1;
        } else {
            trueOrFalse = 0;
        }
        try {
            statement = connect.prepareStatement("INSERT INTO booked_rooms SET room_id = ?, booking_id = (SELECT MAX(id) FROM bookings), extra_bed = ?, meals = ?");
            statement.setString(1, roomId);
            statement.setInt(2, trueOrFalse);
            statement.setString(3, addMeal());
            statement.executeUpdate();
            System.out.println(" reservation successful! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayAvailableRooms(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        System.out.println("Enter street");
        String street = scanner.nextLine();
        System.out.println("Enter city");
        String city = scanner.nextLine();
        System.out.println("Enter country");
        String country = scanner.nextLine();
        try {
            String query = "SELECT room_id, room_type, room_description, room_price, max_persons_per_room \n" +
                    "FROM accommodation_info_and_rooms \n" +
                    "WHERE street = ? \n" +
                    "AND city = ? \n" +
                    "AND country = ? \n" +
                    "AND room_id NOT IN(SELECT room_id \n" +
                    "FROM rooms_in_bookings WHERE checkin >= ? OR checkout <= ?)";

            statement = connect.prepareStatement(query);
            statement.setString(1, street);
            statement.setString(2, city);
            statement.setString(3, country);
            statement.setString(4, checkInDate);
            statement.setString(5, checkOutDate);

            resultSet = statement.executeQuery();
            checkOutDate = null;
            checkInDate = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                String row = "Room ID:" + resultSet.getString("room_id") + " \n"
                        + "Room Type: " + resultSet.getString("room_type") + " \n"
                        + "Description: " + resultSet.getString("room_description") + "\n"
                        + "Room Price: " + resultSet.getString("room_price") + "\n"
                        + "Max Persons Per Room: " + resultSet.getString("max_persons_per_room") + "\n"
                        + "______________________________________________________________________________________";

                System.out.println(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String addMeal() {
        System.out.println("Add meals? 'Y' or 'N'");
        String addMeals = scanner.nextLine();
        String meal = null;
        if (addMeals.toLowerCase().equals("y")) {
            System.out.println("[1] to add half-board");
            System.out.println("[2] to add full-board");
            String fullOrHalfBoard = scanner.nextLine();
            switch (fullOrHalfBoard) {
                case "1":
                    return meal = "half-board";

                case "2":
                    return meal = "full-board";
            }
        }
        return meal = "none";
    }
}
