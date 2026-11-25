import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import datastructures.LinkedList;
import interfaces.IDateTime;
import interfaces.IRideSharingSystem;
import interfaces.ITimeSlot;
import interfaces.VehicleType;
import model.DateTime;
import model.TimeSlot;
import model.persons.Rider;
import system.RideSharingSystem;

/**
 * Command-line menu of the ride-sharing system (Phase 2).
 * <p>
 * Loads riders_100.csv, drivers_30.csv, and rides_40.csv in order,
 * then shows the menu (options 1-12 and 0 to exit).
 * This is the only class that reads input or prints output.
 * </p>
 */
public class Main {

    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static final IRideSharingSystem system = new RideSharingSystem();

    public static void main(String[] args) {
        int ridersLoaded = loadWithFallback("riders_100.csv", "riders");
        int driversLoaded = loadWithFallback("drivers_30.csv", "drivers");
        int ridesLoaded = loadWithFallback("rides_40.csv", "rides");

        System.out.println("Loaded " + ridersLoaded + " riders.");
        System.out.println("Loaded " + driversLoaded + " drivers.");
        System.out.println("Loaded " + ridesLoaded + " rides.");

        while (true) {
            printMenu();
            try {
                int choose = readInt("-> Enter your choice (0-12): ", "[!] Invalid input: Please enter a valid integer (0-12).");

                switch (choose) {
                    case 0 -> {
                        System.out.println("Thank you for using the Ride-Sharing System. Goodbye!");
                        return;
                    }
                    case 1 -> {
                        System.out.println("\n[1] ALL REGISTERED RIDERS");
                        printList(system.getAllRiders(), "No results.");
                    }
                    case 2 -> {
                        // TODO: Search for a rider by ID, and show the number of key comparisons
                        System.out.println("[TODO] Option 2 not implemented yet.");
                    }
                    case 3 -> {
                        String city = readLine("Enter home city to search: ");
                        System.out.println("\n[3] SEARCH RIDERS BY HOME CITY");
                        printList(system.searchRidersByHomeCity(city), "No results.");
                    }
                    case 4 -> {
                        System.out.println("\n[4] ALL RIDES ALPHABETICALLY BY PICKUP LOCATION");
                        printList(system.getAllRidesAlphabetically(), "No results.");
                    }
                    case 5 -> {
                        // TODO: Show the schedule of a rider or a driver (ask which, then the ID), in time order
                        System.out.println("[TODO] Option 5 not implemented yet.");
                    }
                    case 6 -> {
                        // TODO: Show size, height, and ceil(log2(n + 1)) of each of the nine indexes
                        System.out.println("[TODO] Option 6 not implemented yet.");
                    }
                    case 7 -> {
                        System.out.println("\n[7] ADD A RIDER");
                        addRider();
                    }
                    case 8 -> {
                        // TODO: List rides whose pickup time falls in a given range
                        System.out.println("[TODO] Option 8 not implemented yet.");
                    }
                    case 9 -> {
                        // TODO: Find available drivers of a given vehicle type for a given time slot
                        System.out.println("[TODO] Option 9 not implemented yet.");
                    }
                    case 10 -> {
                        System.out.println("\n[10] ADD A RIDE");
                        addRide();
                    }
                    case 11 -> {
                        // TODO: Cancel a ride (ask for the ride ID)
                        System.out.println("[TODO] Option 11 not implemented yet.");
                    }
                    case 12 -> {
                        // TODO: Remove a rider (and cascade to their rides)
                        System.out.println("[TODO] Option 12 not implemented yet.");
                    }
                    default -> System.out.println("[!] Invalid choice: Please enter a number between 0 and 12.");
                }
            } catch (Exception e) {
                System.out.println("[!] Error: " + e.getMessage());
            }
        }
    }

    private static int loadWithFallback(String fileName, String type) {
        String[] paths = { "csv/" + fileName, "../csv/" + fileName, fileName };
        for (String path : paths) {
            int count = switch (type) {
                case "riders" -> system.loadRidersFromCSV(path);
                case "drivers" -> system.loadDriversFromCSV(path);
                default -> system.loadRidesFromCSV(path);
            };
            if (count != -1) return count;
        }
        return -1;
    }

    private static void printMenu() {
        // TODO: format Phase 2 menu as desired (options 1-12 and 0)
        System.out.println();
        System.out.println("=============== RIDE-SHARING SYSTEM (PHASE 2) ===============");
        System.out.println(" 1. List all riders.");
        System.out.println(" 2. Search for a rider by ID (with key comparisons).");
        System.out.println(" 3. Search for riders by home city.");
        System.out.println(" 4. List all rides alphabetically by pickup location.");
        System.out.println(" 5. Show the schedule of a rider or a driver.");
        System.out.println(" 6. Show size, height, and min height of each index.");
        System.out.println(" 7. Add a rider.");
        System.out.println(" 8. List rides whose pickup time falls in a given range.");
        System.out.println(" 9. Find available drivers for a vehicle type and time slot.");
        System.out.println("10. Add a ride.");
        System.out.println("11. Cancel a ride.");
        System.out.println("12. Remove a rider (and cascade to their rides).");
        System.out.println(" 0. Exit the program.");
        System.out.println("=============================================================");
    }

