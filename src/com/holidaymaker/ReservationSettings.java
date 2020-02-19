package com.holidaymaker;

import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ReservationSettings {
    Scanner scanner = new Scanner(System.in);


    public void deleteReservation(Connection connect, PreparedStatement statement, ResultSet resultSet) {
        System.out.println("Enter guest's first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter guest's phone number: ");
        String phoneNumber = scanner.nextLine();
        try {
            statement = connect.prepareStatement("SELECT * FROM guest_bookings WHERE guest_id = (SELECT id FROM guests WHERE first_name = ? AND phone_number = ?)");
            statement.setString(1, firstName);
            statement.setString(2, phoneNumber);
            resultSet = statement.executeQuery();
            try {
                while (resultSet.next()) {
                    String row = "Booking ID: " + resultSet.getString("booking_id")
                            + ", Order Date: " + resultSet.getString("order_datetime")
                            + ", Check-In Date: " + resultSet.getString("checkin_date")
                            + ", Check-Out Date: " + resultSet.getString("checkout_date")
                            + ", Room Type: " + resultSet.getString("type")
                            + ", Room Price: " + resultSet.getString("room_price");
                    System.out.println(row);
                    System.out.println("------------------------------------------------------------");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Select reservation ID you wish to remove: ");
        int removeReservation = Integer.parseInt(scanner.nextLine());
        try {
            statement = connect.prepareStatement("DELETE FROM bookings WHERE id = ?");
            statement.setInt(1, removeReservation);
            statement.executeUpdate();
            System.out.println("Reservation successfully removed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
