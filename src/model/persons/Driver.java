package model.persons;

import interfaces.IDriver;
import interfaces.VehicleType;

/**
 * Represents a driver in the ride-sharing system.
 *
 * <p>Inherits common personal information and ride history
 * from Person. Each driver also has a unique vehicle plate number
 * and a vehicle type. Drivers are compared by their IDs in ascending order. </p>
 *
 * ID & VehiclePlate uniqueness is enforced by DriverList and
 * RideSharingSystem when Driver are added.
 */
public class Driver extends Person implements IDriver {

    private String vehiclePlate;
    private VehicleType vehicleType;

    /**
     * Constructs a driver with the specified information.
     *
     * @param id           the driver's unique identifier
     * @param name         the driver's name
     * @param phoneNumber  the driver's 10-digit phone number
     * @param vehiclePlate the vehicle unique plate number
     * @param vehicleType  the driver's vehicle type
     * @throws IllegalArgumentException if the phone number or vehicle plate format is invalid
     */
    public Driver (int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType) throws IllegalArgumentException {
        super(id, name, phoneNumber);
        setVehiclePlate(vehiclePlate);
        setVehicleType(vehicleType);
    }

    /**
     * Updates the driver's vehicle plate number.
     * <p> The plate must contain exactly three uppercase letters
     * followed by four digits (e.g., ABC1234). </p>
     *
     * @param vehiclePlate the new vehicle plate number
     * @throws IllegalArgumentException if the plate is null
     *                                  or does not match the required format
     */
    @Override
    public void setVehiclePlate (String vehiclePlate) throws IllegalArgumentException {
        if (vehiclePlate == null || !vehiclePlate.matches("[A-Z]{3}\\d{4}")) {
            throw new IllegalArgumentException("Vehicle plate must be exactly 3 uppercase letters followed by 4 digits.");
        }
        this.vehiclePlate = vehiclePlate;
    }

    /**
     * Updates the driver's vehicle type.
     *
     * @param vehicleType the new vehicle type
     * @throws IllegalArgumentException if the vehicle type is null
     */
    @Override
    public void setVehicleType(VehicleType vehicleType) throws IllegalArgumentException {
        if (vehicleType == null) throw new IllegalArgumentException("Vehicle type can't be null");
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
     * Compares this driver with another driver by ID.
     *
     * @param other the driver to compare against
     * @return a negative value if this driver's ID is smaller,
     *         zero if both IDs are equal, or a positive value
     *         if this driver's ID is greater
     */
    @Override
    public int compareTo(IDriver other) {
        return Integer.compare(this.getId(), other.getId());
    }
}
