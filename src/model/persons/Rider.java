package model.persons;

import interfaces.IRider;

/**
 * Represents a rider in the ride-sharing system.
 * <p> Inherits common personal information and ride history
 * from Person. Each rider also has a unique email address
 * and a home city.Riders are compared by their IDs in ascending order. </p>
 * <p> ID & Email uniqueness is enforced by RiderList and
 * RideSharingSystem when riders are added. </p>
 */
public class Rider extends Person implements IRider {

    private String email;
    private String homeCity;

    /**
     * Constructs a rider with the specified information.
     *
     * @param id          the rider's unique identifier
     * @param name        the rider's name
     * @param phoneNumber the rider's 10-digit phone number
     * @param email       the rider's unique email address
     * @param homeCity    the rider's home city
     * @throws IllegalArgumentException if the phone number is invalid or the email is null
     */
    public Rider (int id, String name, String phoneNumber, String email, String homeCity) throws IllegalArgumentException {
        super(id, name, phoneNumber);
        setEmail(email);
        setHomeCity(homeCity);
    }

    /**
     * Updates the rider's email address.
     *
     * The email must not be null. Email uniqueness is
     * checked by the list or system when a rider is added.
     *
     * @param email the new email address
     * @throws IllegalArgumentException if the email is null
     */
    @Override
    public void setEmail (String email) {
        if (email == null) throw new IllegalArgumentException ("Email can't be null");
        this.email = email;
    }

    @Override
    public void setHomeCity (String homeCity) {
        this.homeCity = homeCity;
    }

    @Override
    public String getEmail () {
        return email;
    }

    @Override
    public String getHomeCity () {
        return homeCity;
    }

    @Override
    public String toString () {
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
        if (this.getId() < other.getId()) {
            return -1;
        } else if (this.getId() > other.getId()) {
            return 1;
        } else {
            return 0;
        }
    }
}
