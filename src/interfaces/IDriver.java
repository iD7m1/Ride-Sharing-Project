package interfaces;
/**
 * A driver. Drivers are compared by ID.
 */
public interface IDriver extends IPerson, Comparable<IDriver> {

    // Returns the vehicle plate: 3 uppercase letters followed by 4 digits (e.g., "ABC1234").
    // The constructor throws IllegalArgumentException for any other format.
    String getVehiclePlate();

    // Returns the vehicle type.
    VehicleType getVehicleType();

    // Compares drivers by ID (negative, zero, or positive).
    @Override
    int compareTo(IDriver other);
}
