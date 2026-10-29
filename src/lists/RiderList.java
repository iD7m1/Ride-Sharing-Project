package lists;

import datastructures.LinkedList;
import interfaces.IRider;
import interfaces.IRiderList;

/**
 * Manages the collection of riders in the ride-sharing system.
 * <p>
 * Riders are stored in a custom linked list sorted by ID
 * in ascending order. Each rider must have a unique ID
 * and email address.
 *
 * Email addresses and other textual search attributes
 * are compared case-insensitively.
 * <p>
 * Provides operations for adding, searching, removing,
 * and retrieving riders.
 */

public class RiderList implements IRiderList {
    private LinkedList<IRider> riders;

    public RiderList() {
        riders = new LinkedList<IRider>();
    }

    /**
     * Adds a rider while maintaining ascending ID order.
     *
     * Rejects null riders, duplicate IDs, and duplicate
     * email addresses. Email uniqueness is case-insensitive.
     *
     * @param rider the rider to add
     * @return true if the rider was added successfully; false if null or a uniqueness constraint is violated
     */
    @Override
    public boolean add (IRider rider) {
        // Check the uniqueness
        if (rider == null || findById(rider.getId()) != null || findByEmail(rider.getEmail()) != null)
            return false; // Rider is null or already exists

        // Insert the rider in sorted order by ID

        // First rider
        if (riders.empty()) {
            riders.insert(rider);
            return true;
        }

        riders.findFirst();
        // New rider come before first ride
        if (riders.retrieve().compareTo(rider) > 0) {
            riders.insertFirst(rider);
            return true;
        }

        // Find the first rider that comes after the new ride
        while (!riders.last()) {
            riders.findNext();

            if (riders.retrieve().compareTo(rider) > 0) {
                // Go back one element since the "insert" method insert after the current
                riders.findPrevious();
                riders.insert(rider);
                return true;
            }
        }

        // New rider comes after all existing rides
        riders.insert(rider);
        return true;
    }

    /**
     * Searches for a rider by ID.
     *
     * @param riderId the rider ID to search for
     * @return the matching rider, or null if not found
     */
    @Override
    public IRider findById(int riderId) {
        if (riders.empty()) return null;

        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getId() == riderId)
                return riders.retrieve();
            riders.findNext();
        }
        return riders.retrieve().getId() == riderId ? riders.retrieve() : null;
    }

    /**
     * Finds all riders with the specified full name.
     *
     * Name comparisons are case-insensitive.
     *
     * @param fullName the full name to search for
     * @return a list of matching riders, or an empty list if no matches are found
     */
    @Override
    public LinkedList<IRider> findByName(String fullName) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        if (riders.empty())
            return result;

        riders.findFirst();

        while (!riders.last()) {
            if (riders.retrieve().getName().equalsIgnoreCase(fullName)) {
                result.insert(riders.retrieve());
            }
            riders.findNext();
        }

        // Check last element
        if (riders.retrieve().getName().equalsIgnoreCase(fullName)) {
            result.insert(riders.retrieve());
        }
        return result;
    }

    /**
     * Searches for a rider by email address.
     *
     * Email comparisons are case-insensitive.
     *
     * @param email the email address to search for
     * @return the matching rider, or null if not found
     */
    @Override
    public IRider findByEmail(String email) {
        if (riders.empty())
            return null;

        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getEmail().equalsIgnoreCase(email))
                return riders.retrieve();
            riders.findNext();
        }
        return riders.retrieve().getEmail().equalsIgnoreCase(email) ? riders.retrieve() : null;
    }

    /**
     * Finds all riders living in the specified home city.
     *
     * City comparisons are case-insensitive.
     *
     * @param homeCity the home city to search for
     * @return a list of matching riders, or an empty list if no matches are found
     */
    @Override
    public LinkedList<IRider> findByHomeCity(String homeCity) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        if (riders.empty())
            return result;

        riders.findFirst();

        while (!riders.last()) {
            if (riders.retrieve().getHomeCity().equalsIgnoreCase(homeCity)) {
                result.insert(riders.retrieve());
            }
            riders.findNext();
        }

        // Check last element
        if (riders.retrieve().getHomeCity().equalsIgnoreCase(homeCity)) {
            result.insert(riders.retrieve());
        }
        return result;
    }

    @Override
    public LinkedList<IRider> getAll() {
        return riders;
    }

    /**
     * Removes a rider by ID.
     *
     * @param riderId the ID of the rider to remove
     * @return true if the rider was removed; false if no matching rider exists
     */
    @Override
    public boolean removeById(int riderId) {
        if (riders.empty())
            return false;

        riders.findFirst();

        while (!riders.last()) {
            if (riders.retrieve().getId() == riderId) {
                riders.remove();
                return true;
            }
            riders.findNext();
        }
        // Check last element
        if (riders.retrieve().getId() == riderId) {
            riders.remove();
            return true;
        }
        return false;
    }

    /**
     * Removes a rider by email address.
     *
     * Email comparisons are case-insensitive.
     *
     * @param email the email address of the rider to remove
     * @return true if the rider was removed; false if no matching rider exists
     */
    @Override
    public boolean removeByEmail(String email) {
        if (riders.empty())
            return false;

        riders.findFirst();

        while (!riders.last()) {
            if (riders.retrieve().getEmail().equalsIgnoreCase(email)) {
                riders.remove();
                return true;
            }
            riders.findNext();
        }
        // Check last element
        if (riders.retrieve().getEmail().equalsIgnoreCase(email)) {
            riders.remove();
            return true;
        }
        return false;
    }

    /**
     * Removes all riders with the specified full name.
     *
     * Name comparisons are case-insensitive.
     * The traversal accounts for the automatic cursor
     * movement performed by LinkedList.remove().
     *
     * @param fullName the full name of riders to remove
     * @return the number of riders removed
     */
    @Override
    public int removeByName(String fullName) {
        if (riders.empty())
            return 0;

        int count = 0;
        riders.findFirst();

        while (!riders.last()) {
            if (riders.retrieve().getName().equalsIgnoreCase(fullName)) {
                riders.remove();
                count++;
            } else {
                riders.findNext();
            }
        }

        // Check last element
        if (riders.retrieve().getName().equalsIgnoreCase(fullName)) {
            riders.remove();
            count++;
        }
        return count;
    }

    /**
     * Removes all riders living in the specified home city.
     *
     * City comparisons are case-insensitive.
     * The traversal accounts for the automatic cursor
     * movement performed by LinkedList.remove().
     *
     * @param homeCity the home city of riders to remove
     * @return the number of riders removed
     */
    @Override
    public int removeByHomeCity(String homeCity) {
        if (riders.empty())
            return 0;

        int count = 0;
        riders.findFirst();

        while (!riders.last()) {
            if (riders.retrieve().getHomeCity().equalsIgnoreCase(homeCity)) {
                riders.remove();
                count++;
            } else {
                riders.findNext();
            }
        }

        // Check last element
        if (riders.retrieve().getHomeCity().equalsIgnoreCase(homeCity)) {
            riders.remove();
            count++;
        }
        return count;
    }

    /**
     * Returns the number of riders currently stored.
     *
     * @return the total number of riders
     */
    @Override
    public int size() {
        return riders.size();
    }
}
