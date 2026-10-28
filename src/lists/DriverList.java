package lists;
import datastructures.LinkedList;
import interfaces.IDriver;
import interfaces.IDriverList;
import interfaces.VehicleType;

/**
 * Manages the collection of drivers in the ride-sharing system.
 *
 * Drivers are stored in a custom linked list sorted by ID
 * in ascending order. Each driver must have a unique ID
 * and vehicle plate.
 * <p>
 * Provides operations for adding, searching, removing,
 * and retrieving drivers.
 */
public class DriverList implements IDriverList {

    private LinkedList<IDriver> drivers;

    public DriverList() {
        drivers = new LinkedList<IDriver>();
    }

    /**
     * Adds a driver while maintaining ascending ID order.
     *
     * Rejects null drivers, duplicate IDs, and duplicate
     * vehicle plates.
     *
     * @param driver the driver to add
     * @return true if the driver was added successfully; false if the driver is null or violates uniqueness
     */
    @Override
    public boolean add(IDriver driver) {
        // Check the uniqueness
        if (driver == null || findById(driver.getId()) != null || findByVehiclePlate(driver.getVehiclePlate()) != null)
            return false; // Driver is null or already exists

        // Insert the driver in sorted order by ID

        // First driver
        if (drivers.empty()) {
            drivers.insert(driver);
            return true;
        }

        drivers.findFirst();
        // New driver comes before the first driver
        if (drivers.retrieve().compareTo(driver) > 0) {
            drivers.insertFirst(driver);
            return true;
        }

        // Find the first driver that comes after the new driver
        while (!drivers.last()) {
            drivers.findNext();

            if (drivers.retrieve().compareTo(driver) > 0) {
                // Go back one element since the "insert" method inserts after the current
                drivers.findPrevious();
                drivers.insert(driver);
                return true;
            }
        }

        // New driver comes after all existing drivers
        drivers.insert(driver);
        return true;
    }

    /**
     * Searches for a driver by ID.
     *
     * @param driverId the ID to search for
     * @return the matching driver, or null if not found
     */
    @Override
    public IDriver findById(int driverId) {
        if (drivers.empty()) return null;

        drivers.findFirst();
        while (!drivers.last()) {
            if (drivers.retrieve().getId() == driverId)
                return drivers.retrieve();
            drivers.findNext();
        }
        // Check last element
        if (drivers.retrieve().getId() == driverId)
            return drivers.retrieve();
        else
            return null;
    }

    /**
     * Finds all drivers with the specified full name.
     * Name comparisons are case-insensitive.
     *
     * @param fullName the full name to search for
     * @return a list of matching drivers, or an empty list
     *         if no matches are found
     */
    @Override
    public LinkedList<IDriver> findByName(String fullName) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();
        if (drivers.empty())
            return result;

        drivers.findFirst();

        while (!drivers.last()) {
            if (drivers.retrieve().getName().equalsIgnoreCase(fullName)) {
                result.insert(drivers.retrieve());
            }
            drivers.findNext();
        }

