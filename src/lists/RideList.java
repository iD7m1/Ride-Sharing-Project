package lists;
import datastructures.LinkedList;
import interfaces.*;

/**
 * Manages the collection of rides in the ride-sharing system.
 * <p>
 * Rides are stored in a linked list and maintained
 * in ascending alphabetical order by pickup location.
 * Each ride must have a unique ride ID.
 * <p>
 * Provides operations for adding, removing, and searching
 * rides by pickup location or participating rider name.
 */

public class RideList implements IRideList {
    private LinkedList<IRide> rides;

    public RideList() {
        rides = new LinkedList<>();
    }

    /**
     * Checks whether a ride with the specified ID exists.
     *
     * @param id the ride ID to search for
     * @return true if the ride exists; false otherwise
     */
    private boolean hasRide (int id) {
        if (rides.empty()) return false;

        rides.findFirst();
        while (true) {
            if (rides.retrieve().getRideId() == id) return true;
            if (rides.last()) break;
            rides.findNext();
        }

        return false;
    }

    /**
     * Adds a ride while preserving alphabetical order
     * by pickup location.
     * <p>
     * Rejects null rides and duplicate ride IDs.
     *
     * @param ride the ride to add
     * @return true if the ride was added successfully; false if it is null or its ID already exists
     */
    @Override
    public boolean addRide(IRide ride)  {
        if (ride == null || hasRide(ride.getRideId())) return false;

        // Insert directly when the list is empty.
        if (rides.empty()) {
            rides.insert(ride);
            return true;
        }

        rides.findFirst();

        // Insert before the first ride.
        if (rides.retrieve().compareTo(ride) > 0) {
            rides.insertFirst(ride);
            return true;
        }

        // Find the first ride that should follow the new ride.
        while (!rides.last()) {
            rides.findNext();

            if (rides.retrieve().compareTo(ride) > 0) {
                // insert() inserts after current, so move back first.
                rides.findPrevious();
                rides.insert(ride);
                return true;
            }
        }

        // Append when the new ride belongs at the end.
        rides.insert(ride);
        return true;
    }

    /**
     * Removes a ride with the specified ID.
     *
     * @param rideId the ID of the ride to remove
     * @return true if the ride was removed; false if not found
     */
    @Override
    public boolean removeRideById(int rideId) {
        if (rides.empty()) return false;

        rides.findFirst();

        while (true) {
            if (rides.retrieve().getRideId() == rideId) {
                rides.remove();
                return true;
            }

            if (rides.last()) break;
            rides.findNext();
        }

        return false;
    }

    @Override
    public LinkedList<IRide> getAllAlphabetically() {
        return rides;
    }

    /**
     * Finds all rides with the specified pickup location.
     * <p>
     * The comparison is case-insensitive. Matching rides
     * are returned in their existing alphabetical order.
     *
     * @param pickupLocation the pickup location to search for
     * @return a list of matching rides, or an empty list
     *         if no matches are found
     */
    @Override
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        LinkedList<IRide> result = new LinkedList<>();
        if (rides.empty()) return result;

        rides.findFirst();

        while (true) {
            if (rides.retrieve().getPickupLocation().equalsIgnoreCase(pickupLocation)) result.insert(rides.retrieve());
            if (rides.last()) break;
            rides.findNext();
        }

        return result;
    }

    /**
     * Finds all rides containing a rider with the specified name.
     * <p>
     * For private rides, checks the assigned rider.
     * For shared rides, searches the participant list.
     * <p>
     * Name comparisons are case-insensitive. Each matching
     * ride is included only once, even if multiple participants
     * in the same shared ride have the requested name.
     *
     * @param riderFullName the full name of the rider
     * @return a list of matching rides, or an empty list
     *         if no matches are found
     */
    @Override
    public LinkedList<IRide> findByRiderName(String riderFullName) {
        LinkedList<IRide> result = new LinkedList<>();
        // Empty list case
        if (rides.empty()) return result;
        // Move the current to the first ride
        rides.findFirst();
        // Iterate through the rides except the last one
        while (!rides.last()) {
            IRide ride = rides.retrieve();
            // Case 1 - Ride is a PrivateRide
            if (ride instanceof IPrivateRide) {
                if (((IPrivateRide) ride).getRider().getName().equalsIgnoreCase(riderFullName)) {
                    result.insert(ride);
                }
            }
            // Case 2 - Ride is a shared-ride
            else if (ride instanceof ISharedRide) {
                // Get all riders
                LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
                boolean found = false;
                // Ensure there's at least one rider
                if (!participants.empty()) {
                    // Move the current to the first rider
                    participants.findFirst();
                    // Iterate through the riders except the last one
                    while (!participants.last()) {
                        if (participants.retrieve().getName().equalsIgnoreCase(riderFullName)) {
                            // The given name is found, go out of the loop
                            found = true;
                            break;
                        }
                        participants.findNext();
                    }
                    // Check the last rider only if not already found
                    if (!found && participants.retrieve().getName().equalsIgnoreCase(riderFullName)) {
                        found = true;
                    }
                    // add this ride to the result if the given name is found
                    if (found) result.insert(ride);
                }
            }
            rides.findNext();
        }
        // Check the Last ride
        IRide ride = rides.retrieve();
        // Case 1 - Ride is a PrivateRide
        if (ride instanceof IPrivateRide) {
            if (((IPrivateRide) ride).getRider().getName().equalsIgnoreCase(riderFullName)) {
                result.insert(ride);
            }
        }
        // Case 2 - Ride is a shared-ride
        else if (ride instanceof ISharedRide) {
            // Get all riders
            LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
            boolean found = false;
            // Ensure there's at least one rider
            if (!participants.empty()) {
                // Move the current to the first rider
                participants.findFirst();
                // Iterate through the riders except the last one
                while (!participants.last()) {
                    if (participants.retrieve().getName().equalsIgnoreCase(riderFullName)) {
                        // The given name is found, go out of the loop
                        found = true;
                        break;
                    }
                    participants.findNext();
                }
                // Check the last rider only if not already found
                if (!found && participants.retrieve().getName().equalsIgnoreCase(riderFullName)) {
                    found = true;
                }
                // add this ride to the result if the given name is found
                if (found) result.insert(ride);
            }
        }
        return result;
    }

    @Override
    public int size() {
        return rides.size();
    }
}
