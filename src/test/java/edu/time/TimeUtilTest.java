package edu.time;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class TimeUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(TimeUtilTest.class);

    @Test
    public void currentDateTest() {
        assertNotNull(TimeUtil.getCurrentDate());
    }

    @Test
    public void currentSdfDateTimeTest() {
        LOG.info(TimeUtil.getSdfCurrentDateTime());
        assertNotNull(TimeUtil.getSdfCurrentDateTime());

        LOG.info(TimeUtil.getSdfCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
        assertNotNull(TimeUtil.getSdfCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void currentTimestampTest() {
        assertNotNull(TimeUtil.getCurrentTimestamp());
    }

    @Test
    public void getDate() {
        assertNotNull(TimeUtil.getDate("yyyy-MM-dd", "2020-01-01"));
        assertNull(TimeUtil.getDate("AAAA-MM-dd", "2020-01-01"));
    }

    @Test
    public void getDifferenceInMonthsBetweenDatesTest() throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;

        date1 = format.parse("2018-01-15");
        date2 = format.parse("2018-04-01");
        assertTrue(TimeUtil.getDifferenceInMonths(date2, date1) == 2);
        assertTrue(TimeUtil.getDifferenceInMonths(date1, date2) == 2);

        date1 = format.parse("2015-12-01");
        date2 = format.parse("2016-02-01");
        assertTrue(TimeUtil.getDifferenceInMonths(date2, date1) == 2);

        date1 = format.parse("2015-12-01");
        date2 = format.parse("2016-02-10");
        assertTrue(TimeUtil.getDifferenceInMonths(date2, date1) == 2);
    }

    @Test
    public void getLastDayOfMonth() throws Exception {
        LocalDate date = TimeUtil.getLastDayOfMonth(2, 2023);
        assertNotNull(date);
        assertEquals(28, date.getDayOfMonth());
        assertEquals(Month.FEBRUARY, date.getMonth());
        assertEquals(DayOfWeek.TUESDAY, date.getDayOfWeek());
        assertEquals("Tuesday", date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        assertEquals("Tue", date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        assertEquals(2, date.getMonthValue());
        assertEquals(2023, date.getYear());
    }

    @Test
    public void getFirstDayOfMonth() throws Exception {
        LocalDate date = TimeUtil.getFirstDayOfMonth(2, 2023);
        assertNotNull(date);
        assertEquals(1, date.getDayOfMonth());
        assertEquals(Month.FEBRUARY, date.getMonth());
        assertEquals(DayOfWeek.WEDNESDAY, date.getDayOfWeek());
        assertEquals("Wednesday", date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        assertEquals("Wed", date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        assertEquals(2, date.getMonthValue());
        assertEquals(2023, date.getYear());
    }
}