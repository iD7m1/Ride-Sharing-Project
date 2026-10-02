package model.rides;
import datastructures.LinkedList;
import interfaces.IDateTime;
import interfaces.IDriver;
import interfaces.IRider;
import interfaces.ISharedRide;

public class SharedRide extends Ride implements ISharedRide {
    private LinkedList<IRider> participants;

    public SharedRide(int rideId, IDateTime pickupTime, IDateTime dropoffTime, String pickupLocation, String dropoffLocation, IDriver driver) {
        super(rideId, pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);
        participants = new LinkedList<IRider>();
    }

    // Checks whether a rider participates in this ride.
    @Override
    public boolean hasRider(int riderId) {
        return false;
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return participants;
    }

    // Adds a rider to the shared ride.
    @Override
    public boolean addParticipant(IRider rider) {
        return false;
    }

    // Removes a rider from the shared ride by ID.
    @Override
    public boolean removeParticipantById(int riderId) {
        return false;
    }

    // Returns true if the shared ride has no participants.
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

}
