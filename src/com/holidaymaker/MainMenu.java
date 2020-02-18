package com.holidaymaker;

import com.sun.jdi.ClassNotPreparedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private User user = new User();

    public MainMenu(Connection connect, PreparedStatement statement) {
        menu(connect, statement);
    }

    private void menu(Connection connect, PreparedStatement statement) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Welcome! Please, choose an option: ");
            System.out.println("[1] Register a user. ");
            System.out.println("[2] Delete a user. ");
            System.out.println("[3] Register a booking. ");
            System.out.println("[4] Change, delete or update a booking.");
            System.out.println("[5] Search rooms. ");
            System.out.println("[6] Find all available rooms by price . ");
            System.out.println("[7] Find all rooms rooms by reviews");
            System.out.println("[8] Exit");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    user.registerUser(connect, statement);
                    break;

                case "2":
                    user.deleteUser(connect,statement);
                    break;

                case "3":
                    System.exit(0);
                    break;

                case "4":
                    break;

                case "5":
                    break;

                case "6":
                    break;

                case "7":
                    break;

                case "8":
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
            }
        }
    }
}
