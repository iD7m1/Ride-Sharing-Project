package system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import datastructures.LinkedList;
import interfaces.*;
import lists.DriverList;
import lists.RideList;
import lists.RiderList;
import model.DateTime;
import model.persons.Driver;
import model.persons.Rider;

public class RideSharingSystem implements IRideSharingSystem {

    private IRiderList riderList;
    private IDriverList driverList;
    private IRideList rideList;
    private int nextRideId;

    public RideSharingSystem() {
        this.riderList = new RiderList();
        this.driverList = new DriverList();
        this.rideList = new RideList();
        this.nextRideId = 1;
    }

    private IDateTime parseDateTime(String dateTimeStr) {
        String[] parts = dateTimeStr.trim().split(" ");
        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");

        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        return new DateTime(year, month, day, hour, minute);
    }

    @Override
    public boolean loadRidersFromCSV(String ridersFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                String email = tokens[2].trim();
                String phone = tokens[3].trim();
                String city = tokens[4].trim();

                IRider rider = new Rider(id, name, phone, email, city);
                addRider(rider);
            }
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean loadDriversFromCSV(String driversFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(driversFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                String phone = tokens[2].trim();
                String plate = tokens[3].trim();
                VehicleType vehicleType = VehicleType.valueOf(tokens[4].trim());

                IDriver driver = new Driver(id, name, phone, plate, vehicleType);
                addDriver(driver);
            }
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean loadRidesFromCSV(String ridesFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                String rideType = tokens[0].trim();
                String pickupLoc = tokens[1].trim();
                IDateTime pickupTime = parseDateTime(tokens[2]);
                IDateTime dropoffTime = parseDateTime(tokens[3]);
                String dropoffLoc = tokens[4].trim();
                int driverId = Integer.parseInt(tokens[5].trim());

                if (rideType.equalsIgnoreCase("PRIVATE")) {
                    int riderId = Integer.parseInt(tokens[6].trim());
                    schedulePrivateRide(pickupLoc, pickupTime, dropoffTime, dropoffLoc, riderId, driverId);
                } else if (rideType.equalsIgnoreCase("SHARED")) {
                    String[] idStrs = tokens[6].trim().split(";");
                    int[] riderIds = new int[idStrs.length];
                    for (int i = 0; i < idStrs.length; i++) {
                        riderIds[i] = Integer.parseInt(idStrs[i].trim());
                    }
                    scheduleSharedRide(pickupLoc, pickupTime, dropoffTime, dropoffLoc, riderIds, driverId);
                }
            }
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean addRider(IRider rider) {
        if (rider == null) return false;
        return riderList.add(rider);
    }

    @Override
    public boolean addDriver(IDriver driver) {
        if (driver == null) return false;
        return driverList.add(driver);
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
