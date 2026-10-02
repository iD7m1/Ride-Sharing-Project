package lists;
import datastructures.LinkedList;
import interfaces.IDriver;
import interfaces.IDriverList;
import interfaces.VehicleType;

public class DriverList implements IDriverList {
    private LinkedList<IDriver> drivers;

    public DriverList() {}

    @Override
    public boolean add(IDriver driver) {
        return false;
    }

    @Override
    public IDriver findById(int driverId) {
        return null;
    }

    @Override
    public LinkedList<IDriver> findByName(String fullName) {
        return null;
    }

    @Override
    public IDriver findByVehiclePlate(String vehiclePlate) {
        return null;
    }

    @Override
    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        return null;
    }

    @Override
    public LinkedList<IDriver> getAll() {
        return null;
    }

    @Override
    public boolean removeById(int driverId) {
        return false;
    }

    @Override
    public boolean removeByVehiclePlate(String vehiclePlate) {
        return false;
    }

    @Override
    public int removeByName(String fullName) {
        return 0;
    }

    @Override
    public int removeByVehicleType(VehicleType vehicleType) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }
}