    private static <T> void printList(LinkedList<T> list, String emptyMessage) {
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
        System.out.printf("%d %s found%n", list.size(), list.size() == 1 ? "result" : "results");
    }

    private static void addRider() {
        int id = readInt("Enter rider ID: ", "Please enter a valid whole number.");
        String name = readLine("Enter full name: ");
        String email = readLine("Enter email: ");
        String phone = readLine("Enter phone number (10 digits): ");
        String city = readLine("Enter home city: ");

        try {
            if (system.addRider(new Rider(id, name, email, phone, city)))
                System.out.println("[+] Rider added successfully.");
            else
                System.out.println("[!] Failed to add rider: A rider with this ID already exists.");
        } catch (IllegalArgumentException e) {
            System.out.println("[!] Failed to add rider: " + e.getMessage());
        }
    }

    private static void addRide() {
        System.out.println("Select the ride type:");
        System.out.println("[1] Private Ride");
        System.out.println("[2] Shared Ride");

        int rideType;
        while (true) {
            rideType = readInt("Enter ride type (1-2): ", "[!] Invalid input. Please enter a valid integer.");
            if (rideType == 1 || rideType == 2) break;
            System.out.println("[!] Invalid ride type. Please enter 1 for Private or 2 for Shared.");
        }

        String pickupLocation = readLine("Enter pickup location: ");
        IDateTime pickupTime = readDateTime("Enter pickup date and time (MM/DD/YYYY HH:MM): ");
        String dropOffLocation = readLine("Enter drop-off location: ");
        IDateTime dropOffTime = readDateTime("Enter drop-off date and time (MM/DD/YYYY HH:MM): ");
        int driverId = readInt("Enter driver ID: ", "Please enter a valid whole number.");

        try {
            ITimeSlot timeSlot = new TimeSlot(pickupTime, dropOffTime);

            if (rideType == 1) {
                int riderId = readInt("Enter rider ID: ", "Please enter a valid whole number.");
                int rideId = system.schedulePrivateRide(pickupLocation, dropOffLocation, timeSlot, riderId, driverId);
                if (rideId != -1)
                    System.out.println("[+] Private ride scheduled successfully. Ride ID: " + rideId);
                else
                    System.out.println("[!] Failed to schedule private ride.");
            } else {
                int numberOfParticipants;
                while (true) {
                    numberOfParticipants = readInt("Enter the number of participants: ", "Please enter a valid whole number.");
                    if (numberOfParticipants >= 2) break;
                    System.out.println("[!] Please enter at least 2 participants.");
                }

                int[] riderIds = new int[numberOfParticipants];
                for (int i = 0; i < numberOfParticipants; i++) {
                    riderIds[i] = readInt(String.format("Enter rider ID for participant (%d): ", i + 1), "Please enter a valid whole number.");
                }

                int rideId = system.scheduleSharedRide(pickupLocation, dropOffLocation, timeSlot, riderIds, driverId);
                if (rideId != -1)
                    System.out.println("[+] Shared ride scheduled successfully. Ride ID: " + rideId);
                else
                    System.out.println("[!] Failed to schedule shared ride.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[!] Failed to schedule ride: " + e.getMessage());
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        try {
            String line = input.readLine();
            if (line == null) {
                System.out.println("\nInput ended. Goodbye!");
                System.exit(0);
            }
            return line.trim();
        } catch (IOException e) {
            System.out.println("Could not read input. Goodbye!");
            System.exit(1);
            return "";
        }
    }

    private static int readInt(String prompt, String invalidMessage) {
        while (true) {
            String text = readLine(prompt);
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println(invalidMessage);
            }
        }
    }

    private static VehicleType readVehicleType() {
        while (true) {
            String text = readLine("Enter vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ");
            try {
                return VehicleType.valueOf(text.toUpperCase().replaceAll("\\s+", "_"));
            } catch (IllegalArgumentException e) {
                System.out.println("[!] Invalid vehicle type. Please choose from the listed options.");
            }
        }
    }

    private static IDateTime readDateTime(String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                String[] parts = text.split("\\s+");
                if (parts.length != 2) throw new IllegalArgumentException();

                String[] date = parts[0].split("/", -1);
                String[] time = parts[1].split(":", -1);
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
