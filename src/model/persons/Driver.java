package model.persons;

import interfaces.IDriver;
import interfaces.VehicleType;

public class Driver extends Person implements IDriver {
    private String vehiclePlate;
    private VehicleType vehicleType;

    public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType)
            throws IllegalArgumentException {
        super(id, name, phoneNumber);
        setVehiclePlate(vehiclePlate);
        setVehicleType(vehicleType);
    }

    /**
     * Sets the driver's vehicle plate number.
     * The plate must follow the format of exactly 3 uppercase letters
     * followed by 4 digits (e.g., "ABC1234"). Implementations must
     * validate the format and throw IllegalArgumentException if it does
     * not match. Vehicle plate numbers must be unique across all drivers
     * in the system; enforcing that uniqueness is the responsibility of
     * IDriverList/IRideSharingSystem when a driver is added.
     */
    @Override
    public void setVehiclePlate(String vehiclePlate) throws IllegalArgumentException {
        if (vehiclePlate == null || !vehiclePlate.matches("[A-Z]{3}\\d{4}")) {
            throw new IllegalArgumentException(
                    "Vehicle plate must be exactly 3 uppercase letters followed by 4 digits.");
        }
        this.vehiclePlate = vehiclePlate;
    }

    @Override
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    @Override
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return super.toString() + ", Vehicle Plate: " + vehiclePlate + ", Vehicle Type: " + vehicleType;
    }

    /**
     * Compares this driver with another driver based on driver ID.
     * Returns a negative integer, zero, or a positive integer as this
     * driver's ID is less than, equal to, or greater than the other
     * driver's ID. This ordering must be consistent with equality by ID.
     */
    @Override
    public int compareTo(IDriver other) {
        if (this.getId() < other.getId()) {
            return -1;
        } else if (this.getId() > other.getId()) {
            return 1;
        } else {
            return 0;
        }
    }
}
