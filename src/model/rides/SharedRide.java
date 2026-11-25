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
    public SharedRide(int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, IDriver driver) {
        super(rideId, pickupLocation, dropoffLocation, timeSlot, driver);
        this.participants = new BST<Integer, IRider>();
    }

    @Override
    public boolean hasRider(int riderId) {
        // TODO: check if riderId exists in participants BST
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        // TODO: return participants sorted by rider ID via participants.inOrder()
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean addParticipant(IRider rider) {
        // TODO: add rider to participants BST in O(h)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean removeParticipantById(int riderId) {
        // TODO: remove rider from participants BST in O(h)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isEmpty() {
        // TODO: return whether participants BST is empty
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String toString() {
        // TODO: format shared ride with its participants
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
