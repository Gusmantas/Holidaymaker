package com.holidaymaker;

import java.util.Scanner;

public class ReservationHelper {
    private Scanner scanner = new Scanner(System.in);
    private GuestSettingsHelper guestSettingsHelper = new GuestSettingsHelper();



    public Double setDistance(String enterDistance) { //Asks for specific distance to be set when searching rooms
        double desiredDistance;
        System.out.println(enterDistance);
        String distance = scanner.nextLine();
        if(!distance.equals("")) {
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


}
