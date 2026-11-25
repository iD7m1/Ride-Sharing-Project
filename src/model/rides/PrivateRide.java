package model.rides;

import interfaces.IDriver;
import interfaces.IPrivateRide;
import interfaces.IRider;
import interfaces.ITimeSlot;

/**
 * Represents a private ride in the ride-sharing system.
 * <p>
 * A private ride is associated with exactly one rider
 * and one assigned driver.
 * </p>
 */
public class PrivateRide extends Ride implements IPrivateRide {
    private final IRider rider;

    /**
     * Constructs a private ride with the specified information.
     *
     * @param rideId          the ride's unique identifier
     * @param pickupLocation  the pickup location
     * @param dropoffLocation the drop-off location
     * @param timeSlot        the scheduled time slot
     * @param rider           the rider participating in this ride
     * @param driver          the assigned driver
     * @throws IllegalArgumentException if the rider is null or the inherited ride information is invalid
     */
    public PrivateRide(int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, IRider rider, IDriver driver) {
        super(rideId, pickupLocation, dropoffLocation, timeSlot, driver);
        if (rider == null)
            throw new IllegalArgumentException("Rider cannot be null.");
        this.rider = rider;
    }

    @Override
    public boolean hasRider(int riderId) {
        return rider.getId() == riderId;
    }

    @Override
    public IRider getRider() {
        return rider;
    }

    @Override
    public String toString() {
        return String.format("Private, %s, Rider: %s (ID %d)", super.toString(), rider.getName(), rider.getId());
    }
}
