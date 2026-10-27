package model.rides;
import interfaces.IDateTime;
import interfaces.IDriver;
import interfaces.IPrivateRide;
import interfaces.IRider;


/**
 * Represents a private ride in the ride-sharing system.
 * <p>
 * A private ride is associated with exactly one rider
 * and one assigned driver.
 *
 * Inherits the ride ID, pickup and drop-off information,
 * scheduled times, and assigned driver from Ride. </p>
 */
public class PrivateRide extends Ride implements IPrivateRide {
    private IRider rider;

    /**
     * Constructs a private ride with the specified information.
     *
     * @param rideId          the ride's unique identifier
     * @param pickupTime      the scheduled pickup time
     * @param dropoffTime     the scheduled drop-off time
     * @param pickupLocation  the pickup location
     * @param dropoffLocation the drop-off location
     * @param driver          the assigned driver
     * @param rider           the rider participating in this ride
     * @throws IllegalArgumentException if the rider is null or the inherited ride information is invalid
     */
    public PrivateRide (int rideId, IDateTime pickupTime, IDateTime dropoffTime, String pickupLocation, String dropoffLocation, IDriver driver, IRider rider) {
        super(rideId, pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);
        setRider(rider);
    }

    /**
     * Checks whether the specified rider participates in this ride.
     *
     * Since a private ride contains exactly one rider, the method
     * compares the provided ID with the assigned rider's ID.
     *
     * @param riderId the ID of the rider to check
     * @return true if the IDs match; false otherwise
     */
    @Override
    public boolean hasRider(int riderId) {
        return rider.getId() == riderId;
    }

    @Override
    public IRider getRider() {
        return rider;
    }

    @Override
    public void setRider(IRider rider) {
        if (rider == null) throw new IllegalArgumentException ("Rider cannot be null.");
        this.rider = rider;
    }

    @Override
    public String toString() {
        return String.format("%s, Rider: %s (ID %d)", super.toString(), rider.getName(), rider.getId());
    }
}
