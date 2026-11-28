package model.rides;

import datastructures.BST;
import datastructures.LinkedList;
import interfaces.IBST;
import interfaces.IDriver;
import interfaces.IRider;
import interfaces.ISharedRide;
import interfaces.ITimeSlot;

/**
 * Represents a shared ride in the ride-sharing system.
 * <p>
 * Participants are stored in a BST keyed by rider ID.
 * </p>
 */
public class SharedRide extends Ride implements ISharedRide {
    private final IBST<Integer, IRider> participants;

    /**
     * Constructs a shared ride with an initially empty
     * participant BST.
     *
     * @param rideId          the ride's unique identifier
     * @param pickupLocation  the pickup location
     * @param dropoffLocation the drop-off location
     * @param timeSlot        the scheduled time slot
     * @param driver          the assigned driver
     * @throws IllegalArgumentException if the inherited ride information is invalid
     */
    public SharedRide (int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, IDriver driver) {
        super(rideId, pickupLocation, dropoffLocation, timeSlot, driver);
        this.participants = new BST<Integer, IRider>();
    }

    @Override
    public boolean hasRider(int riderId) {
        return participants.findKey(riderId);
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return participants.inOrder();
    }

    @Override
    public boolean addParticipant(IRider rider) {
        return participants.insert(rider.getId(), rider);
    }

    @Override
    public boolean removeParticipantById (int riderId) {
        return participants.removeKey(riderId);
    }

    @Override
    public boolean isEmpty () {
        return participants.empty();
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder(super.toString());

        LinkedList<IRider> list = getParticipants();

        if (list.empty()) {
            sb.append("\nRiders: None");
            return sb.toString();
        }

        sb.append("\nRiders:");

        list.findFirst();

        while (true) {
            IRider rider = list.retrieve();

            sb.append("\nID: ").append(rider.getId()).append(", Name: ").append(rider.getName());

            if (list.last())
                break;

            list.findNext();
        }

        return sb.toString();
    }
}
