package model.persons;
import datastructures.LinkedList;
import interfaces.IPerson;
import interfaces.IRide;

/**
 * Represents the common attributes and behavior of a person
 * in the ride-sharing system.
 *
 * <p>This abstract class serves as the base class for Rider
 * and Driver. Each person has a unique identifier, a name,
 * a phone number, and a history of associated rides.</p>
 *
 * <p>The identifier cannot be changed after construction.
 * Phone numbers must contain exactly 10 numeric digits.</p>
 *
 * ID uniqueness is enforced by DriverList, RiderList and
 * RideSharingSystem when records are added.
 */

public abstract class Person implements IPerson {

    private final int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    /**
     * a person with the specified information.
     *
     * @param id          the person's identifier
     * @param name        the person's name
     * @param phoneNumber the person's 10-digit phone number
     * @throws IllegalArgumentException if the phone number is invalid
     */
    public Person (int id, String name, String phoneNumber) throws IllegalArgumentException {
        this.id = id;
        setName(name);
        setPhoneNumber(phoneNumber);
        rideHistory = new LinkedList<IRide>();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the person's phone number.
     *
     * <p>The number must contain exactly 10 digits (0-9).
     * No spaces, letters, or other characters are permitted.</p>
     *
     * @param phoneNumber the new phone number
     * @throws IllegalArgumentException if the value is null or does not contain exactly 10 digits
     *
     */
    @Override
    public void setPhoneNumber (String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        }
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public LinkedList<IRide> getRideHistory() {
        return rideHistory;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber;
    }
}
