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

    @Override
    public LinkedList<IDriver> findAvailableDrivers(ITimeSlot timeSlot, VehicleType vehicleType) {
        // TODO: get drivers of vehicleType and filter those with !driver.getSchedule().findKey(timeSlot)
        throw new UnsupportedOperationException("Not implemented yet");
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
