package system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import datastructures.BST;
import datastructures.LinkedList;
import interfaces.*;
import model.DateTime;
import model.TimeSlot;
import model.persons.Driver;
import model.persons.Rider;

/**
 * The Ride-Sharing System (Phase 2).
 * <p>
 * Stores all data in the nine required BST indexes and maintains
 * person schedules and shared-ride participant BSTs.
 * RideSharingSystem must not print anything; all I/O belongs in Main.
 * </p>
 */
public class RideSharingSystem implements IRideSharingSystem {

    // Rider indexes
    private final IBST<Integer, IRider> ridersById;
    private final IBST<String, IBST<Integer, IRider>> ridersByName;
    private final IBST<String, IBST<Integer, IRider>> ridersByHomeCity;

    // Driver indexes
    private final IBST<Integer, IDriver> driversById;
    private final IBST<String, IDriver> driversByPlate;
    private final IBST<VehicleType, IBST<Integer, IDriver>> driversByVehicleType;

    // Ride indexes
    private final IBST<Integer, IRide> ridesById;
    private final IBST<String, IBST<Integer, IRide>> ridesByPickupLocation;
    private final IBST<IDateTime, IBST<Integer, IRide>> ridesByPickupTime;

    private int nextRideId;

    /**
     * Constructs an empty ride-sharing system with all nine BST indexes
     * and sets the initial ride ID to 1.
     */
    public RideSharingSystem() {
        this.ridersById = new BST<Integer, IRider>();
        this.ridersByName = new BST<String, IBST<Integer, IRider>>();
        this.ridersByHomeCity = new BST<String, IBST<Integer, IRider>>();

        this.driversById = new BST<Integer, IDriver>();
        this.driversByPlate = new BST<String, IDriver>();
        this.driversByVehicleType = new BST<VehicleType, IBST<Integer, IDriver>>();

        this.ridesById = new BST<Integer, IRide>();
        this.ridesByPickupLocation = new BST<String, IBST<Integer, IRide>>();
        this.ridesByPickupTime = new BST<IDateTime, IBST<Integer, IRide>>();

        this.nextRideId = 1;
    }

    // -------------------------------------------------------- internal helpers

    /**
     * Inserts a value into a non-unique (BST-inside-BST) index.
     * Creates a new inner BST if the outer key does not exist yet.
     *
     * @param outerIndex the outer BST mapping outerKey to an inner BST keyed by ID
     * @param outerKey   the non-unique key in the outer BST
     * @param innerKey   the unique object ID in the inner BST
     * @param value      the object to store
     */
    private <K extends Comparable<K>, V> void insertIntoNestedIndex(
            IBST<K, IBST<Integer, V>> outerIndex, K outerKey, int innerKey, V value) {
        if (outerIndex.findKey(outerKey)) {
            outerIndex.retrieve().insert(innerKey, value);
        } else {
            IBST<Integer, V> inner = new BST<Integer, V>();
            inner.insert(innerKey, value);
            outerIndex.insert(outerKey, inner);
        }
    }

    /**
     * Removes a value from a non-unique (BST-inside-BST) index.
     * If removing leaves the inner BST empty, its key is also removed from the outer BST.
     *
     * @param outerIndex the outer BST mapping outerKey to an inner BST keyed by ID
     * @param outerKey   the non-unique key in the outer BST
     * @param innerKey   the unique object ID in the inner BST
     */
    private <K extends Comparable<K>, V> void removeFromNestedIndex(
            IBST<K, IBST<Integer, V>> outerIndex, K outerKey, int innerKey) {
        if (outerIndex.findKey(outerKey)) {
            IBST<Integer, V> inner = outerIndex.retrieve();
            inner.removeKey(innerKey);
            if (inner.empty()) {
                outerIndex.removeKey(outerKey);
            }
        }
    }

    /**
     * Returns the outer BST corresponding to the given IndexName.
     *
     * @param index the index identifier
     * @return the corresponding IBST
     */
    private IBST<?, ?> getIndex(IndexName index) {
        if (index == null) {
            throw new IllegalArgumentException("Index name cannot be null.");
        }
        return switch (index) {
            case RIDERS_BY_ID -> ridersById;
            case RIDERS_BY_NAME -> ridersByName;
            case RIDERS_BY_HOME_CITY -> ridersByHomeCity;
            case DRIVERS_BY_ID -> driversById;
            case DRIVERS_BY_PLATE -> driversByPlate;
            case DRIVERS_BY_VEHICLE_TYPE -> driversByVehicleType;
            case RIDES_BY_ID -> ridesById;
            case RIDES_BY_PICKUP_LOCATION -> ridesByPickupLocation;
            case RIDES_BY_PICKUP_TIME -> ridesByPickupTime;
        };
    }

