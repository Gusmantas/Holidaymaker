package com.holidaymaker;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SearchFilter {
    private Scanner scanner = new Scanner(System.in);

    public SearchFilter(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        searchMenu(connect,statement,resultSet);
    }

    public void searchMenu(Connection connect, PreparedStatement statement, ResultSet resultSet) {
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

    private String availableAsset(String asset) {
        System.out.println(asset);
        String userInput = scanner.nextLine();
        String yesNoOrNull;
        if (userInput.toLowerCase().equals("y")) {
            return yesNoOrNull = "yes";
        } else if (userInput.toLowerCase().equals("n")) {
            return yesNoOrNull = "no";
        }
        return yesNoOrNull = null;
    }


    private void searchRoomsAndAccommodations(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        System.out.println("Enter number of people: ");
        int numOfPeople = Integer.parseInt(scanner.nextLine());
        String poolAvailable = availableAsset("Pool included? 'Y' or 'N' ");
        String eveningEventsAvailable = availableAsset("Evening events included? 'Y': yes, 'N': no, 'D': Doesn't matter :");
        String childActivitiesAvailable = availableAsset("Child activities included? 'Y': yes, 'N': no, 'D': Doesn't matter :");
        String restaurantAvailable = availableAsset("Restaurant included? 'Y': yes, 'N': no, 'D': Doesn't matter :");
        System.out.println("Enter desired distance to beach:");
        double desiredDistanceToBeach = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter desired distance to centrum: ");
        double desiredDistanceToCentrum = Double.parseDouble(scanner.nextLine());


        try {
            String query = "SELECT DISTINCT * FROM filtered_accommodations_and_booked_rooms_info\n" +
                    "WHERE max_persons_per_room = ?\n" +
                    "AND pool = ?\n" +
                    "AND evening_events = ?\n" +
                    "AND child_activities = ?\n" +
                    "AND restaurant = ?\n" +
                    "AND distance_to_beach <= ?\n" +
                    "AND distance_to_centrum <= ?\n" +
                    "GROUP BY room_price";

            statement = connect.prepareStatement(query);
            statement.setInt(1, numOfPeople);
            statement.setString(2, poolAvailable);
            statement.setString(3, eveningEventsAvailable);
            statement.setString(4, childActivitiesAvailable);
            statement.setString(5, restaurantAvailable);
            statement.setDouble(6, desiredDistanceToBeach);
            statement.setDouble(7, desiredDistanceToCentrum);
            resultSet = statement.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
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
            System.out.println("All rooms are sorted by low-to-high price.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
