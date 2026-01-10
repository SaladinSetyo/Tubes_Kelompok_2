package com.paulquiz.util;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for date and time operations
 */
public class DateUtil {

    // Date format patterns
    private static final String DATETIME_PATTERN = "dd MMM yyyy, HH:mm";
    private static final String DATE_PATTERN = "dd MMM yyyy";
    private static final String TIME_PATTERN = "HH:mm";

    /**
     * Format timestamp to readable date-time string
     * 
     * @param timestamp The timestamp to format
     * @return Formatted date-time string
     */
    public static String formatDateTime(Timestamp timestamp) {
        if (timestamp == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN);
        return sdf.format(new Date(timestamp.getTime()));
    }

    /**
     * Format timestamp to date only
     * 
     * @param timestamp The timestamp to format
     * @return Formatted date string
     */
    public static String formatDate(Timestamp timestamp) {
        if (timestamp == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(new Date(timestamp.getTime()));
    }

    /**
     * Format timestamp to time only
     * 
     * @param timestamp The timestamp to format
     * @return Formatted time string
     */
    public static String formatTime(Timestamp timestamp) {
        if (timestamp == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        return sdf.format(new Date(timestamp.getTime()));
    }

    /**
     * Get current timestamp
     * 
     * @return Current timestamp
     */
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Get relative time string (e.g., "2 hours ago", "3 days ago")
     * 
     * @param timestamp The timestamp to compare
     * @return Relative time string
     */
    public static String getRelativeTime(Timestamp timestamp) {
        if (timestamp == null)
            return "";

        long currentTime = System.currentTimeMillis();
        long pastTime = timestamp.getTime();
        long diff = currentTime - pastTime;

        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long days = TimeUnit.MILLISECONDS.toDays(diff);

        if (seconds < 60) {
            return "Baru saja";
        } else if (minutes < 60) {
            return minutes + " menit yang lalu";
        } else if (hours < 24) {
            return hours + " jam yang lalu";
        } else if (days < 7) {
            return days + " hari yang lalu";
        } else if (days < 30) {
            long weeks = days / 7;
            return weeks + " minggu yang lalu";
        } else if (days < 365) {
            long months = days / 30;
            return months + " bulan yang lalu";
        } else {
            long years = days / 365;
            return years + " tahun yang lalu";
        }
    }

    /**
     * Check if timestamp is within last N minutes
     * 
     * @param timestamp The timestamp to check
     * @param minutes   Number of minutes
     * @return true if within range, false otherwise
     */
    public static boolean isWithinMinutes(Timestamp timestamp, int minutes) {
        if (timestamp == null)
            return false;

        long currentTime = System.currentTimeMillis();
        long pastTime = timestamp.getTime();
        long diff = currentTime - pastTime;
        long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(diff);

        return minutesDiff <= minutes;
    }

    /**
     * Add minutes to timestamp
     * 
     * @param timestamp The timestamp to modify
     * @param minutes   Number of minutes to add
     * @return New timestamp
     */
    public static Timestamp addMinutes(Timestamp timestamp, int minutes) {
        if (timestamp == null)
            return null;
        long time = timestamp.getTime() + TimeUnit.MINUTES.toMillis(minutes);
        return new Timestamp(time);
    }

    /**
     * Add hours to timestamp
     * 
     * @param timestamp The timestamp to modify
     * @param hours     Number of hours to add
     * @return New timestamp
     */
    public static Timestamp addHours(Timestamp timestamp, int hours) {
        if (timestamp == null)
            return null;
        long time = timestamp.getTime() + TimeUnit.HOURS.toMillis(hours);
        return new Timestamp(time);
    }

    /**
     * Add days to timestamp
     * 
     * @param timestamp The timestamp to modify
     * @param days      Number of days to add
     * @return New timestamp
     */
    public static Timestamp addDays(Timestamp timestamp, int days) {
        if (timestamp == null)
            return null;
        long time = timestamp.getTime() + TimeUnit.DAYS.toMillis(days);
        return new Timestamp(time);
    }
}

