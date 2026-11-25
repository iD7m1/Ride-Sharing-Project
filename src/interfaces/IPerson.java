package interfaces;
/**
 * Fields and behavior shared by riders and drivers.
 */
public interface IPerson {

    // Returns the unique ID of this person.
    int getId();

    // Returns the full name of this person.
    String getName();

    // Returns the phone number of this person.
    String getPhoneNumber();

    // Sets the phone number; throws IllegalArgumentException unless it is exactly 10 digits.
    void setPhoneNumber(String phoneNumber);

    // Returns this person's rides (as a rider or as the driver) in a BST keyed by time slot.
    IBST<ITimeSlot, IRide> getSchedule();

    // Returns a formatted string describing this person.
    @Override
    String toString();
}
