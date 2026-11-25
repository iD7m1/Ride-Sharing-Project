package model.rides;

import interfaces.IDriver;
import interfaces.IRide;
import interfaces.ITimeSlot;

/**
 * Represents the common attributes and behavior of a ride
 * in the ride-sharing system.
 * <p>
 * This abstract class serves as the base class for PrivateRide
 * and SharedRide. Rides are immutable and compared by ride ID.
 * </p>
 */
public abstract class Ride implements IRide {

    private final int rideId;
    private final String pickupLocation;
    private final String dropoffLocation;
    private final ITimeSlot timeSlot;
    private final IDriver driver;

    /**
     * Constructs a ride with the specified information.
     *
     * @param rideId          the ride's unique identifier
     * @param pickupLocation  the pickup location
     * @param dropoffLocation the drop-off location
     * @param timeSlot        the scheduled time slot [pickup, drop-off)
     * @param driver          the assigned driver
     * @throws IllegalArgumentException if timeSlot or driver is null
     */
    public Ride(int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, IDriver driver) {
        if (timeSlot == null)
            throw new IllegalArgumentException("Time slot cannot be null.");
        if (driver == null)
            throw new IllegalArgumentException("Driver cannot be null.");

        this.rideId = rideId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.timeSlot = timeSlot;
        this.driver = driver;
    }

    @Override
    public abstract boolean hasRider(int riderId);

    /**
     * Compares this ride with another ride by ride ID.
     *
     * @param other the ride to compare against
     * @return a negative value, zero, or a positive value
     */
    @Override
    public int compareTo(IRide other) {
        return Integer.compare(this.rideId, other.getRideId());
    }

    @Override
    public int getRideId() {
        return rideId;
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
    public ITimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public IDriver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return String.format("Ride ID: %d, Driver: %d - %s, Pickup: %s, Pickup Time: %s, Drop-off: %s, Drop-off Time: %s",
                rideId, driver.getId(), driver.getName(), pickupLocation,
                timeSlot.getStart().format(), dropoffLocation, timeSlot.getEnd().format());
    }
}