    /**
     * Converts a formatted date-time string (MM/DD/YYYY HH:MM) into an IDateTime object.
     *
     * @param dateTimeStr the date-time string to parse
     * @return an IDateTime object representing the specified date and time
     * @throws IllegalArgumentException if the date-time format or value is invalid
     */
    private IDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null) {
            throw new IllegalArgumentException("Date/time string cannot be null.");
        }
        String[] parts = dateTimeStr.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid date/time format: " + dateTimeStr);
        }
        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");
        if (dateParts.length != 3 || timeParts.length != 2) {
            throw new IllegalArgumentException("Invalid date/time format: " + dateTimeStr);
        }

        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        return new DateTime(year, month, day, hour, minute);
    }

    // ------------------------------------------------------------- CSV loading

    @Override
    public int loadRidersFromCSV(String ridersFilePath) {
        if (ridersFilePath == null) return -1;
        int added = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ridersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    String[] tokens = line.split(",");
                    if (tokens.length < 5) continue;

                    int id = Integer.parseInt(tokens[0].trim());
                    String name = tokens[1].trim();
                    String email = tokens[2].trim();
                    String phone = tokens[3].trim();
                    String homeCity = tokens[4].trim();

                    IRider rider = new Rider(id, name, email, phone, homeCity);
                    if (addRider(rider)) {
                        added++;
                    }
                } catch (IllegalArgumentException e) {
                    // Skip lines that cannot be parsed or validated
                }
            }
            return added;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int loadDriversFromCSV(String driversFilePath) {
        if (driversFilePath == null) return -1;
        int added = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(driversFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    String[] tokens = line.split(",");
                    if (tokens.length < 5) continue;

                    int id = Integer.parseInt(tokens[0].trim());
                    String name = tokens[1].trim();
                    String phone = tokens[2].trim();
                    String plate = tokens[3].trim();
                    VehicleType vehicleType = VehicleType.valueOf(tokens[4].trim());

                    IDriver driver = new Driver(id, name, phone, plate, vehicleType);
                    if (addDriver(driver)) {
                        added++;
                    }
                } catch (IllegalArgumentException e) {
                    // Skip lines that cannot be parsed or validated
                }
            }
            return added;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int loadRidesFromCSV(String ridesFilePath) {
        if (ridesFilePath == null) return -1;
        int added = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ridesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    String[] tokens = line.split(",");
                    if (tokens.length < 7) continue;

                    String rideType = tokens[0].trim();
                    String pickupLocation = tokens[1].trim();
                    IDateTime pickupTime = parseDateTime(tokens[2]);
                    IDateTime dropoffTime = parseDateTime(tokens[3]);
                    String dropoffLocation = tokens[4].trim();
                    int driverId = Integer.parseInt(tokens[5].trim());

                    ITimeSlot timeSlot = new TimeSlot(pickupTime, dropoffTime);

                    if (rideType.equalsIgnoreCase("PRIVATE")) {
                        int riderId = Integer.parseInt(tokens[6].trim());
                        if (schedulePrivateRide(pickupLocation, dropoffLocation, timeSlot, riderId, driverId) != -1) {
                            added++;
                        }
                    } else if (rideType.equalsIgnoreCase("SHARED")) {
                        String[] idStrs = tokens[6].trim().split(";");
                        int[] riderIds = new int[idStrs.length];
                        for (int i = 0; i < idStrs.length; i++) {
                            riderIds[i] = Integer.parseInt(idStrs[i].trim());
                        }
                        if (scheduleSharedRide(pickupLocation, dropoffLocation, timeSlot, riderIds, driverId) != -1) {
                            added++;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // Skip lines that cannot be parsed or validated
                }
            }
            return added;
        } catch (IOException e) {
            return -1;
        }
    }

    // ------------------------------------------------------------------ riders

    @Override
    public boolean addRider(IRider rider) {
        if (rider == null || rider.getName() == null || rider.getHomeCity() == null) {
            return false;
        }
        // Check uniqueness first so a rejected rider is never left in any index
        if (ridersById.findKey(rider.getId())) {
            return false;
        }

        ridersById.insert(rider.getId(), rider);
        insertIntoNestedIndex(ridersByName, rider.getName(), rider.getId(), rider);
        insertIntoNestedIndex(ridersByHomeCity, rider.getHomeCity(), rider.getId(), rider);
        return true;
    }

    @Override
    public IRider searchRiderById(int riderId) {
        if (ridersById.findKey(riderId)) {
            return ridersById.retrieve();
        }
        return null;
    }

    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        if (fullName == null || !ridersByName.findKey(fullName)) {
            return new LinkedList<IRider>();
        }
        return ridersByName.retrieve().inOrder();
    }

    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        if (homeCity == null || !ridersByHomeCity.findKey(homeCity)) {
            return new LinkedList<IRider>();
        }
        return ridersByHomeCity.retrieve().inOrder();
    }

    @Override
    public LinkedList<IRider> getAllRiders() {
        return ridersById.inOrder();
    }

    @Override
    public boolean removeRider(int riderId) {
        if (!ridersById.findKey(riderId)) {
            return false;
        }
        IRider rider = ridersById.retrieve();

        // Snapshot the rider's rides before mutating schedules
        LinkedList<IRide> riderRides = rider.getSchedule().inOrder();
        if (!riderRides.empty()) {
            riderRides.findFirst();
            while (true) {
                IRide ride = riderRides.retrieve();
                if (ride instanceof IPrivateRide) {
                    cancelRide(ride.getRideId());
                } else if (ride instanceof ISharedRide) {
                    ISharedRide sharedRide = (ISharedRide) ride;
                    sharedRide.removeParticipantById(riderId);
                    rider.getSchedule().removeKey(ride.getTimeSlot());
                    if (sharedRide.isEmpty()) {
                        cancelRide(ride.getRideId());
                    }
                }
                if (riderRides.last()) break;
                riderRides.findNext();
            }
        }

        // Remove the rider from all three rider indexes
        ridersById.removeKey(riderId);
        removeFromNestedIndex(ridersByName, rider.getName(), riderId);
        removeFromNestedIndex(ridersByHomeCity, rider.getHomeCity(), riderId);
        return true;
    }

    // ----------------------------------------------------------------- drivers

    @Override
    public boolean addDriver(IDriver driver) {
        // TODO: check uniqueness by ID and vehicle plate, insert into all driver indexes
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IDriver searchDriverById(int driverId) {
        // TODO: search driversById
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        // TODO: search driversByVehicleType and return inner BST inOrder() (empty list if not found)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IDriver> getAllDrivers() {
        // TODO: return driversById.inOrder()
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean removeDriver(int driverId) {
        // TODO: delete all driver's rides from every index & schedule, then remove from all driver indexes
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // ------------------------------------------------------------------- rides

    @Override
    public int schedulePrivateRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int riderId, int driverId) {
        // TODO: validate rider/driver existence and schedule conflicts via getSchedule().findKey(timeSlot); return new ride ID or -1
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int scheduleSharedRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int[] riderIds, int driverId) {
        // TODO: validate >= 2 unique riders, existence, and schedule conflicts; return new ride ID or -1
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean cancelRide(int rideId) {
        // TODO: delete ride from all ride indexes and from driver/rider schedules
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        // TODO: search ridesByPickupLocation and return inner BST inOrder()
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        // TODO: find riders by name, collect rides from schedules (deduplicated & sorted by ride ID)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRider> getSharedRideParticipants(int rideId) {
        // TODO: look up ride in ridesById; if ISharedRide return getParticipants(), else empty list
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRide> getAllRidesAlphabetically() {
        // TODO: traverse ridesByPickupLocation in-order and collect inner BSTs in-order in O(n)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupTimeRange(IDateTime from, IDateTime to) {
        // TODO: use ridesByPickupTime.rangeSearch(from, to) and collect inner BSTs in-order
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IDriver> findAvailableDrivers(ITimeSlot timeSlot, VehicleType vehicleType) {
        // TODO: get drivers of vehicleType and filter those with !driver.getSchedule().findKey(timeSlot)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // --------------------------------------------------------- tree statistics

    @Override
    public int nbKeyCompRiderById(int riderId) {
        return ridersById.nbKeyComp(riderId);
    }

    @Override
    public int indexSize(IndexName index) {
        return getIndex(index).size();
    }

    @Override
    public int indexHeight(IndexName index) {
        return getIndex(index).height();
    }
}
