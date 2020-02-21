package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchFilter {
    private Scanner scanner = new Scanner(System.in);
    private String checkOutDate;
    private String checkInDate;
    private GuestSettingsHelper guestSettingsHelper = new GuestSettingsHelper();
    private ReservationHelper reservationHelper = new ReservationHelper();

    public void searchRoomsAndAccommodations(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
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
                statement.setInt(1, numOfPeople);
                statement.setString(2, reservationHelper.availableAsset("Pool included? 'Y': yes, 'N': no: "));
                statement.setString(3, reservationHelper.availableAsset("Evening events included? 'Y': yes, 'N': no:"));
                statement.setString(4, reservationHelper.availableAsset("Child activities included? 'Y': yes, 'N': no:"));
                statement.setString(5, reservationHelper.availableAsset("Restaurant included? 'Y': yes, 'N': no:"));
                statement.setDouble(6, reservationHelper.setDistance("Enter desired distance to beach in meters (hit enter to skip):"));
                statement.setDouble(7, reservationHelper.setDistance("Enter desired distance to centrum in meters(hit enter to skip): "));
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

    private void bookRoom(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        System.out.println("Enter ID for a room you wish to book:");
        String roomId = scanner.nextLine();
        registerBooking(connect, statement, resultSet);
        statement = connect.prepareStatement("INSERT INTO booked_rooms SET room_id = ?, booking_id = (SELECT MAX(id) FROM bookings), extra_bed = ?, meals = ?");
        statement.setString(1, roomId);
        statement.setInt(2, reservationHelper.addBed());
        statement.setString(3, reservationHelper.addMeal());
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
                guestSettingsHelper.registerUser(connect, statement);
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
}
