package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class SearchFilter {
    private Scanner scanner = new Scanner(System.in);
    private String checkOutDate;
    private String checkInDate;
    private ReservationHelper reservationHelper = new ReservationHelper();

    public SearchFilter(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        searchMenu(connect, statement, resultSet);
    }

    private void searchMenu(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("[1] Begin search");
            System.out.println("[2] Exit");
            String beginSearchOrExit = scanner.nextLine();
            switch (beginSearchOrExit) {
                case "1":
                    searchRoomsAndAccommodations(connect, statement, resultSet);
                    break;
                case "2":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Please enter a number between 1-2");
                    break;
            }
        }
    }

    private void searchRoomsAndAccommodations(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Enter check-in date and time (yyyy-mm-dd hh:mm):  ");
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

            try {
                String query = "SELECT DISTINCT * FROM filtered_accommodations_and_booked_rooms_info\n" +
                        "WHERE max_persons_per_room = ?\n" +
                        "AND pool = ?\n" +
                        "AND evening_events = ?\n" +
                        "AND child_activities = ?\n" +
                        "AND restaurant = ?\n" +
                        "AND distance_to_beach <= ?\n" +
                        "AND distance_to_centrum <= ?\n" +
                        "AND room_id  IN(SELECT id FROM all_rooms\n" +
                        "WHERE checkin IS NULL OR checkout IS NULL \n" +
                        "OR checkin  NOT BETWEEN ? AND ? \n" +
                        "AND checkout NOT BETWEEN ? AND ?\n" +
                        "AND ? NOT BETWEEN checkin AND checkout\n" +
                        "AND ? NOT BETWEEN checkin AND checkout)\n" +
                        "GROUP BY room_price;";

                statement = connect.prepareStatement(query);
                statement.setInt(1, setNumOfPeople());
                statement.setString(2, availableAsset("Pool included? 'Y': yes, 'N': no, 'D': Doesn't matter : "));
                statement.setString(3, availableAsset("Evening events included? 'Y': yes, 'N': no, 'D': Doesn't matter :"));
                statement.setString(4, availableAsset("Child activities included? 'Y': yes, 'N': no, 'D': Doesn't matter :"));
                statement.setString(5, availableAsset("Restaurant included? 'Y': yes, 'N': no, 'D': Doesn't matter :"));
                statement.setDouble(6, setDistance("Enter desired distance to beach in meters (hit enter to skip):"));
                statement.setDouble(7, setDistance("Enter desired distance to centrum in meters(hit enter to skip): "));
                statement.setString(8, checkInDate);
                statement.setString(9, checkOutDate);
                statement.setString(10, checkInDate);
                statement.setString(11, checkOutDate);
                statement.setString(12, checkInDate);
                statement.setString(13, checkOutDate);
                resultSet = statement.executeQuery();
                printRoomInformation(connect, statement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bookRoom(connect, statement, resultSet);
            isRunning = false;
        }
    }

    private Double setDistance(String enterDistance) {
        double desiredDistance;
        System.out.println(enterDistance);
        String distance = scanner.nextLine();
        if(!distance.equals("")) {
            return desiredDistance = Integer.parseInt(distance);
        }
        return desiredDistance = 99999999.9999;
    }

    private Integer setNumOfPeople() {
        int numOfPeople;
        System.out.println("Enter number of people (there are rooms up to 9 persons): ");
        return numOfPeople = Integer.parseInt(scanner.nextLine());
    }

    private String availableAsset(String asset) {
        System.out.println(asset);
        String userInput = scanner.nextLine();
        String yesNoOrNull;
        if (userInput.toLowerCase().equals("y")) {
            return yesNoOrNull = "yes";
        } else if (userInput.toLowerCase().equals("n")) {
            return yesNoOrNull = "no";
        }
        return null;
    }


    private void bookRoom(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        System.out.println("Enter ID for a room you wish to book:");
        String roomId = scanner.nextLine();
        registerBooking(connect, statement, resultSet);
        statement = connect.prepareStatement("INSERT INTO booked_rooms SET room_id = ?, booking_id = (SELECT MAX(id) FROM bookings), extra_bed = ?, meals = ?");
        statement.setString(1, roomId);
        statement.setInt(2, addBed());
        statement.setString(3, addMeal());
        statement.executeUpdate();
        System.out.println("Reservation successful! ");
    }

    private void printRoomInformation(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (!resultSet.isBeforeFirst()) {
                System.out.println("There are no rooms available. Search again: ");
                searchRoomsAndAccommodations(connect, statement, resultSet);
            }
            while (resultSet.next()) {
                String row =
                        "__________________________________________________________________________________________\n" +
                                " Accommodation ID: " + resultSet.getString("accommodation_id") + " \n"
                                + " Street: " + resultSet.getString("street") + " \n"
                                + " City: " + resultSet.getString("city") + "\n"
                                + " Country: " + resultSet.getString("country") + "\n"
                                + " Extra Bed Price: " + resultSet.getString("extra_bed_price") + "\n"
                                + " Half-Board Price: " + resultSet.getString("half_board_price") + "\n"
                                + " Full-Board Price: " + resultSet.getString("full_board_price") + "\n"
                                + " Pool: " + resultSet.getString("pool") + "\n"
                                + " Evening Events: " + resultSet.getString("evening_events") + "\n"
                                + " Child Activities: " + resultSet.getString("child_activities") + "\n"
                                + " Restaurant: " + resultSet.getString("restaurant") + "\n"
                                + " Distance To Beach: " + resultSet.getString("distance_to_beach") + "\n"
                                + " Distance To Centrum: " + resultSet.getString("distance_to_centrum") + "\n"
                                + " Room ID: " + resultSet.getString("room_id") + "\n"
                                + " Room Type: " + resultSet.getString("room_type") + "\n"
                                + " Max Persons Per Room: " + resultSet.getString("max_persons_per_room") + "\n"
                                + " Room Price: " + resultSet.getString("room_price") + "\n"
                                + " Room Description: " + resultSet.getString("room_description") + "\n"
                                + " Room Booked (Check-In Date): " + resultSet.getString("room_booked_chekin") + "\n"
                                + " Room Booked (Check-Out Date): " + resultSet.getString("room_booked_checkout") + "\n"
                                + "______________________________________________________________________________________";
                System.out.println(row);
            }
        } catch (Exception e) {
            System.out.println("");
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
        return trueOrFalse = 0;
    }

    public void registerBooking(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        System.out.println("To make a reservation, enter client first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter client phone number: ");
        String phoneNumber = scanner.nextLine();
        try {
            statement = connect.prepareStatement("SELECT id FROM guests WHERE first_name = ? AND phone_number = ?");
            statement.setString(1, firstName);
            statement.setString(2, phoneNumber);
            resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Guest was not found. Register guest: ");
                reservationHelper.registerUser(connect, statement);
            } else {
                String insertBooking = "INSERT INTO bookings " +
                        "SET guest_id = (SELECT id FROM guests WHERE first_name = ? AND phone_number = ?)," +
                        " checkin_date = ?," +
                        " checkout_date = ?, " +
                        "order_datetime = NOW()";
                statement = connect.prepareStatement(insertBooking);
                statement.setString(1, firstName);
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
                default:
                    System.out.println("Wrong input. Enter either '1' or '2'");
                    break;
            }
        }
        return meal = "none";
    }
}
