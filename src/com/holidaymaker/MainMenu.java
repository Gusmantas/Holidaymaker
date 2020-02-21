package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private ReservationHelper reservationHelper = new ReservationHelper();
    private ReservationSettings reservationSettings = new ReservationSettings();

    public MainMenu(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        menu(connect, statement, resultSet);
    }

    private void menu(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Welcome! Please, choose an option: ");
            System.out.println("[1] Register a user. ");
            System.out.println("[2] Delete a user. ");
            System.out.println("[3] Register a booking. ");
            System.out.println("[4] Change, delete or update a booking.");
            System.out.println("[5] Search rooms. ");
            System.out.println("[7] Find all rooms by reviews");
            System.out.println("[8] Exit");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    reservationHelper.registerUser(connect, statement);
                    break;

                case "2":
                    reservationHelper.deleteUser(connect, statement);
                    break;

                case "3":
                    new Reservation(connect, statement, resultSet);
                    break;

                case "4":
                    reservationOptions(connect,statement,resultSet);
                    break;

                case "5":
                    new SearchFilter(connect,statement,resultSet);
                    break;

                case "6":
                    break;

                case "7":
                    break;

                case "8":
                    System.out.println("Bye!");
                   isRunning = false;
                    break;
            }
        }
    }

    private void reservationOptions(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        boolean optionsIsRunning = true;
        while (optionsIsRunning) {
            System.out.println("[1] Delete Reservation");
            System.out.println("[2] Update Reservation");
            System.out.println("[3] Exit to Main Menu");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    reservationSettings.deleteReservation(connect,statement,resultSet);
                    break;

                case "2":
                    reservationSettings.updateReservation(connect,statement,resultSet);
                    break;

                case "3":
                    optionsIsRunning = false;
                    break;

                default:
                    System.out.println("Please enter a number between 1-3");
                    break;
            }
        }
    }
}
