package model.rides;
import interfaces.IDateTime;
import interfaces.IDriver;
import interfaces.IRide;

public abstract class Ride implements IRide {
    private final int rideId;
    private final IDateTime pickupTime;
    private final IDateTime dropoffTime;

    private String pickupLocation;
    private String dropoffLocation;
    private IDriver driver;

    public Ride(int rideId, IDateTime pickupTime, IDateTime dropoffTime, String pickupLocation, String dropoffLocation, IDriver driver) {
        this.rideId = rideId;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        setDropoffLocation(dropoffLocation);
        setPickupLocation(pickupLocation);
        setDriver(driver);
    }

    // Checks whether a rider participates in this ride.
    @Override
    public abstract boolean hasRider(int riderId);

    // Compares rides alphabetically by pickup location.
    @Override
    public int compareTo(IRide other) {
        return 0;
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
        return null;
    }


}
