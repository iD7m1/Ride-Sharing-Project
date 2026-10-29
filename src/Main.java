import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import datastructures.LinkedList;
import interfaces.IDateTime;
import interfaces.IRideSharingSystem;
import interfaces.VehicleType;
import model.DateTime;
import model.persons.Driver;
import model.persons.Rider;
import system.RideSharingSystem;

/**
 * Command-line menu of the ride-sharing system.
 * <p>
 * Shows the menu until the user chooses 0.
 * This is the only class that reads input or prints
 * output: every option calls one RideSharingSystem method and
 * prints what it returns.
 * </p>
 */
public class Main {

    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static final IRideSharingSystem system = new RideSharingSystem();

    public static void main (String[] args) {

    }

    private static void printMenu () {
        System.out.println("========== Ride-Sharing System ==========");
        System.out.println("1. List all riders");
        System.out.println("2. Search for riders by home city");
        System.out.println("3. Search for riders by name");
        System.out.println("4. Add a rider");
        System.out.println("5. List all drivers");
        System.out.println("6. Search for drivers by vehicle type");
        System.out.println("7. Add a driver");
        System.out.println("8. List all rides alphabetically by pickup location");
        System.out.println("9. Search for rides by pickup location");
        System.out.println("10. Search for rides by rider name");
        System.out.println("11. List the participants of a shared ride by pickup location");
        System.out.println("12. Add a ride");
        System.out.println("0. Exit");
        System.out.println("=========================================");
    }

    /**
     * Prints every element of a list on its own line using toString(),
     * followed by the number of results, or a message if the list is empty.
     *
     * @param list         the list to print
     * @param emptyMessage the message to print when the list is empty
     */
    private static <T> void printList (LinkedList<T> list, String emptyMessage) {
        if (list == null || list.empty()) {
            System.out.println(emptyMessage);
            return;
        }

        list.findFirst();
        while (true) {
            System.out.println(list.retrieve());
            if (list.last()) break;
            list.findNext();
        }
        System.out.printf("(%d result(s))\n", list.size());
    }

    /**
     * Option 4: reads a rider's information, creates the rider, and adds it.
     */
    private static void addRider () {
        int id = readInt("Enter rider ID: ");
        String name = readLine("Enter full name: ");
        String email = readLine("Enter email: ");
        String phone = readLine("Enter phone number (10 digits): ");
        String city = readLine("Enter home city: ");

        try {
            if (system.addRider(new Rider(id, name, phone, email, city)))
                System.out.println("Rider added successfully.");
            else
                System.out.println("Failed to add rider: a rider with this ID or email already exists.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add rider: " + e.getMessage());
        }
    }

    /**
     * Option 7: reads a driver's information, creates the driver, and adds it.
     */
    private static void addDriver () {
        int id = readInt("Enter driver ID: ");
        String name = readLine("Enter full name: ");
        String phone = readLine("Enter phone number (10 digits): ");
        String plate = readLine("Enter vehicle plate (e.g., ABC1234): ");
        VehicleType vehicleType = readVehicleType();

        try {
            if (system.addDriver(new Driver(id, name, phone, plate, vehicleType)))
                System.out.println("Driver added successfully.");
            else
                System.out.println("Failed to add driver: a driver with this ID or vehicle plate already exists.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add driver: " + e.getMessage());
        }
    }

    /**
     * Reads one line of input, trimmed. Exits the program if the input ends.
     *
     * @param prompt the message to show before reading
     * @return the line the user entered
     */
    private static String readLine (String prompt) {
        System.out.print(prompt);
        try {
            String line = input.readLine();
            if (line == null) {
                System.out.println();
                System.out.println("Input ended. Goodbye!");
                System.exit(0);
            }
            return line.trim();
        } catch (IOException e) {
            System.out.println("Could not read input. Goodbye!");
            System.exit(1);
            return "";
        }
    }

    /**
     * Reads a whole number, asking again until the input is valid.
     *
     * @param prompt the message to show before reading
     * @return the number the user entered
     */
    private static int readInt (String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    /**
     * Reads a vehicle type, asking again until it is one of the
     * VehicleType values. A space may be used instead of the underscore.
     *
     * @return the vehicle type the user entered
     */
    private static VehicleType readVehicleType () {
        while (true) {
            String text = readLine("Enter vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ");
            try {
                return VehicleType.valueOf(text.toUpperCase().replace(' ', '_'));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid vehicle type.");
            }
        }
    }

    /**
     * Reads a date/time in the format MM/DD/YYYY HH:MM, asking again
     * until the input is a valid date and time.
     *
     * @param prompt the message to show before reading
     * @return the date/time the user entered
     */
    private static IDateTime readDateTime (String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                String[] parts = text.split("\\s+");
                String[] date = parts[0].split("/");
                String[] time = parts[1].split(":");

                int month = Integer.parseInt(date[0]);
                int day = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);

                return new DateTime(year, month, day, hour, minute);
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid date/time. Use the format MM/DD/YYYY HH:MM (e.g., 03/15/2026 14:30).");
            }
        }
    }
}
