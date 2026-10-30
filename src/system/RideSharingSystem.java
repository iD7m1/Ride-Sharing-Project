package system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import datastructures.LinkedList;
import interfaces.*;
import lists.DriverList;
import lists.RideList;
import lists.RiderList;
import model.DateTime;
import model.persons.Driver;
import model.persons.Rider;
import model.rides.PrivateRide;
import model.rides.SharedRide;

/**
 * The ride-sharing system application.
 * <p>
 * Coordinates RiderList, DriverList, and RideList and enforces the
 * system rules: unique rider IDs, driver IDs, and vehicle plates,
 * existence checks and conflict-free scheduling of rides, and cascade
 * removal of rides when a rider or a driver is removed.
 * </p>
 * Every scheduled ride is also stored in the ride history of its
 * driver and of each of its riders, so conflict checks and cascade
 * removals only visit the rides of the people involved.
 */
public class RideSharingSystem implements IRideSharingSystem {

    private IRiderList riderList;
    private IDriverList driverList;
    private IRideList rideList;
    private int nextRideId;

    /**
     * Constructs an empty ride-sharing system.
     *
     * Initializes the rider, driver, and ride collections
     * and sets the initial ride ID to 1.
     */
    public RideSharingSystem () {
        this.riderList = new RiderList();
        this.driverList = new DriverList();
        this.rideList = new RideList();
        this.nextRideId = 1;
    }

    /**
     * Converts a formatted date-time string into an IDateTime object.
     *
     * The expected format is MM/DD/YYYY HH:MM.
     *
     * @param dateTimeStr the date-time string to parse
     * @return an IDateTime object representing the specified date and time
     * @throws IllegalArgumentException if the date-time format or value is invalid
     */

    private IDateTime parseDateTime (String dateTimeStr) {
        String[] parts = dateTimeStr.trim().split(" ");
        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");

        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        return new DateTime(year, month, day, hour, minute);
    }

    /**
     * Loads rider records from a CSV file.
     *
     * Each nonempty line is expected to contain the rider ID,
     * name, email, phone number, and home city.
     *
     * Creates a Rider object for each record and attempts
     * to add it to the system.
     *
     * Empty lines are skipped. Invalid file operations or
     * invalid argument values cause the method to return false.
     *
     * @param ridersFilePath the path to the riders CSV file
     * @return true if the file is processed without a caught exception;
     *         false otherwise
     */
    @Override
    public boolean loadRidersFromCSV(String ridersFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                String email = tokens[2].trim();
                String phone = tokens[3].trim();
                String city = tokens[4].trim();

                IRider rider = new Rider(id, name, phone, email, city);
                addRider(rider);
            }
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Loads driver records from a CSV file.
     *
     * Each nonempty line is expected to contain the driver ID,
     * name, phone number, vehicle plate, and vehicle type.
     *
     * Converts the vehicle type into its corresponding enum
     * constant, creates a Driver object, and attempts to add
     * it to the system.
     *
     * Empty lines are skipped. Invalid file operations or
     * invalid argument values cause the method to return false.
     *
     * @param driversFilePath the path to the drivers CSV file
     * @return true if the file is processed without a caught exception;
     *         false otherwise
     */
    @Override
    public boolean loadDriversFromCSV(String driversFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(driversFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                String phone = tokens[2].trim();
                String plate = tokens[3].trim();
                VehicleType vehicleType = VehicleType.valueOf(tokens[4].trim());

                IDriver driver = new Driver(id, name, phone, plate, vehicleType);
                addDriver(driver);
            }
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Loads ride records from a CSV file.
     *
     * Each nonempty line specifies the ride type, pickup location,
     * pickup time, drop-off time, drop-off location, driver ID,
     * and rider ID or IDs.
     *
     * Private rides contain one rider ID, while shared rides
     * contain multiple rider IDs separated by semicolons.
     *
     * Uses the corresponding scheduling method to attempt
     * to register each ride.
     *
     * Empty lines are skipped. Invalid file operations or
     * invalid argument values cause the method to return false.
     *
     * @param ridesFilePath the path to the rides CSV file
     * @return true if the file is processed without a caught exception; false otherwise
     */
    @Override
    public boolean loadRidesFromCSV(String ridesFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                String rideType = tokens[0].trim();
                String pickupLoc = tokens[1].trim();
                IDateTime pickupTime = parseDateTime(tokens[2]);
                IDateTime dropoffTime = parseDateTime(tokens[3]);
                String dropoffLoc = tokens[4].trim();
                int driverId = Integer.parseInt(tokens[5].trim());

                if (rideType.equalsIgnoreCase("PRIVATE")) {
                    int riderId = Integer.parseInt(tokens[6].trim());
                    schedulePrivateRide(pickupLoc, pickupTime, dropoffTime, dropoffLoc, riderId, driverId);
                } else if (rideType.equalsIgnoreCase("SHARED")) {
                    String[] idStrs = tokens[6].trim().split(";");
                    int[] riderIds = new int[idStrs.length];
                    for (int i = 0; i < idStrs.length; i++) {
                        riderIds[i] = Integer.parseInt(idStrs[i].trim());
                    }
                    scheduleSharedRide(pickupLoc, pickupTime, dropoffTime, dropoffLoc, riderIds, driverId);
                }
            }
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Adds a rider to the system.
     *
     * Rejects null riders and delegates insertion and
     * uniqueness validation to the rider list.
     *
     * @param rider the rider to add
     * @return true if the rider is added successfully;
     *         false if the rider is null or rejected
     */
    @Override
    public boolean addRider(IRider rider) {
        if (rider == null) return false;
        return riderList.add(rider);
    }

    /**
     * Adds a driver to the system.
     *
     * Rejects null drivers and delegates insertion and
     * uniqueness validation to the driver list.
     *
     * @param driver the driver to add
     * @return true if the driver is added successfully;
     *         false if the driver is null or rejected
     */
    @Override
    public boolean addDriver(IDriver driver) {
        if (driver == null) return false;
        return driverList.add(driver);
    }

    /**
     * Searches for a rider by their unique ID.
     *
     * @param riderId the ID of the rider to find
     * @return the matching rider, or null if not found
     */
    @Override
    public IRider searchRiderById (int riderId) {
        return riderList.findById(riderId);
    }

    /**
     * Searches for a rider by email address.
     *
     * @param email the email address to search for
     * @return the matching rider, or null if not found
     */
    @Override
    public IRider searchRiderByEmail(String email) {
        return riderList.findByEmail(email);
    }

    /**
     * Searches for all riders with the specified full name.
     *
     * @param fullName the full name to search for
     * @return a linked list containing all matching riders
     */
    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riderList.findByName(fullName);
    }

