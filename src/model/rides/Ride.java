package model.rides;
import interfaces.IDateTime;
import interfaces.IDriver;
import interfaces.IRide;

/**
 * Represents the common attributes and behavior of a ride
 * in the ride-sharing system.
 * <p>
 * This abstract class serves as the base class for PrivateRide
 * and SharedRide. Each ride has a unique identifier, pickup
 * and drop-off times, pickup and drop-off locations, and
 * an assigned driver.
 * </p>
 * The ride ID and scheduled times cannot be reassigned
 * after construction. Rides are compared alphabetically
 * by their pickup locations.
 */

public abstract class Ride implements IRide {

    private final int rideId;
    private final IDateTime pickupTime;
    private final IDateTime dropoffTime;

    private String pickupLocation;
    private String dropoffLocation;
    private IDriver driver;

    /**
     * Constructs a ride with the specified information.
     *
     * @param rideId          the ride's unique identifier
     * @param pickupTime      the scheduled pickup time
     * @param dropoffTime     the scheduled drop-off time
     * @param pickupLocation  the pickup location
     * @param dropoffLocation the drop-off location
     * @param driver          the assigned driver
     * @throws IllegalArgumentException if either time is null or the drop-off time is not later than the pickup time
     */
    public Ride (int rideId, IDateTime pickupTime, IDateTime dropoffTime, String pickupLocation, String dropoffLocation, IDriver driver) {

        if (pickupTime == null || dropoffTime == null)
            throw new IllegalArgumentException ("Pickup and drop-off times cannot be null.");
        if (pickupTime.compareTo(dropoffTime) >= 0)
            throw new IllegalArgumentException("Drop-off time must be later than pickup time.");

        this.rideId = rideId;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        setDropoffLocation(dropoffLocation);
        setPickupLocation(pickupLocation);
        setDriver(driver);
    }

    /**
     * Checks whether a rider participates in this ride.
     *
     * Each subclass provides its own implementation because
     * private and shared rides store riders differently.
     *
     * @param riderId the ID of the rider to search for
     * @return true if the rider participates; false otherwise
     */
    @Override
    public abstract boolean hasRider(int riderId);

    /**
     * Compares this ride with another ride alphabetically
     * by pickup location.
     *
     * @param other the ride to compare against
     * @return a negative value if this pickup location comes
     *         first, zero if both locations are equal, or a
     *         positive value if this location comes later
     */
    @Override
    public int compareTo(IRide other) {
        return this.pickupLocation.compareTo(other.getPickupLocation());
    }

    @Override
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @Override
    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    @Override
    public void setDriver(IDriver driver) {
        this.driver = driver;
    }

    @Override
    public int getRideId() {
        return rideId;
    }

    @Override
    public IDateTime getPickupTime() {
        return pickupTime;
    }

    @Override
    public IDateTime getDropoffTime() {
        return dropoffTime;
    }

    @Override
    public String getPickupLocation() {
        return pickupLocation;
    }

    @Override
    public String getDropoffLocation() {
        return dropoffLocation;
    }

    @Override
    public IDriver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return String.format("Ride ID: %d, Driver: %d - %s Pickup: %s, Pickup Time: %s, Drop-off: %s, Drop-off Time: %s",
                rideId, driver.getId(), driver.getName(), pickupLocation, pickupTime.format(), dropoffLocation, dropoffTime.format());
    }
}