        // Check last element
        if (drivers.retrieve().getName().equalsIgnoreCase(fullName)) {
            result.insert(drivers.retrieve());
        }
        return result;
    }

    /**
     * Searches for a driver by vehicle plate.
     *
     * Vehicle plates are compared exactly and
     * case-sensitively.
     *
     * @param vehiclePlate the vehicle plate to search for
     * @return the matching driver, or null if not found
     */
    @Override
    public IDriver findByVehiclePlate(String vehiclePlate) {
        if (drivers.empty())
            return null;

        drivers.findFirst();
        while (!drivers.last()) {
            if (drivers.retrieve().getVehiclePlate().equals(vehiclePlate))
                return drivers.retrieve();
            drivers.findNext();
        }
        // Check last element
        if (drivers.retrieve().getVehiclePlate().equals(vehiclePlate))
            return drivers.retrieve();
        else
            return null;
    }

    /**
     * Finds all drivers with the specified vehicle type.
     *
     * @param vehicleType the vehicle type to search for
     * @return a list of matching drivers, or an empty list if no matches are found
     */
    @Override
    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();
        if (drivers.empty())
            return result;

        drivers.findFirst();

        while (!drivers.last()) {
            // Enum values are unique objects, so == is enough
            if (drivers.retrieve().getVehicleType() == vehicleType) {
                result.insert(drivers.retrieve());
            }
            drivers.findNext();
        }

        // Check last element
        if (drivers.retrieve().getVehicleType() == vehicleType) {
            result.insert(drivers.retrieve());
        }
        return result;
    }

    @Override
    public LinkedList<IDriver> getAll() {
        return drivers;
    }

    /**
     * Removes a driver by ID.
     *
     * @param driverId the ID of the driver to remove
     * @return true if the driver was removed; false if no matching driver exists
     */
    @Override
    public boolean removeById(int driverId) {
        if (drivers.empty())
            return false;

        drivers.findFirst();

        while (!drivers.last()) {
            if (drivers.retrieve().getId() == driverId) {
                drivers.remove();
                return true;
            }
            drivers.findNext();
        }
        // Check last element
        if (drivers.retrieve().getId() == driverId) {
            drivers.remove();
            return true;
        }
        return false;
    }

    /**
     * Removes a driver by vehicle plate.
     *
     * Vehicle plates are compared exactly and
     * case-sensitively.
     *
     * @param vehiclePlate the vehicle plate to search for
     * @return true if the driver was removed; false if no matching driver exists
     */
    @Override
    public boolean removeByVehiclePlate(String vehiclePlate) {
        if (drivers.empty())
            return false;

        drivers.findFirst();

        while (!drivers.last()) {
            if (drivers.retrieve().getVehiclePlate().equals(vehiclePlate)) {
                drivers.remove();
                return true;
            }
            drivers.findNext();
        }
        // Check last element
        if (drivers.retrieve().getVehiclePlate().equals(vehiclePlate)) {
            drivers.remove();
            return true;
        }
        return false;
    }

    /**
     * Removes all drivers with the specified full name.
     *
     * Name comparisons are case-insensitive.
     * The traversal accounts for the cursor movement
     * performed automatically by LinkedList.remove().
     *
     * @param fullName the full name of the drivers to remove
     * @return the number of drivers removed
     */
    @Override
    public int removeByName(String fullName) {
        if (drivers.empty())
            return 0;

        int count = 0;
        drivers.findFirst();

        while (!drivers.last()) {
            if (drivers.retrieve().getName().equalsIgnoreCase(fullName)) {
                // remove() already moves the cursor to the next element
                drivers.remove();
                count++;
            } else {
                drivers.findNext();
            }
        }

        // Check last element
        if (drivers.retrieve().getName().equalsIgnoreCase(fullName)) {
            drivers.remove();
            count++;
        }
        return count;
    }

    /**
     * Removes all drivers with the specified vehicle type.
     *
     * The traversal accounts for the cursor movement
     * performed automatically by LinkedList.remove().
     *
     * @param vehicleType the vehicle type of drivers to remove
     * @return the number of drivers removed
     */
    @Override
    public int removeByVehicleType(VehicleType vehicleType) {
        if (drivers.empty())
            return 0;

        int count = 0;
        drivers.findFirst();

        while (!drivers.last()) {
            if (drivers.retrieve().getVehicleType() == vehicleType) {
                // remove() already moves the cursor to the next element
                drivers.remove();
                count++;
            } else {
                drivers.findNext();
            }
        }

        // Check last element
        if (drivers.retrieve().getVehicleType() == vehicleType) {
            drivers.remove();
            count++;
        }
        return count;
    }

    /**
     * Returns the number of drivers currently stored.
     *
     * @return the total number of drivers
     */
    @Override
    public int size() {
        return drivers.size();
    }
}
