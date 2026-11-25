package interfaces;
import datastructures.LinkedList;
/**
 * The Ride-Sharing System (Phase 2). It must store its data in the BST indexes
 * listed in Section 2.3 of the assignment. Ride IDs are assigned 1, 2, 3, ...
 * to rides as they are scheduled successfully; IDs are never reused.
 * Methods returning a LinkedList return an empty list (never null) when there
 * are no results.
 */
public interface IRideSharingSystem {

	// Each load method skips lines that cannot be parsed or added, and returns
	// the number of records added, or -1 if the file cannot be opened.
	int loadRidersFromCSV(String ridersFilePath);

	int loadDriversFromCSV(String driversFilePath);

	// Schedules each line with schedulePrivateRide/scheduleSharedRide, in file order.
	int loadRidesFromCSV(String ridesFilePath);

	// ---------------------------------------------------------------- riders

	// Returns false if a rider with the same ID already exists.
	boolean addRider(IRider rider);

	// Returns null if not found.
	IRider searchRiderById(int riderId);

	// Riders whose full name exactly matches, sorted by ID.
	LinkedList<IRider> searchRidersByName(String fullName);

	// Riders from the home city, sorted by ID.
	LinkedList<IRider> searchRidersByHomeCity(String homeCity);

	// All riders, sorted by ID.
	LinkedList<IRider> getAllRiders();

	// Removes the rider: their private rides are deleted, they are removed from
	// their shared rides, and shared rides left with no riders are deleted.
	// Deleted rides are removed from every index and schedule.
	boolean removeRider(int riderId);

	// --------------------------------------------------------------- drivers

	// Returns false if a driver with the same ID or vehicle plate already exists.
	boolean addDriver(IDriver driver);

	// Returns null if not found.
	IDriver searchDriverById(int driverId);

	// Drivers of the vehicle type, sorted by ID.
	LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType);

	// All drivers, sorted by ID.
	LinkedList<IDriver> getAllDrivers();

	// Removes the driver and deletes all their rides from every index and schedule.
	boolean removeDriver(int driverId);

	// ----------------------------------------------------------------- rides

	// The rider and driver must exist and the time slot must not conflict with
	// either schedule (checked with getSchedule().findKey). Returns the new ride ID, or -1.
	int schedulePrivateRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int riderId, int driverId);

	// Same rules for every rider; riderIds must hold at least two IDs and no
	// duplicates. If any check fails nothing changes. Returns the new ride ID, or -1.
	int scheduleSharedRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int[] riderIds, int driverId);

	// Deletes the ride from every index and from the schedules of its driver and riders.
	// Returns false if there is no ride with this ID.
	boolean cancelRide(int rideId);

	// Rides from the pickup location, sorted by ride ID.
	LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation);

	// Rides of any rider with this full name (found through the riders' schedules),
	// each ride once, sorted by ride ID.
	LinkedList<IRide> searchRidesByRiderName(String riderName);

	// Participants of the shared ride with this ID, sorted by rider ID.
	LinkedList<IRider> getSharedRideParticipants(int rideId);

	// All rides, alphabetically by pickup location (ties by ride ID).
	LinkedList<IRide> getAllRidesAlphabetically();

	// Rides with from <= pickup date/time <= to, sorted by pickup date/time, then ride ID.
	LinkedList<IRide> searchRidesByPickupTimeRange(IDateTime from, IDateTime to);

	// Drivers of the vehicle type with no ride overlapping the time slot, sorted by ID.
	LinkedList<IDriver> findAvailableDrivers(ITimeSlot timeSlot, VehicleType vehicleType);

	// ------------------------------------------------------- tree statistics

	// Number of nodes visited when the rider-ID index is searched for this ID (IBST.nbKeyComp).
	int nbKeyCompRiderById(int riderId);

	// size() of the given index; for a BST inside a BST, the number of keys of the outer BST.
	int indexSize(IndexName index);

	// height() of the given index; for a BST inside a BST, the height of the outer BST.
	int indexHeight(IndexName index);
}
