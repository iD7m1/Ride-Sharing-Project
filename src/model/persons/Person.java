package model.persons;

import datastructures.BST;
import interfaces.IBST;
import interfaces.IPerson;
import interfaces.IRide;
import interfaces.ITimeSlot;

/**
 * Represents the common attributes and behavior of a person
 * in the ride-sharing system.
 *
 * <p>This abstract class serves as the base class for Rider
 * and Driver. Each person has a unique identifier, a name,
 * a phone number, and a schedule of associated rides stored in a BST.</p>
 *
 * <p>The identifier and name cannot be changed after construction.
 * Phone numbers must contain exactly 10 numeric digits.</p>
 */
public abstract class Person implements IPerson {

    private final int id;
    private final String name;
    private String phoneNumber;
    private final IBST<ITimeSlot, IRide> schedule;

    /**
     * Constructs a person with the specified information.
     *
     * @param id          the person's identifier
     * @param name        the person's name
     * @param phoneNumber the person's 10-digit phone number
     * @throws IllegalArgumentException if the phone number is invalid
     */
    public Person(int id, String name, String phoneNumber) throws IllegalArgumentException {
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber);
        this.schedule = new BST<ITimeSlot, IRide>();
    }

    /**
     * Updates the person's phone number.
     *
     * <p>The number must contain exactly 10 digits (0-9).
     * No spaces, letters, or other characters are permitted.</p>
     *
     * @param phoneNumber the new phone number
     * @throws IllegalArgumentException if the value is null or does not contain exactly 10 digits
     */
    @Override
    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
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
    public IBST<ITimeSlot, IRide> getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber;
    }
}
