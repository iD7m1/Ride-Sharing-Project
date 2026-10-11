package model.persons;

import datastructures.LinkedList;
import interfaces.IPerson;
import interfaces.IRide;

public abstract class Person implements IPerson {
    private final int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    public Person(int id, String name, String phoneNumber) throws IllegalArgumentException {
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
     * Sets the phone number of this person.
     * The phone number must be exactly 10 digits (numeric characters only,
     * e.g., "0551234567"). Implementations must validate the format and
     * throw IllegalArgumentException if it does not match.
     */
    @Override
    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber == null || !phoneNumber.matches("05\\d{8}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
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
