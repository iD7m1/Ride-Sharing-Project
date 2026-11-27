package system;

import datastructures.BST;
import datastructures.LinkedList;
import interfaces.*;
import model.DateTime;

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

    /**
     * Converts a formatted date-time string (MM/DD/YYYY HH:MM) into an IDateTime object.
     *
     * @param dateTimeStr the date-time string to parse
     * @return an IDateTime object representing the specified date and time
     * @throws IllegalArgumentException if the date-time format or value is invalid
     */
    private IDateTime parseDateTime(String dateTimeStr) {
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

    // ---------------------------------------------------------------- CSV loading

    @Override
    public int loadRidersFromCSV(String ridersFilePath) {
        // TODO: skip invalid lines, return count of added riders, or -1 if file cannot be opened
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int loadDriversFromCSV(String driversFilePath) {
        // TODO: skip invalid lines, return count of added drivers, or -1 if file cannot be opened
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int loadRidesFromCSV(String ridesFilePath) {
        // TODO: schedule each line in file order (note: driverId is before riderIds in CSV), return count or -1
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // ---------------------------------------------------------------- riders

    @Override
    public boolean addRider(IRider rider) {
        // TODO: check uniqueness by ID, insert into ridersById, ridersByName, ridersByHomeCity
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IRider searchRiderById(int riderId) {
        // TODO: search ridersById
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        // TODO: search ridersByName and return inner BST inOrder() (empty list if not found)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        // TODO: search ridersByHomeCity and return inner BST inOrder() (empty list if not found)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRider> getAllRiders() {
        // TODO: return ridersById.inOrder()
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean removeRider(int riderId) {
        // TODO: cascade delete private rides, remove from shared rides (delete if empty), remove from all rider indexes
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // --------------------------------------------------------------- drivers

    /**
     * Adds a driver to the three driver indexes.
     * <p>
     * Uniqueness of the ID and of the vehicle plate is checked before
     * anything is inserted, so a rejected driver is never left behind
     * in some of the indexes.
     * </p>
     *
     * @param driver the driver to add
     * @return true if the driver was added; false if the driver is invalid,
     *         or a driver with the same ID or vehicle plate already exists
     */
    @Override
    public boolean addDriver(IDriver driver) {
        if (driver == null)
            return false;

        int id = driver.getId();
        String plate = driver.getVehiclePlate();
        VehicleType vehicleType = driver.getVehicleType();

        // The plate and the vehicle type are BST keys, so they cannot be missing
        if (plate == null || vehicleType == null)
            return false;

        // Check uniqueness first: nothing is inserted if the driver is rejected
        if (driversById.findKey(id) || driversByPlate.findKey(plate))
            return false;

        // Unique-key indexes: each key maps directly to the driver
        driversById.insert(id, driver);
        driversByPlate.insert(plate, driver);

        // Non-unique-key index: each vehicle type maps to an inner BST
        // holding all the drivers of that type, keyed by driver ID
        if (driversByVehicleType.findKey(vehicleType)) {
            driversByVehicleType.retrieve().insert(id, driver);
        } else {
            IBST<Integer, IDriver> sameTypeDrivers = new BST<Integer, IDriver>();
            sameTypeDrivers.insert(id, driver);
            driversByVehicleType.insert(vehicleType, sameTypeDrivers);
        }
        return true;
    }

    /**
     * Searches the driver-ID index for a driver.
     *
     * @param driverId the ID of the driver to search for
     * @return the driver with this ID, or null if not found
     */
    @Override
    public IDriver searchDriverById(int driverId) {
        if (driversById.findKey(driverId))
            return driversById.retrieve();
        return null;
    }

    /**
     * Returns the drivers of a vehicle type, sorted by ID.
     * <p>
     * The inner BST of the vehicle type is keyed by driver ID, so its
     * in-order traversal is already sorted and no sorting is needed.
     * </p>
     *
     * @param vehicleType the vehicle type to search for
     * @return a list of the matching drivers, or an empty list if there are none
     */
    @Override
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        if (vehicleType != null && driversByVehicleType.findKey(vehicleType))
            return driversByVehicleType.retrieve().inOrder();
        return new LinkedList<IDriver>();
    }

    /**
     * Returns all drivers, sorted by ID, using an in-order traversal
     * of the driver-ID index.
     *
     * @return a list of all drivers, or an empty list if there are none
     */
    @Override
    public LinkedList<IDriver> getAllDrivers() {
        return driversById.inOrder();
    }

    @Override
    public boolean removeDriver(int driverId) {
        // TODO: delete all driver's rides from every index & schedule, then remove from all driver indexes
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // ----------------------------------------------------------------- rides

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

    /**
     * Returns the drivers of a vehicle type that have no ride overlapping
     * the time slot, sorted by ID.
     * <p>
     * Two time slots compare as equal when they overlap, so a single
     * search of a driver's schedule tells whether the driver is busy.
     * </p>
     *
     * @param timeSlot    the time slot the drivers must be free in
     * @param vehicleType the vehicle type of the drivers
     * @return a list of the available drivers, or an empty list if there are none
     */
    @Override
    public LinkedList<IDriver> findAvailableDrivers(ITimeSlot timeSlot, VehicleType vehicleType) {
        LinkedList<IDriver> available = new LinkedList<IDriver>();
        if (timeSlot == null)
            return available;

        // Drivers of the requested type, already sorted by ID
        LinkedList<IDriver> candidates = searchDriversByVehicleType(vehicleType);
        if (candidates.empty())
            return available;

        candidates.findFirst();
        while (true) {
            IDriver driver = candidates.retrieve();
            // findKey returns true only if a ride of the schedule overlaps the slot
            if (!driver.getSchedule().findKey(timeSlot))
                available.insert(driver);
            if (candidates.last())
                break;
            candidates.findNext();
        }
        return available;
    }

    // ------------------------------------------------------- tree statistics

    @Override
    public int nbKeyCompRiderById(int riderId) {
        // TODO: return ridersById.nbKeyComp(riderId)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int indexSize(IndexName index) {
        // TODO: return size() of the requested index
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int indexHeight(IndexName index) {
        // TODO: return height() of the requested index
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
