package model.persons;
import datastructures.LinkedList;
import interfaces.IPerson;
import interfaces.IRide;

public abstract class Person implements IPerson {
    private final int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    public Person(int id, String name, String phoneNumber) {
        this.id = id;
        setName(name);
        setPhoneNumber(phoneNumber);
        rideHistory = new LinkedList<IRide>();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    // The phone number must be exactly 10 digits (numeric characters only)
    @Override
    public void setPhoneNumber(String phoneNumber) {
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
        return null;
    }
}
