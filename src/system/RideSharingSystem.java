package system;

import datastructures.LinkedList;
import interfaces.*;

public class RideSharingSystem implements IRideSharingSystem {

    @Override
    public boolean loadRidersFromCSV(String ridersFilePath) {
        return false;
    }

    @Override
    public boolean loadDriversFromCSV(String driversFilePath) {
        return false;
    }

    @Override
    public boolean loadRidesFromCSV(String ridesFilePath) {
        return false;
    }

    @Override
    public boolean addRider(IRider rider) {
        return false;
    }

    @Override
    public boolean addDriver(IDriver driver) {
        return false;
    }

    @Override
    public IRider searchRiderById(int riderId) {
        return null;
    }

    @Override
    public IRider searchRiderByEmail(String email) {
        return null;
    }

    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        return null;
    }

    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return null;
    }

    @Override
    public LinkedList<IRider> getAllRiders() {
        return null;
    }

    @Override
    public IDriver searchDriverById(int driverId) {
        return null;
    }

    @Override
    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return null;
    }

    @Override
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return null;
    }

    @Override
    public LinkedList<IDriver> getAllDrivers() {
        return null;
    }

    @Override
    public boolean removeRider(int riderId) {
        return false;
    }

    @Override
    public boolean removeDriver(int driverId) {
        return false;
    }

    @Override
    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId) {
        return false;
    }

    @Override
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {
        return false;
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return null;
    }

    @Override
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return null;
    }

    @Override
    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
        return null;
    }

    @Override
    public LinkedList<IRide> getAllRidesAlphabetically() {
        return null;
    }
}
