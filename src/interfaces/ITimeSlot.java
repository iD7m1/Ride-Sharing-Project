package interfaces;
/**
 * A half-open time interval [start, end): start is the pickup date/time and
 * end is the drop-off date/time.
 */
public interface ITimeSlot extends Comparable<ITimeSlot> {

    // Returns the start of the interval (pickup date/time).
    IDateTime getStart();

    // Returns the end of the interval (drop-off date/time).
    IDateTime getEnd();

    // Returns a negative value if this slot ends at or before other starts, a positive value
    // if this slot starts at or after other ends, and 0 if the two slots overlap.
    @Override
    int compareTo(ITimeSlot other);
}
