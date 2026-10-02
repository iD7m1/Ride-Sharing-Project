package lists;
import datastructures.LinkedList;
import interfaces.IRide;
import interfaces.IRideList;


public class RideList implements IRideList {
    private LinkedList<IRide> rides;

    @Override
    public boolean addRide(IRide ride) {
        return false;
    }

    @Override
    public boolean removeRideById(int rideId) {
        return false;
    }

    @Override
    public LinkedList<IRide> getAllAlphabetically() {
        return null;
    }

    @Override
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        return null;
    }

    @Override
    public LinkedList<IRide> findByRiderName(String riderFullName) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
