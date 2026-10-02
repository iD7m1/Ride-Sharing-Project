package model.persons;
import interfaces.IDriver;
import interfaces.VehicleType;

public class Driver extends Person implements IDriver {
    private String vehiclePlate;
    private VehicleType vehicleType;

    public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType) {
        super(id, name, phoneNumber);
        setVehiclePlate(vehiclePlate);
        setVehicleType(vehicleType);
    }

    // The plate must follow the format of exactly 3 uppercase letters followed by 4 digits
    @Override
    public void setVehiclePlate(String vehiclePlate) {
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
        return null;
    }

    // Compares this rider with another rider based on rider ID.
    @Override
    public int compareTo(IDriver other) {
        return 0;
    }
}
