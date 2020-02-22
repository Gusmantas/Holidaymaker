package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ReservationHelper {
    private Scanner scanner = new Scanner(System.in);
    private GuestSettingsHelper guestSettingsHelper = new GuestSettingsHelper();

    public Double setDistance(String enterDistance) { //Asks for specific distance to be set when searching rooms
        double desiredDistance;
        System.out.println(enterDistance);
        String distance = scanner.nextLine();
        if (!distance.equals("")) {
            return desiredDistance = Integer.parseInt(distance);
        }
        return desiredDistance = 99999999.9999;
    }

    public String availableAsset(String asset) { //Returns asset question and value when searching rooms
        System.out.println(asset);
        String userInput = scanner.nextLine();
        String yesNoOrNull;
        if (userInput.toLowerCase().equals("y")) {
            return yesNoOrNull = "yes";
        }
        return yesNoOrNull = "no";
    }

    public Integer addBed() {
        System.out.println("Extra assets:");
        System.out.println("Add extra bed? 'Y' or 'N' ");
        String extraBed = scanner.nextLine();
        int trueOrFalse;
        if (extraBed.toLowerCase().equals("y")) {
            return trueOrFalse = 1;
        }
        return trueOrFalse = 0;
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

    public String sortBy() {
        System.out.println("[1] Sort by room price (low-to-high");
        System.out.println("[2] Sort by accommodation ratings (high-to-low)");
        System.out.println("[3] Skip");
        String sortBy = scanner.nextLine();
        String query = "SELECT * FROM accommodations_with_reviews\n" +
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
                "AND ? NOT BETWEEN checkin AND checkout)\n";
        if (sortBy.equals("1")) {
            return query = query + "ORDER BY room_price ASC";
        } else if (sortBy.equals("2")) {
            return query = query + "ORDER BY stars DESC";
        }
        return query;
    }

    public void getAllReservations(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        try {
            statement = connect.prepareStatement(" SELECT * FROM guest_info_and_bookings");
            resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("There are no bookings in the system yet.");
            }
            while (resultSet.next()) {
                String row = " Reservation ID: " + resultSet.getString("booking_id") + "\n" +
                        " Reservation Date: " + resultSet.getString("order_datetime") + "\n" +
                        " Check-In Date: " + resultSet.getString("checkin_date") + "\n" +
                        " Check-Out Date: " + resultSet.getString("checkout_date") + "\n" +
                        " Room Type: " + resultSet.getString("type") + "\n" +
                        " Extra Bed: " + resultSet.getString("extra_bed") + "\n" +
                        " Meals: " + resultSet.getString("meals") + "\n" +
                        " Room Price: " + resultSet.getString("room_price") + "\n" +
                        " First name: " + resultSet.getString("first_name") + "\n" +
                        " Last name: " + resultSet.getString("last_name") + "\n" +
                        " Phone number: " + resultSet.getString("phone_number");
                System.out.println(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
