package model;
import interfaces.IDateTime;

public class DateTime implements IDateTime {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public DateTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    // Formats this date/time for display (e.g., "MM/DD/YYYY HH:MM")
    @Override
    public String format() {
        return "";
    }

    //  Compares this date/time with another chronologically: by year, then month, then day, then hour, then minute.
    @Override
    public int compareTo(IDateTime other) {
        return 0;
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
