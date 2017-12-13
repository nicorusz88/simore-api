package ar.com.simore.simoreapi.services.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat simpleDateFormatHourAndMinutes = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String formatDateHourAndMinutes(final Date date){
        return simpleDateFormatHourAndMinutes.format(date);
    }


    /*** Gets the current date with only the Hour set
     * @return
     */
    public static Date getCurrentDateWithHourOnly() {
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStampMillis);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getCurrentDate(){
        return Date.from(Instant.now());
    }

    /** Gets the start time of the current date.Ex. 13/02/2017 00:00:00
     * @return
     */
    public static Date getCurrentDateFirstHour(){
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStampMillis);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** Gets the last time of the day. Ex. 13/02/2017 23:59:59
     * @return
     */
    public static Date getCurrentDateLastHour(){
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStampMillis);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** Gets the start time of tomorrow date. Ex. 13/02/2017 00:00:00
     * @return
     */
    public static Date getTomorrowDateFirstHour(){
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStampMillis);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** Gets the last time of tomorrow. Ex. 13/02/2017 23:59:59
     * @return
     */
    public static Date getTomorrowDateLastHour(){
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStampMillis);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /** Gets the current date with only the Hour and minutes set
     * @return
     */
    public static Date getCurrentDateWithHourAndMinutesOnly() {
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStampMillis);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** Gets onle the day part of the passed in date
     * @param date
     * @param startAt
     * @return
     */
    public static Date getDateStartAt(final Date date, final int startAt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, startAt);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static long getElapsedTimeInMinutes(long startTime) {
        final long stopTime = System.currentTimeMillis();
        final long elapsedTime = stopTime - startTime;
        return TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
    }
}
