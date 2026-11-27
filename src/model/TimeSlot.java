package model;

import interfaces.IDateTime;
import interfaces.ITimeSlot;

/**
 * A half-open time interval [start, end) where start is the pickup date/time
 * and end is the drop-off date/time.
 * <p>
 * A time slot is immutable. Two time slots compare as equal (0) when they
 * overlap, which lets a schedule BST detect a conflict with a single search.
 * </p>
 */
public class TimeSlot implements ITimeSlot {

    private final IDateTime start;
    private final IDateTime end;

    /**
     * Constructs the time slot [start, end).
     *
     * @param start the start of the interval (pickup date/time)
     * @param end   the end of the interval (drop-off date/time)
     * @throws IllegalArgumentException if start or end is null, or if start is not strictly before end
     */
    public TimeSlot(IDateTime start, IDateTime end) throws IllegalArgumentException {
        if (start == null || end == null)
            throw new IllegalArgumentException("Start and end date/times cannot be null.");
        if (start.compareTo(end) >= 0)
            throw new IllegalArgumentException("Start date/time must be before end date/time.");

        this.start = start;
        this.end = end;
    }

    @Override
    public IDateTime getStart() {
        return start;
    }

    @Override
    public IDateTime getEnd() {
        return end;
    }

    /**
     * Compares this time slot with another one.
     * <p>
     * The intervals are half-open, so a slot that starts exactly when
     * the other one ends does not overlap it.
     * </p>
     *
     * @param other the time slot to compare against
     * @return a negative value if this slot ends at or before other starts,
     *         a positive value if this slot starts at or after other ends,
     *         and 0 if the two slots overlap
     */
    @Override
    public int compareTo(ITimeSlot other) {
        // This slot is entirely before the other one
        if (this.end.compareTo(other.getStart()) <= 0)
            return -1;
        // This slot is entirely after the other one
        if (this.start.compareTo(other.getEnd()) >= 0)
            return 1;
        // Neither before nor after: the two slots share some time
        return 0;
    }
}
