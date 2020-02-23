package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ReservationSettings {
    private Scanner scanner = new Scanner(System.in);
    private GuestSettingsHelper guestSettingsHelper = new GuestSettingsHelper();
    private int reservationId;

    public void deleteReservation(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        guestSettingsHelper.findGuestBookings(connect, statement, resultSet);
        System.out.println("Select reservation ID you wish to remove: ");
        int removeReservation = Integer.parseInt(scanner.nextLine());
        try {
            statement = connect.prepareStatement("DELETE FROM bookings WHERE id = ?");
            statement.setInt(1, removeReservation);
            statement.executeUpdate();
            System.out.println("Reservation successfully removed!");
        } catch (Exception e) {
            System.out.println("There was an error, try again");
        }
    }

    public void updateReservation(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        displayReservation(connect, statement, resultSet);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("[1] To update check-in date");
            System.out.println("[2] To update check-out date");
            System.out.println("[3] To add or remove extra beds");
            System.out.println("[4] To change meals");
            System.out.println("[5] Exit to main menu");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    System.out.println("Enter new check-in date (yyyy-mm-dd hh:mm): ");
                    String newCheckIn = scanner.nextLine();
                    if (newCheckIn.compareTo("2020-06-01 00:00:00") <= 0) {
                        System.out.println("Reservations are available between 2020.06.01 00:00:01 - 2020-07-31 23:59:59");
                        break;
                    } else if (newCheckIn.compareTo("2020-07-31 00:00:00") >= 0) {
                        System.out.println("Reservations are available between 2020.06.01 00:00:01 - 2020-07-31 23:59:59");
                        break;
                    }
                    executeUpdate(connect, statement, "UPDATE bookings SET checkin_date = ? WHERE id =" + reservationId, newCheckIn);
                    break;
                case "2":
                    System.out.println("Enter new check-out date (yyyy-mm-dd hh:mm): ");
                    String newCheckOut = scanner.nextLine();
                    if (newCheckOut.compareTo("2020-06-01 00:00:00") <= 0) {
                        System.out.println("Reservations are available between 2020.06.01 00:00:01 - 2020-07-31 23:59:59");
                        break;
                    } else if (newCheckOut.compareTo("2020-07-31 00:00:00") >= 0) {
                        System.out.println("Reservations are available between 2020.06.01 00:00:01 - 2020-07-31 23:59:59");
                        break;
                    }
                    executeUpdate(connect, statement, "UPDATE bookings SET checkout_date = ? WHERE id =" + reservationId, newCheckOut);
                    break;
                case "3":
                    addOrRemoveBed(connect, statement);
                    break;
                case "4":
                    addOrRemoveMeals(connect, statement);
                    break;
                case "5":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Please  enter a number between 1-5");
                    break;
            }
        }
    }

    private void addOrRemoveMeals(Connection connect, PreparedStatement statement) {
        System.out.println("[1] to add half-board");
        System.out.println("[2] to add full-board");
        System.out.println("[3] do not add any meals");
        String fullOrHalfBoard = scanner.nextLine();
        String meal = null;
        switch (fullOrHalfBoard) {
            case "1":
                meal = "half-board";
                break;

            case "2":
                meal = "full-board";
                break;
            case "3":
                meal = "none";
                break;
            default:
                System.out.println("Please enter a number between 1-3");
                break;
        }
        executeUpdate(connect, statement, "UPDATE booked_rooms SET meals = ? WHERE booking_id =" + reservationId, meal);
    }

    private void addOrRemoveBed(Connection connect, PreparedStatement statement) {
        System.out.println("Add extra bed? 'Y' or 'N' ");
        String extraBed = scanner.nextLine();
        int trueOrFalse;
        if (extraBed.toLowerCase().equals("y")) {
            trueOrFalse = 1;
        } else {
            trueOrFalse = 0;
        }
        try {
            statement = connect.prepareStatement("UPDATE booked_rooms SET extra_bed = ? WHERE booking_id =" + reservationId);
            statement.setInt(1, trueOrFalse);
            statement.executeUpdate();
            System.out.println("updated successfully!");
        } catch (Exception e) {
            System.out.println("There was an error, try again");
        }
    }

    private void executeUpdate(Connection connect, PreparedStatement statement, String query, String setValue) {
        try {
            statement = connect.prepareStatement(query);
            statement.setString(1, setValue);
            statement.executeUpdate();
            System.out.println("updated successfully!");
        } catch (Exception e) {
            System.out.println("There was an error, try again");
        }
    }

    private void displayReservation(Connection connect, PreparedStatement statement, ResultSet resultSet) {

        guestSettingsHelper.findGuestBookings(connect, statement, resultSet);
        while (true) {
            try {
                System.out.println("Select reservation ID you wish to update ('N': to exit): ");
                String updateOrExit = scanner.nextLine();
                if (updateOrExit.toLowerCase().equals("n")) {
                    break;
                } else {
                    int updateReservation = Integer.parseInt(updateOrExit);
                    reservationId = updateReservation;
                    statement = connect.prepareStatement("SELECT * FROM guest_bookings WHERE booking_id = ? ");
                    statement.setInt(1, updateReservation);
                    resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String row = "Booking ID: " + resultSet.getString("booking_id")
                                + ", Order Issued: " + resultSet.getString("order_datetime")
                                + ", Check-In Date: " + resultSet.getString("checkin_date")
                                + ", Check-Out Date: " + resultSet.getString("checkout_date")
                                + ", Room Type: " + resultSet.getString("type")
                                + ", Extra Bed: " + resultSet.getString("extra_bed")
                                + ", Meals : " + resultSet.getString("meals")
                                + ", Room Price: " + resultSet.getString("room_price") + "SEK";
                        System.out.println(row);
                    }
                }
            } catch (Exception e) {
                System.out.println("There was an error, try again");
            }
        }
    }
}
