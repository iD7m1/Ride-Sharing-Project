package interfaces;
import datastructures.LinkedList;
/**
 * A shared ride. Participants are stored in a BST keyed by rider ID.
 */
public interface ISharedRide extends IRide {

	// Returns the participants sorted by rider ID.
	LinkedList<IRider> getParticipants();

	// Adds a rider; returns false if already a participant. O(h).
	boolean addParticipant(IRider rider);

	// Removes a rider by ID; returns false if not a participant. O(h).
	boolean removeParticipantById(int riderId);

	// Returns true if the ride has no participants.
	boolean isEmpty();
}
