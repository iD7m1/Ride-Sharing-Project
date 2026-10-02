package model.rides;
import interfaces.IDateTime;
import interfaces.IDriver;
import interfaces.IPrivateRide;
import interfaces.IRider;

public class PrivateRide extends Ride implements IPrivateRide {
    private IRider rider;

    public PrivateRide(int rideId, IDateTime pickupTime, IDateTime dropoffTime, String pickupLocation, String dropoffLocation, IDriver driver, IRider rider) {
        super(rideId, pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);
        setRider(rider);
    }

    // Checks whether a rider participates in this ride.
    @Override
    public boolean hasRider(int riderId) {
        return false;
    }

    @Override
    public IRider getRider() {
        return rider;
    }

    @Override
    public void setRider(IRider rider) {
        this.rider = rider;
    }

    @Override
    public String toString() {
        return null;
    }
}
