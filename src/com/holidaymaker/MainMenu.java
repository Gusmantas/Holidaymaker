package com.holidaymaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private GuestSettingsHelper guestSettingsHelper = new GuestSettingsHelper();
    private ReservationSettings reservationSettings = new ReservationSettings();
    private SearchFilter searchFilter = new SearchFilter();
    private ReservationHelper reservationHelper = new ReservationHelper();

    public MainMenu(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        menu(connect, statement, resultSet);
    }

    private void menu(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Welcome! Please, choose an option: ");
            System.out.println("[1] Register a user. ");
            System.out.println("[2] Delete a user. ");
            System.out.println("[3] Search rooms and make a reservation");
            System.out.println("[4] Change, delete or update a booking.");
            System.out.println("[5] See reservations ");
            System.out.println("[6] All Registered Users ");
            System.out.println("[7] Exit");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    guestSettingsHelper.registerUser(connect, statement, resultSet);
                    break;

                case "2":
                    guestSettingsHelper.deleteUser(connect, statement);
                    break;

                case "3":
                    searchMenu(connect, statement, resultSet);
                    break;

                case "4":
                    reservationOptions(connect, statement, resultSet);
                    break;

                case "5":
                    seeReservations(connect, statement, resultSet);
                    break;

                case "6":
                    guestSettingsHelper.printAllRegisteredUsers(connect, statement, resultSet);
                    break;


                case "7":
                    System.out.println("Bye!");
                    isRunning = false;
                    break;
            }
        }
    }

    private void seeReservations(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        boolean isMenuOn = true;
        while (isMenuOn) {
            System.out.println("[1] See all reservations");
            System.out.println("[2] See reservations by specific guest");
            System.out.println("[3] Exit to main menu");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    reservationHelper.getAllReservations(connect, statement, resultSet);
                    break;

                case "2":
                    guestSettingsHelper.findGuestBookings(connect, statement, resultSet);
                    break;

                case "3":
                    isMenuOn = false;
                    break;

                default:
                    System.out.println("Please enter a number between 1-3");
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
                    reservationSettings.deleteReservation(connect, statement, resultSet);
                    break;

                case "2":
                    reservationSettings.updateReservation(connect, statement, resultSet);
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

    private void searchMenu(Connection connect, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("[1] Begin search");
            System.out.println("[2] Exit");
            String beginSearchOrExit = scanner.nextLine();
            switch (beginSearchOrExit) {
                case "1":
                    searchFilter.searchRoomsAndAccommodations(connect, statement, resultSet);
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
}
