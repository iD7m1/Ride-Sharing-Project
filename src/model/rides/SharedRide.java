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
        if (participants.empty()) return false;
        // Move the current to the first element
        participants.findFirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) return true;
            // Move the current one position
            participants.findNext();
        }
        // Check the last element
        return participants.retrieve().getId() == riderId;
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return participants;
    }

    // Adds a rider to the shared ride.
    @Override
    public boolean addParticipant(IRider rider) {
        // Check the uniqueness
        if (rider == null || hasRider(rider.getId())) return false;
        participants.insert(rider);
        return true;
    }

    // Removes a rider from the shared ride by ID.
    @Override
    public boolean removeParticipantById(int riderId) {
        if (participants.empty()) return false;
        // Move the current to the first node
        participants.findFirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) {
                participants.remove();
                return true;
            }
            // Move the current one position
            participants.findNext();
        }
        // Check the last element
        if (participants.retrieve().getId() == riderId) {
            participants.remove();
            return true;
        }
        return false;
    }

    // Returns true if the shared ride has no participants.
    @Override
    public boolean isEmpty() {
        return participants.empty();
    }

    @Override
    public String toString() {
        StringBuilder riders = new StringBuilder("Riders: ");

        if (participants.empty())
            return String.format("%s, Riders: None", super.toString());

        participants.findFirst();

        while (!participants.last()) {
            riders.append(String.format("%s (ID: %d) | ", participants.retrieve().getName(), participants.retrieve().getId()));
            participants.findNext();
        }

        riders.append(String.format("%s (ID: %d)", participants.retrieve().getName(), participants.retrieve().getId()));
        return String.format("%s, %s", super.toString(), riders);
    }
}
