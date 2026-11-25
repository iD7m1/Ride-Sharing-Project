package model;

import interfaces.IDateTime;
import interfaces.ITimeSlot;

/**
 * A half-open time interval [start, end) where start is the pickup date/time
 * and end is the drop-off date/time.
 */
public class TimeSlot implements ITimeSlot {

    private final IDateTime start;
    private final IDateTime end;

    public TimeSlot(IDateTime start, IDateTime end) {
        // TODO: validate start != null, end != null, and start is strictly before end
        this.start = start;
        this.end = end;
    }

    @Override
    public IDateTime getStart() {
        // TODO: return start
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IDateTime getEnd() {
        // TODO: return end
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int compareTo(ITimeSlot other) {
        // TODO: negative if this.end <= other.start, positive if this.start >= other.end, 0 if overlapping
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
