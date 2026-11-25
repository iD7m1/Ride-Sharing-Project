package model.persons;

import interfaces.IRider;

/**
 * Represents a rider in the ride-sharing system.
 * <p>Inherits common personal information and schedule
 * from Person. Each rider also has an email address
 * and a home city. Riders are compared by their IDs in ascending order.</p>
 */
public class Rider extends Person implements IRider {

    private final String email;
    private final String homeCity;

    /**
     * Constructs a rider with the specified information.
     *
     * @param id          the rider's unique identifier
     * @param name        the rider's name
     * @param email       the rider's email address
     * @param phoneNumber the rider's 10-digit phone number
     * @param homeCity    the rider's home city
     * @throws IllegalArgumentException if the phone number is invalid or the email is null
     */
    public Rider(int id, String name, String email, String phoneNumber, String homeCity) throws IllegalArgumentException {
        super(id, name, phoneNumber);
        if (email == null) {
            throw new IllegalArgumentException("Email can't be null");
        }
        this.email = email;
        this.homeCity = homeCity;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getHomeCity() {
        return homeCity;
    }

    @Override
    public String toString() {
        return super.toString() + ", Email: " + email + ", Home City: " + homeCity;
    }

    /**
     * Compares this rider with another rider by ID.
     *
     * @param other the rider to compare against
     * @return a negative value if this rider's ID is smaller,
     *         zero if both IDs are equal, or a positive value
     *         if this rider's ID is greater
     */
    @Override
    public int compareTo(IRider other) {
        return Integer.compare(this.getId(), other.getId());
    }
}