    /**
     * Searches for all riders from the specified home city.
     *
     * @param homeCity the home city to search for
     * @return a linked list containing all matching riders
     */
    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riderList.findByHomeCity(homeCity);
    }

    /**
     * Retrieves all riders registered in the system.
     *
     * @return a linked list containing all registered riders
     */
    @Override
    public LinkedList<IRider> getAllRiders() {
        return riderList.getAll();
    }

    /**
     * Searches for a driver by their unique ID.
     *
     * @param driverId the ID of the driver to find
     * @return the matching driver, or null if not found
     */
    @Override
    public IDriver searchDriverById(int driverId) {
        return driverList.findById(driverId);
    }

    /**
     * Searches for a driver by vehicle plate number.
     *
     * @param vehiclePlate the vehicle plate to search for
     * @return the matching driver, or null if not found
     */
    @Override
    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return driverList.findByVehiclePlate(vehiclePlate);
    }

    /**
     * Searches for all drivers with the specified vehicle type.
     *
     * @param vehicleType the vehicle type to search for
     * @return a linked list containing all matching drivers
     */
    @Override
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return driverList.findByVehicleType(vehicleType);
    }

    /**
     * Retrieves all drivers registered in the system.
     *
     * @return a linked list containing all registered drivers
     */
    @Override
    public LinkedList<IDriver> getAllDrivers() {
        return driverList.getAll();
    }

    /**
     * Removes a rider and cascades the removal to their rides.
     * <p>
     * Every private ride of the rider is deleted. The rider is removed
     * from every shared ride they are registered on, and a shared ride
     * left with no participants is deleted.
     * </p>
     * The rider's ride history holds exactly the rides they are on,
     * so only those rides are visited.
     *
     * @param riderId the ID of the rider to remove
     * @return true if the rider was found and removed; false otherwise
     */
    @Override
    public boolean removeRider (int riderId) {
        IRider rider = riderList.findById(riderId);
        if (rider == null) return false;

        LinkedList<IRide> history = rider.getRideHistory();

        // Take the first ride out of the history until the history is empty
        while (!history.empty()) {
            history.findFirst();
            IRide ride = history.retrieve();
            history.remove();

            if (ride instanceof IPrivateRide) {
                deleteRide(ride);
            } else if (ride instanceof ISharedRide) {
                ISharedRide sharedRide = (ISharedRide) ride;
                sharedRide.removeParticipantById(riderId);

                // A shared ride with no riders left is deleted
                if (sharedRide.isEmpty())
                    deleteRide(ride);
            }
        }

        riderList.removeById(riderId);
        return true;
    }

    /**
     * Removes a driver and deletes every ride (private or shared)
     * assigned to them.
     * <p>
     * The driver's ride history holds exactly the rides assigned
     * to them, so only those rides are visited.
     *
     * @param driverId the ID of the driver to remove
     * @return true if the driver was found and removed; false otherwise
     */
    @Override
    public boolean removeDriver (int driverId) {
        IDriver driver = driverList.findById(driverId);
        if (driver == null) return false;

        LinkedList<IRide> history = driver.getRideHistory();

        // Take the first ride out of the history until the history is empty
        while (!history.empty()) {
            history.findFirst();
            IRide ride = history.retrieve();
            history.remove();
            deleteRide (ride);
        }

        driverList.removeById(driverId);
        return true;
    }

    /**
     * Schedules a private ride for one rider and one driver.
     *
     * The rider and driver must already exist, and the ride
     * must not conflict with either person's existing schedule.
     *
     * @param pickupLocation the pickup location
     * @param pickupTime the pickup date and time
     * @param dropoffTime the drop-off date and time
     * @param dropoffLocation the drop-off location
     * @param riderId the ID of the rider
     * @param driverId the ID of the assigned driver
     * @return true if the ride is scheduled successfully;
     *         false otherwise
     */
    @Override
    public boolean schedulePrivateRide (String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId) {
        if (!isValidRideInfo(pickupLocation, pickupTime, dropoffTime, dropoffLocation)) return false;

        // The rider and the driver must exist
        IRider rider = riderList.findById(riderId);
        IDriver driver = driverList.findById(driverId);
        if (rider == null || driver == null) return false;

        // The ride must not conflict with the rider's or the driver's schedule
        if (hasConflict(rider.getRideHistory(), pickupTime, dropoffTime)) return false;
        if (hasConflict(driver.getRideHistory(), pickupTime, dropoffTime)) return false;

        IPrivateRide ride = new PrivateRide(nextRideId++, pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver, rider);
        rideList.addRide(ride);
        rider.getRideHistory().insert(ride);
        driver.getRideHistory().insert(ride);
        return true;
    }

    /**
     * Schedules a shared ride involving multiple riders
     * and one driver.
     *
     * All riders and the driver must already exist.
     * The ride must not conflict with the existing schedule
     * of any participating rider or the assigned driver.
     *
     * @param pickupLocation the pickup location
     * @param pickupTime the pickup date and time
     * @param dropoffTime the drop-off date and time
     * @param dropoffLocation the drop-off location
     * @param riderIds the IDs of the participating riders
     * @param driverId the ID of the assigned driver
     * @return true if the ride is scheduled successfully;
     *         false otherwise
     */
    @Override
    public boolean scheduleSharedRide (String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {
        if (!isValidRideInfo(pickupLocation, pickupTime, dropoffTime, dropoffLocation)) return false;
        if (riderIds == null || riderIds.length < 2) return false;

        // The driver must exist and be free at this time
        IDriver driver = driverList.findById(driverId);
        if (driver == null) return false;
        if (hasConflict(driver.getRideHistory(), pickupTime, dropoffTime)) return false;

        // Every rider must be listed once, exist, and be free at this time
        IRider[] riders = new IRider[riderIds.length];
        for (int i = 0; i < riderIds.length; i++) {
            for (int j = 0; j < i; j++) {
                if (riderIds[j] == riderIds[i]) return false; // Same rider listed twice
            }

            riders[i] = riderList.findById(riderIds[i]);
            if (riders[i] == null) return false;
            if (hasConflict(riders[i].getRideHistory(), pickupTime, dropoffTime)) return false;
        }

        // All checks passed: create the ride and link it to everyone on it
        ISharedRide ride = new SharedRide(nextRideId++, pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);
        for (int i = 0; i < riders.length; i++) {
            ride.addParticipant(riders[i]);
            riders[i].getRideHistory().insert(ride);
        }
        rideList.addRide(ride);
        driver.getRideHistory().insert(ride);
        return true;
    }

    /**
     * Searches for all rides with the specified pickup location.
     *
     * Multiple rides may have the same pickup location.
     *
     * @param pickupLocation the pickup location to search for
     * @return a linked list containing all matching rides
     */
    @Override
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rideList.findByPickupLocation(pickupLocation);
    }

    /**
     * Searches for all rides involving a rider
     * with the specified name.
     *
     * @param riderName the rider name to search for
     * @return a linked list containing all matching rides
     */
    @Override
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rideList.findByRiderName(riderName);
    }

    /**
     * Returns the riders registered on the shared ride at the given
     * pickup location.
     * <p>
     * If more than one shared ride starts at that location, the riders
     * of all of them are returned, each rider only once. Private rides
     * at that location are ignored.
     *
     * @param pickupLocation the pickup location of the shared ride
     * @return a list of the participants, or an empty list if there is
     *         no shared ride at that location
     */
    @Override
    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        LinkedList<IRide> matches = rideList.findByPickupLocation(pickupLocation);
        if (matches.empty()) return result;

        matches.findFirst();
        while (true) {
            if (matches.retrieve() instanceof ISharedRide) {
                LinkedList<IRider> participants = ((ISharedRide) matches.retrieve()).getParticipants();

                if (!participants.empty()) {
                    participants.findFirst();
                    while (true) {
                        // containsRider leaves the cursor on the last element when
                        // the rider is not found, so insert() appends at the end
                        if (!containsRider(result, participants.retrieve().getId()))
                            result.insert(participants.retrieve());
                        if (participants.last()) break;
                        participants.findNext();
                    }
                }
            }
            if (matches.last()) break;
            matches.findNext();
        }
        return result;
    }

    @Override
    public LinkedList<IRide> getAllRidesAlphabetically() {
        // RideList keeps the rides sorted by pickup location, so no sorting is needed
        return rideList.getAllAlphabetically();
    }

    /**
     * Checks the common information every new ride needs: both locations
     * present, both times present, and the drop-off after the pickup.
     *
     * @return true if the information is valid; false otherwise
     */
    private boolean isValidRideInfo(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation) {
        if (pickupLocation == null || pickupLocation.trim().isEmpty()) return false;
        if (dropoffLocation == null || dropoffLocation.trim().isEmpty()) return false;
        if (pickupTime == null || dropoffTime == null) return false;

        // The drop-off must come after the pickup
        return pickupTime.compareTo(dropoffTime) < 0;
    }

    /**
     * Checks whether a time range overlaps any ride in a ride history.
     * <p>
     * Two ranges [pickup1, dropoff1) and [pickup2, dropoff2) overlap
     * exactly when pickup1 < dropoff2 and pickup2 < dropoff1, where
     * "<" is evaluated with compareTo. A ride that starts exactly when
     * another one ends is therefore not a conflict.
     *
     * @param history     the rides already scheduled for a person
     * @param pickupTime  the pickup time of the new ride
     * @param dropoffTime the drop-off time of the new ride
     * @return true if the new ride overlaps a ride in the history; false otherwise
     */
    private boolean hasConflict(LinkedList<IRide> history, IDateTime pickupTime, IDateTime dropoffTime) {
        if (history.empty()) return false;

        history.findFirst();
        while (true) {
            IRide ride = history.retrieve();
            if (pickupTime.compareTo(ride.getDropoffTime()) < 0 && ride.getPickupTime().compareTo(dropoffTime) < 0)
                return true;
            if (history.last()) break;
            history.findNext();
        }
        return false;
    }

    /**
     * Deletes a ride from the system: removes it from the ride list,
     * from its driver's ride history, and from the ride history of
     * every rider on it.
     *
     * @param ride the ride to delete
     */
    private void deleteRide (IRide ride) {
        int rideId = ride.getRideId();
        rideList.removeRideById(rideId);
        removeFromHistory (ride.getDriver().getRideHistory(), rideId);

        if (ride instanceof IPrivateRide) {
            removeFromHistory(((IPrivateRide) ride).getRider().getRideHistory(), rideId);
        } else if (ride instanceof ISharedRide) {
            LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
            if (participants.empty()) return;

            participants.findFirst();
            while (true) {
                removeFromHistory(participants.retrieve().getRideHistory(), rideId);
                if (participants.last()) break;
                participants.findNext();
            }
        }
    }

    /**
     * Removes the ride with the given ID from a ride history, if present.
     *
     * @param history the ride history to update
     * @param rideId  the ID of the ride to remove
     */
    private void removeFromHistory (LinkedList<IRide> history, int rideId) {
        if (history.empty()) return;

        history.findFirst();
        while (true) {
            if (history.retrieve().getRideId() == rideId) {
                history.remove();
                return;
            }
            if (history.last()) break;
            history.findNext();
        }
    }

    /**
     * Checks whether a list of riders contains a rider with the given ID.
     * When the rider is not found, the cursor is left on the last element.
     *
     * @param riders  the list to search
     * @param riderId the rider ID to search for
     * @return true if the rider is in the list; false otherwise
     */
    private boolean containsRider(LinkedList<IRider> riders, int riderId) {
        if (riders.empty()) return false;

        riders.findFirst();
        while (true) {
            if (riders.retrieve().getId() == riderId) return true;
            if (riders.last()) break;
            riders.findNext();
        }
        return false;
    }
}
