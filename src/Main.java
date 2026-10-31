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

    private static final BufferedReader input = new BufferedReader (new InputStreamReader(System.in));
    private static final IRideSharingSystem system = new RideSharingSystem ();

    public static void main (String[] args) {

            if (!system.loadDriversFromCSV("csv/drivers_30.csv"))
                if (!system.loadDriversFromCSV("../csv/drivers_30.csv"))
                    system.loadDriversFromCSV("drivers_30.csv");
            if (!system.loadRidersFromCSV("csv/riders_100.csv"))
                if (!system.loadRidersFromCSV("../csv/riders_100.csv"))
                    system.loadRidersFromCSV("riders_100.csv");
            if (!system.loadRidesFromCSV("csv/rides_40.csv"))
                if (!system.loadRidesFromCSV("../csv/rides_40.csv"))
                    system.loadRidesFromCSV("rides_40.csv");

            while (true) {
                printMenu();
                try {
                    int choose = readInt("➜ Enter your choice (0–12): ", "[!] Invalid input: Please enter a valid integer (0–12).");

                    switch (choose) {
                        case 0 -> {
                            System.out.println("Thank you for using the Ride-Sharing System. Goodbye!");
                            return;
                        }
                        case 1 -> {
                            System.out.println("\n══════════════════ [1] ALL REGISTERED RIDERS ══════════════════");
                            printList(system.getAllRiders(), "No riders found. There are currently no registered riders in the system.");
                        }
                        case 2 -> {
                            String city = readLine("Enter home city to search: ");
                            System.out.println("\n══════════════════ [2] SEARCH RIDERS BY HOME CITY ══════════════════");
                            printList(system.searchRidersByHomeCity(city), "No riders found in the specified home city.");
                        }
                        case 3 -> {
                            String name = readLine("Enter rider name to search: ");
                            System.out.println("\n══════════════════ [3] SEARCH RIDERS BY NAME ══════════════════");
                            printList(system.searchRidersByName(name), "No riders found matching the specified name.");
                        }
                        case 4 -> {
                            System.out.println("\n══════════════════ [4] ADD NEW RIDER ══════════════════");
                            addRider();
                        }
                        case 5 -> {
                            System.out.println("\n══════════════════ [5] ALL REGISTERED DRIVERS ══════════════════");
                            printList(system.getAllDrivers(), "No drivers found. There are currently no registered drivers in the system.");
                        }
                        case 6 -> {
                            VehicleType vehicleType = readVehicleType();
                            System.out.println("\n══════════════════ [6] SEARCH DRIVERS BY VEHICLE TYPE ══════════════════");
                            printList(system.searchDriversByVehicleType(vehicleType), "No drivers found with the specified vehicle type.");
                        }
                        case 7 -> {
                            System.out.println("\n══════════════════ [7] ADD NEW DRIVER ══════════════════");
                            addDriver();
                        }
                        case 8 -> {
                            System.out.println("\n══════════════════ [8] ALL RIDES BY PICKUP LOCATION ══════════════════");
                            printList(system.getAllRidesAlphabetically(), "No rides found. There are currently no scheduled rides in the system.");
                        }
                        case 9 -> {
                            String pickupLocation = readLine("Enter pickup location to search: ");
                            System.out.println("\n══════════════════ [9] SEARCH RIDES BY PICKUP LOCATION ══════════════════");
                            printList(system.searchRidesByPickupLocation(pickupLocation),"No rides found matching the specified pickup location.");
                        }
                        case 10 -> {
                            String name = readLine("Enter rider name to search: ");
                            System.out.println("\n══════════════════ [10] SEARCH RIDES BY RIDER NAME ══════════════════");
                            printList(system.searchRidesByRiderName(name),"No rides found for the specified rider name.");

                        }
                        case 11 -> {
                            String pickupLocation = readLine("Enter shared ride pickup location: ");
                            System.out.println("\n══════════════════ [11] SHARED RIDE PARTICIPANTS BY PICKUP LOCATION ══════════════════");
                            printList(system.getSharedRideParticipants(pickupLocation),"No participants found for shared rides at the specified pickup location.");
                        }
                        case 12 -> {
                            System.out.println("\n══════════════════ [12] SCHEDULE A NEW RIDE ══════════════════");
                            addRide();
                        }
                        default -> System.out.println("  [!] Invalid choice: Please enter a number between 0 and 12.");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    private static void printMenu () {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   RIDE-SHARING MANAGEMENT SYSTEM                     ║");
        System.out.println("║                        Main Control Panel                            ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ RIDER MANAGEMENT                                                     ║");
        System.out.println("║──────────────────────────────────────────────────────────────────────║");
        System.out.println("║  [1]  List all riders                                                ║");
        System.out.println("║  [2]  Search riders by home city                                     ║");
        System.out.println("║  [3]  Search riders by name                                          ║");
        System.out.println("║  [4]  Add a new rider                                                ║");
        System.out.println("║──────────────────────────────────────────────────────────────────────║");
        System.out.println("║ DRIVER MANAGEMENT                                                    ║");
        System.out.println("║──────────────────────────────────────────────────────────────────────║");
        System.out.println("║  [5]  List all drivers                                               ║");
        System.out.println("║  [6]  Search drivers by vehicle type                                 ║");
        System.out.println("║  [7]  Add a new driver                                               ║");
        System.out.println("║──────────────────────────────────────────────────────────────────────║");
        System.out.println("║ RIDE MANAGEMENT                                                      ║");
        System.out.println("║──────────────────────────────────────────────────────────────────────║");
        System.out.println("║  [8]  List all rides alphabetically by pickup location               ║");
        System.out.println("║  [9]  Search rides by pickup location                                ║");
        System.out.println("║  [10] Search rides by rider name                                     ║");
        System.out.println("║  [11] List shared ride participants by pickup location               ║");
        System.out.println("║  [12] Schedule a new ride                                            ║");
        System.out.println("║                                                                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  [0]  Exit Application                                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

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

        int i = 1;

        list.findFirst();
        while (true) {
            System.out.printf("[%02d] - %s%n", i++, list.retrieve());
            if (list.last()) break;
            list.findNext();
        }
        System.out.printf("%d %s found", list.size(), list.size() == 1 ? "result" : "results");
    }

    /**
     * Option 4: reads a rider's information, creates the rider, and adds it.
     */
    private static void addRider () {
        int id = readInt("Enter rider ID: ", "Please enter a valid whole number.");
        String name = readLine("Enter full name: ");
        String email = readLine("Enter email: ");
        String phone = readLine("Enter phone number (10 digits): ");
        String city = readLine("Enter home city: ");

        try {
            if (system.addRider(new Rider(id, name, phone, email, city)))
                System.out.println("[+] Rider added successfully.");
            else
                System.out.println("[!] Failed to add rider: A rider with this ID or email already exists.");
        } catch (IllegalArgumentException e) {
            System.out.println("[!] Failed to add rider: " + e.getMessage());
        }
    }

    /**
     * Option 7: reads a driver's information, creates the driver, and adds it.
     */
    private static void addDriver () {
        int id = readInt("Enter driver ID: ", "Please enter a valid whole number.");
        String name = readLine("Enter full name: ");
        String phone = readLine("Enter phone number (10 digits): ");
        String plate = readLine("Enter vehicle plate (e.g., ABC1234): ");
        VehicleType vehicleType = readVehicleType();

        try {
            if (system.addDriver(new Driver(id, name, phone, plate, vehicleType)))
                System.out.println("[+] Driver added successfully.");
            else
                System.out.println("[!] Failed to add driver: a driver with this ID or vehicle plate already exists.");
        } catch (IllegalArgumentException e) {
            System.out.println("[!] Failed to add driver: " + e.getMessage());
        }
    }

    private static void addRide () {
        System.out.println("Select the ride type: ");
        System.out.println("[1] Private Ride");
        System.out.println("[2] Shared Ride");

        int rideType;

        while (true) {
            rideType = readInt("Enter ride type (1–2): ", "[!] Invalid input. Please enter a valid integer.");

            if (rideType == 1 || rideType == 2) break;

            System.out.println("[!] Invalid ride type. Please enter 1 for Private or 2 for Shared.");
        }

        if (rideType == 1)
            System.out.println("\n────────────────── PRIVATE RIDE DETAILS ──────────────────");
        else
            System.out.println("\n────────────────── SHARED RIDE DETAILS ──────────────────");


        String pickupLocation = readLine("Enter pickup location: ");
        IDateTime pickupTime = readDateTime("Enter pickup date and time (MM/DD/YYYY HH:MM): ");
        String dropOffLocation = readLine("Enter drop-off location: ");
        IDateTime dropOffTime = readDateTime("Enter drop-off date and time (MM/DD/YYYY HH:MM): ");
        int driverId = readInt("Enter driver ID: ", "Please enter a valid whole number");

        if (rideType == 1) {

            int riderId = readInt("Enter rider ID: ", "Please enter a valid whole number");

            try {

                if (system.schedulePrivateRide(pickupLocation,pickupTime,dropOffTime,dropOffLocation,riderId,driverId))
                    System.out.println("[+] Private ride scheduled successfully.");
                else
                    System.out.println("[!] Failed to schedule private ride. Please verify the rider, driver, and ride details.");

            } catch (IllegalArgumentException e) {
                System.out.println("[!] Failed to schedule private ride: " + e.getMessage());
            }

        } else {
            int numberOfParticipants;

            while (true) {
                numberOfParticipants = readInt("Enter the number of participants: ","Please enter a valid whole number");

                if (numberOfParticipants >= 2) break;

                System.out.println("[!] Invalid number of participants. Please enter at least 2 participants.");
            }

            int[] riderIds = new int[numberOfParticipants];

            for (int i = 0; i < numberOfParticipants; i++) {
                riderIds[i] = readInt(String.format("Enter rider ID for participant (%d): ", i + 1),"Please enter a valid whole number");
            }

            try {

                if (system.scheduleSharedRide(pickupLocation,pickupTime,dropOffTime,dropOffLocation,riderIds,driverId))
                    System.out.println("[+] Shared ride scheduled successfully.");
                else
                    System.out.println("[!] Failed to schedule shared ride. Please verify the participants, driver, and ride details.");

            } catch (IllegalArgumentException e) {
                System.out.println("[!] Failed to schedule shared ride: " + e.getMessage());
            }
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
    private static int readInt (String prompt, String invalidMessage) {
        while (true) {
            String text = readLine(prompt);
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println(invalidMessage);
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
                return VehicleType.valueOf(text.toUpperCase().replaceAll("\\s+", "_"));
            } catch (IllegalArgumentException e) {
                System.out.println("[!] Invalid vehicle type. Please choose from the listed options.");
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
                if (parts.length != 2) throw new IllegalArgumentException();

                String[] date = parts[0].split("/",-1);
                String[] time = parts[1].split(":",-1);
                if (date.length != 3 || time.length != 2) throw new IllegalArgumentException();

                int month = Integer.parseInt(date[0]);
                int day = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);

                return new DateTime(year, month, day, hour, minute);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date/time. Use the format MM/DD/YYYY HH:MM (e.g., 03/15/2026 14:30).");
            }
        }
    }
}
