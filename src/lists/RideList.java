package lists;
import datastructures.LinkedList;
import interfaces.*;

public class RideList implements IRideList {
    private LinkedList<IRide> rides;

    public RideList() {
        rides = new LinkedList<>();
    }

    // Check if a ride exists
    private boolean hasRide (int id) {
        if (rides.empty()) return false;

        rides.findFirst();
        while (!rides.last()) {
            if (rides.retrieve().getRideId() == id) return true;
            rides.findNext();
        }
        return rides.retrieve().getRideId() == id;
    }

    @Override
    public boolean addRide(IRide ride) {
        // Check the uniqueness
        if (ride == null || hasRide(ride.getRideId())) return false;

        // First ride
        if (rides.empty()) {
            rides.insert(ride);
            return true;
        }

        rides.findFirst();
        // New ride come before first ride
        if (rides.retrieve().compareTo(ride) > 0) {
            rides.insertFirst(ride);
            return true;
        }

        // Find the first ride that comes after the new ride
        while (!rides.last()) {
            rides.findNext();

            if (rides.retrieve().compareTo(ride) > 0) {
                // Go back one element since the "insert" method insert after the current
                rides.findPrevious();
                rides.insert(ride);
                return true;
            }
        }

        // New ride comes after all existing rides
        rides.insert(ride);
        return true;
    }

    @Override
    public boolean removeRideById(int rideId) {
        if (rides.empty()) return false;

        rides.findFirst();

        while (!rides.last()) {
            if (rides.retrieve().getRideId() == rideId) {
                rides.remove();
                return true;
            }
            rides.findNext();
        }
        // Check last element
        if (rides.retrieve().getRideId() == rideId) {
            rides.remove();
            return true;
        }
        return false;
    }

    @Override
    public LinkedList<IRide> getAllAlphabetically() {
        return rides;
    }

    @Override
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        LinkedList<IRide> result = new LinkedList<>();
        if (rides.empty()) return result;

        rides.findFirst();

        while (!rides.last()) {
            if (rides.retrieve().getPickupLocation().equalsIgnoreCase(pickupLocation)) {
                result.insert(rides.retrieve());
            }
            rides.findNext();
        }

        // Check last element
        if (rides.retrieve().getPickupLocation().equalsIgnoreCase(pickupLocation)) {
            result.insert(rides.retrieve());
        }
        return result;
    }

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
