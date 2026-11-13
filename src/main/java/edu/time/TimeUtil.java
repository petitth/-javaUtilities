package edu.time;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {

    private TimeUtil() {}

    /**
     * Get current date
     * @return current date
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * Get current date and time using SimpleDateFormat
     * @return a string containing date and time
     */
    public static String getSdfCurrentDateTime() {
        final String FORMAT = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return sdf.format(new Date());
    }

    /**
     * Get current date and time using SimpleDateFormat
     * @param format format to use, see javadoc of SimpleDateFormat
     * @return a string containing date and time
     */
    public static String getSdfCurrentDateTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * Get current timestamp
     * @return current timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    /**
     * Create a date object from a defined value and format.
     * @param dateFormat date format
     * @param dateString date value
     * @return a date object
     */
    public static Date getDate(String dateFormat, String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.parse(dateString);
        } catch(IllegalArgumentException | ParseException ex) {
            return null;
        }
    }

    /**
     * Get a difference, in months, between two dates
     * @param oldestDate the oldest date
     * @param newestDate the newest date
     * @return the difference in months
     */
    public static long getDifferenceInMonths(Date oldestDate, Date newestDate) {
        Date xOldestDate = oldestDate;
        Date xNewestDate = newestDate;
        if (oldestDate.after(newestDate)) {
            xOldestDate = newestDate;
            xNewestDate = oldestDate;
        }

        LocalDateTime oldestDateTime = LocalDateTime.ofInstant(xOldestDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime newestDateTime = LocalDateTime.ofInstant(xNewestDate.toInstant(), ZoneId.systemDefault());

        Period p = Period.between(oldestDateTime.toLocalDate(), newestDateTime.toLocalDate());
        return (p.getYears() * 12 + p.getMonths());
    }

    /**
     * Get last day of the month.
     * @param month month
     * @param year year
     * @return LocalDate
     */
    public static LocalDate getLastDayOfMonth(int month, int year) {
        LocalDate date = getFirstDayOfMonth(month, year);
        date = date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
        return date;
    }

    /**
     * Get first day of the month.
     * @param month month
     * @param year year
     * @return LocalDate
     */
    public static LocalDate getFirstDayOfMonth(int month, int year) {
        final String FORMAT = "d/M/yyyy";
        StringBuilder sb = new StringBuilder();
        sb.append("01/");
        sb.append(month);
        sb.append("/");
        sb.append(year);
        return LocalDate.parse(sb.toString(), DateTimeFormatter.ofPattern(FORMAT));
    }
}
