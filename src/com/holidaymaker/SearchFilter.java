package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class SearchFilter {
    private Scanner scanner = new Scanner(System.in);
    private String checkOutDate;
    private String checkInDate;
    private GuestSettingsHelper guestSettingsHelper = new GuestSettingsHelper();
    private ReservationHelper reservationHelper = new ReservationHelper();
    private double bedPrice;
    private double halfBoardPrice;
    private double fullBoardPrice;
    private double roomPrice;

    public void searchRoomsAndAccommodations(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("Enter check-in date and time (yyyy-mm-dd hh:mm):");
                String checkIn = scanner.nextLine();
                if (checkIn.compareTo("2020-05-31 23:59:59") <= 0) {
                    System.out.println("Reservations are available between 2020.06.01 00:00 - 2020-07-31 00:00");
                    continue;
                } else {
                    checkInDate = checkIn;
                }
                System.out.println("Enter check-out date and time (yyyy-mm-dd hh:mm):  ");
                String checkOut = scanner.nextLine();
                if (checkOut.compareTo("2020-08-01 00:00:01") >= 0) {
                    System.out.println("Reservations are available between 2020.06.01 00:00:01 - 2020-07-31 00:00:00");
                    continue;
                } else {
                    checkOutDate = checkOut;
                }
                System.out.println("Enter number of people (there are rooms up to 9 persons): ");
                int people = Integer.parseInt(scanner.nextLine());
                int numOfPeople;
                if (people <= 9) {
                    numOfPeople = people;
                } else {
                    System.out.println("Wrong input. Available number of persons is between 1-9");
                    continue;
                }
                String pool = reservationHelper.availableAsset("Pool included? 'Y': yes, 'N': no: ");
                String evening_events = reservationHelper.availableAsset("Evening events included? 'Y': yes, 'N': no:");
                String child_activities = reservationHelper.availableAsset("Child activities included? 'Y': yes, 'N': no:");
                String restaurant = reservationHelper.availableAsset("Restaurant included? 'Y': yes, 'N': no:");
                Double distance_to_beach = reservationHelper.setDistance("Enter desired distance to beach in meters (hit enter to skip):");
                Double distance_to_centrum = reservationHelper.setDistance("Enter desired distance to centrum in meters(hit enter to skip): ");

                statement = connect.prepareStatement(reservationHelper.sortBy());
                statement.setInt(1, numOfPeople);
                statement.setString(2, pool);
                statement.setString(3, evening_events);
                statement.setString(4, child_activities);
                statement.setString(5, restaurant);
                statement.setDouble(6, distance_to_beach);
                statement.setDouble(7, distance_to_centrum);
                statement.setString(8, checkInDate);
                statement.setString(9, checkOutDate);
                statement.setString(10, checkInDate);
                statement.setString(11, checkOutDate);
                statement.setString(12, checkInDate);
                statement.setString(13, checkOutDate);
                resultSet = statement.executeQuery();
                reservationHelper.printRoomInformation(connect, statement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred, please try again.");
            }
            System.out.println("Do you wish to proceed with this order? <ENTER>: yes, 'N': no");
            String proceedWithOrder = scanner.nextLine();
            if (proceedWithOrder.toLowerCase().equals("n")) {
                isRunning = false;
            } else {
                bookRoom(connect, statement, resultSet);
                isRunning = false;
            }
        }
    }

    private Integer addBed() {
        System.out.println("Extra assets:");
        System.out.println("Add extra bed? 'Y' or 'N' ");
        String extraBed = scanner.nextLine();
        int trueOrFalse;
        if (extraBed.toLowerCase().equals("y")) {
            return trueOrFalse = 1;
        }
        bedPrice = 0;
        return trueOrFalse = 0;
    }

    private void bookRoom(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        System.out.println("Enter ID for a room you wish to book:");
        String roomId = scanner.nextLine();
        registerBooking(connect, statement, resultSet);
        getPrices(connect, statement, resultSet, roomId);
        statement = connect.prepareStatement("INSERT INTO booked_rooms SET room_id = ?, booking_id = (SELECT MAX(id) FROM bookings), extra_bed = ?, meals = ?");
        statement.setString(1, roomId);
        statement.setInt(2, addBed());
        statement.setString(3, addMeal());
        statement.executeUpdate();
        LocalDateTime checkIn = LocalDateTime.parse(checkInDate, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
        LocalDateTime checkout = LocalDateTime.parse(checkOutDate, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
        double numOfDays = ChronoUnit.DAYS.between(checkIn, checkout);
        double totalSum = (bedPrice + halfBoardPrice + fullBoardPrice + roomPrice) * numOfDays;
        System.out.printf("Total price: %.2f \n", totalSum);
        System.out.println("Reservation successful! ");
        bedPrice = 0;
        halfBoardPrice = 0;
        fullBoardPrice = 0;
        roomPrice = 0;
    }

    private void getPrices(Connection connect, PreparedStatement statement, ResultSet resultSet, String roomId) throws SQLException {
        statement = connect.prepareStatement("SELECT extra_bed_price, half_board_price, full_board_price, room_price FROM full_accommodation_and_rooms_info WHERE room = ?");
        statement.setString(1, roomId);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            bedPrice = Double.parseDouble(resultSet.getString("extra_bed_price"));
            roomPrice = Double.parseDouble(resultSet.getString("room_price"));
            halfBoardPrice = Double.parseDouble(resultSet.getString("half_board_price"));
            fullBoardPrice = Double.parseDouble(resultSet.getString("full_board_price"));
        }
    }

    private String addMeal() {
        System.out.println("Add meals? 'Y' or 'N'");
        String addMeals = scanner.nextLine();
        String meal = null;
        if (addMeals.toLowerCase().equals("y")) {
            System.out.println("[1] to add half-board");
            System.out.println("[2] to add full-board");
            String fullOrHalfBoard = scanner.nextLine();
            switch (fullOrHalfBoard) {
                case "1":
                    fullBoardPrice = 0;
                    return meal = "half-board";
                case "2":
                    halfBoardPrice = 0;
                    return meal = "full-board";
                default:
                    System.out.println("Wrong input. Enter either '1' or '2'");
                    break;
            }
        }
        fullBoardPrice = 0;
        halfBoardPrice = 0;
        return meal = "none";
    }

    private void registerBooking(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        System.out.println("To make a reservation, enter client first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter client phone number: ");
        String phoneNumber = scanner.nextLine();
        try {
            statement = connect.prepareStatement("SELECT id FROM guests WHERE first_name = ? AND phone_number = ?");
            statement.setString(1, firstName.toLowerCase());
            statement.setString(2, phoneNumber);
            resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Guest was not found. Register guest: ");
                guestSettingsHelper.registerUser(connect, statement, resultSet);
                searchRoomsAndAccommodations(connect, statement, resultSet);
            } else {
                String insertBooking = "INSERT INTO bookings " +
                        "SET guest_id = (SELECT id FROM guests WHERE first_name = ? AND phone_number = ?)," +
                        " checkin_date = ?," +
                        " checkout_date = ?, " +
                        "order_datetime = NOW()";
                statement = connect.prepareStatement(insertBooking);
                statement.setString(1, firstName.toLowerCase());
                statement.setString(2, phoneNumber);
                statement.setString(3, checkInDate);
                statement.setString(4, checkOutDate);
                statement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("There was an error, try again.");
        }
    }
}
