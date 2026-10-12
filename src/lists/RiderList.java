package lists;

import datastructures.LinkedList;
import interfaces.IRider;
import interfaces.IRiderList;

public class RiderList implements IRiderList {
    private LinkedList<IRider> riders;

    public RiderList() {
        riders = new LinkedList<IRider>();
    }

    @Override
    public boolean add(IRider rider) {
        // Check the uniqueness
        if (rider == null || (findById(rider.getId()) != null && findByEmail(rider.getEmail()) != null))
            return false; // Rider is null or already exists

        // Insert the rider in sorted order by ID

        // First ride
        if (riders.empty()) {
            riders.insert(rider);
            return true;
        }

        riders.findFirst();
        // New ride come before first ride
        if (riders.retrieve().compareTo(rider) > 0) {
            riders.insertFirst(rider);
            return true;
        }

        // Find the first ride that comes after the new ride
        while (!riders.last()) {
            riders.findNext();

            if (riders.retrieve().compareTo(rider) > 0) {
                // Go back one element since the "insert" method insert after the current
                riders.findPrevious();
                riders.insert(rider);
                return true;
            }
        }

        // New ride comes after all existing rides
        riders.insert(rider);
        return true;
    }

    @Override
    public IRider findById(int riderId) {
        if (riders.empty())
            return null;

        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getId() == riderId)
                return riders.retrieve();
            riders.findNext();
        }
        return riders.retrieve().getId() == riderId ? riders.retrieve() : null;
    }

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

    @Override
    public int size() {
        return riders.size();
    }
}
