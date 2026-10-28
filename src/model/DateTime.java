package model;
import interfaces.IDateTime;

/**
 * Represents an immutable date and time in the ride-sharing system.
 * <p> Stores the year, month, day, hour, and minute as separate fields.
 * All values are validated during construction and cannot be
 * modified afterward. </p>
 *
 * Date-time values are compared chronologically and formatted
 * using the pattern MM/DD/YYYY HH:MM.
 */

public class DateTime implements IDateTime {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    /**
     * Constructs a date-time value with the specified components.
     *
     * Validates the calendar date and time, including years,
     * month lengths, and valid hour and minute ranges.
     *
     * @param year   the year
     * @param month  the month (1-12)
     * @param day    the day of the month
     * @param hour   the hour (0-23)
     * @param minute the minute (0-59)
     * @throws IllegalArgumentException if any component forms an invalid date or time
     */

    public DateTime (int year, int month, int day, int hour, int minute) throws IllegalArgumentException {
        if (year < 1)
            throw new IllegalArgumentException("Year must be positive.");
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        if (day < 1 || day > daysInMonth(year, month))
            throw new IllegalArgumentException("Day is out of range for the given month.");
        if (hour < 0 || hour > 23)
            throw new IllegalArgumentException("Hour must be between 0 and 23.");
        if (minute < 0 || minute > 59)
            throw new IllegalArgumentException("Minute must be between 0 and 59.");

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the number of days in the given month,
     * taking leap years into account for February.
     *
     * @param year  the year
     * @param month the month (1-12)
     * @return the number of days in that month
     */
    private static int daysInMonth (int year, int month) {
        switch (month) {
            case 2:
                // Leap year: divisible by 4, except centuries not divisible by 400
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    return 29;
                else
                    return 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * Formats the date and time as MM/DD/YYYY HH:MM.
     *
     * @return the formatted date-time string
     */
    @Override
    public String format() {
        // %02d pads single digits with a leading zero (e.g., 3 -> 03)
        return String.format("%02d/%02d/%04d %02d:%02d", month, day, year, hour, minute);
    }

    /**
     * Compares this date-time with another chronologically.
     *
     * Compares the year first, followed by the month, day,
     * hour, and minute until a difference is found.
     *
     * @param other the date-time to compare against
     * @return a negative value if this date-time is earlier, zero if both are equal, or a positive value if this date-time is later
     */
    @Override
    public int compareTo(IDateTime other) {
        if (this.year != other.getYear())
            return Integer.compare(this.year, other.getYear());
        if (this.month != other.getMonth())
            return Integer.compare(this.month, other.getMonth());
        if (this.day != other.getDay())
            return Integer.compare(this.day, other.getDay());
        if (this.hour != other.getHour())
            return Integer.compare(this.hour, other.getHour());
        return Integer.compare(this.minute, other.getMinute());
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }
}
