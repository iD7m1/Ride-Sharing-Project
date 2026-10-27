package model.rides;
import datastructures.LinkedList;
import interfaces.IDateTime;
import interfaces.IDriver;
import interfaces.IRider;
import interfaces.ISharedRide;

/**
 * Represents a shared ride in the ride-sharing system.
 * <p>
 * A shared ride allows multiple riders to participate in
 * the same ride. Participants are stored in a linked
 * list, and each rider may appear only once based on their ID.
 * </p>
 * Inherits the ride ID, scheduled times, pickup and drop-off
 * locations, and assigned driver from Ride.
 */
public class SharedRide extends Ride implements ISharedRide {
    private LinkedList<IRider> participants;

    /**
     * Constructs a shared ride with an initially empty
     * participant list.
     *
     * @param rideId          the ride's unique identifier
     * @param pickupTime      the scheduled pickup time
     * @param dropoffTime     the scheduled drop-off time
     * @param pickupLocation  the pickup location
     * @param dropoffLocation the drop-off location
     * @param driver          the assigned driver
     * @throws IllegalArgumentException if the inherited ride
     *                                  information is invalid
     */
    public SharedRide (int rideId, IDateTime pickupTime, IDateTime dropoffTime, String pickupLocation, String dropoffLocation, IDriver driver) {
        super(rideId, pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);
        participants = new LinkedList<IRider>();
    }

    /**
     * Checks whether a rider participates in this shared ride.
     *
     * Searches the participant list by rider ID.
     *
     * @param riderId the ID of the rider to search for
     * @return true if the rider participates; false otherwise
     */
    @Override
    public boolean hasRider(int riderId) {
        if (participants.empty()) return false;

        participants.findFirst();

        while (true) {
            if (participants.retrieve().getId() == riderId) return true;
            if (participants.last()) break;
            participants.findNext();
        }

        return false;
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return participants;
    }

    /**
     * Adds a rider to this shared ride.
     *
     * A rider cannot be null or already present in the
     * participant list. Uniqueness is determined by rider ID.
     *
     * @param rider the rider to add
     * @return true if the rider was added successfully; false if the rider is null or already participates
     */
    @Override
    public boolean addParticipant (IRider rider) {
        if (rider == null || hasRider(rider.getId())) return false;
        participants.insert(rider);
        return true;
    }
    /**
     * Removes a participant from this shared ride by ID.
     *
     * @param riderId the ID of the rider to remove
     * @return true if the rider was found and removed; false if the rider does not participate
     */
    @Override
    public boolean removeParticipantById(int riderId) {
        if (participants.empty()) return false;

        participants.findFirst();

        while (true) {
            if (participants.retrieve().getId() == riderId) {
                participants.remove();
                return true;
            }
            if (participants.last()) break;
            participants.findNext();
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return participants.empty();
    }

    @Override
    public String toString() {
        if (participants.empty()) return String.format("%s, Riders: None", super.toString());

        StringBuilder riders = new StringBuilder("Riders: ");
        participants.findFirst();

        while (true) {
            riders.append(String.format("%s (ID: %d)", participants.retrieve().getName(), participants.retrieve().getId()));

            if (participants.last()) break;

            riders.append(" | ");
            participants.findNext();
        }

        return String.format("%s, %s", super.toString(), riders);
    }
}
