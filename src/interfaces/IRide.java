package interfaces;
/**
 * A ride. Rides are immutable and compared by ride ID.
 */
public interface IRide extends Comparable<IRide> {

	// Returns the ride ID (assigned by the system).
	int getRideId();

	// Returns the pickup location.
	String getPickupLocation();

	// Returns the drop-off location.
	String getDropoffLocation();

	// Returns the time slot [pickup date/time, drop-off date/time).
	ITimeSlot getTimeSlot();

	// Returns the assigned driver.
	IDriver getDriver();

	// Returns true if the rider participates in this ride.
	boolean hasRider(int riderId);

	// Returns a formatted string describing the ride, including its ride ID.
	@Override
	String toString();

	// Compares rides by ride ID (negative, zero, or positive).
	@Override
	int compareTo(IRide other);
}
