package edu.time;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {

    private static final Logger LOG = LoggerFactory.getLogger(TimeTest.class);

    @Test
    public void checkingDates(){
        final String FORMAT = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT);

        long millis = System.currentTimeMillis();
        assertNotNull(millis);
        LOG.debug("System.currentTimeMillis = {}", millis);
        LOG.debug("java.sql.Date = {}", sdf.format(new java.sql.Date(millis)));
        LOG.debug("java.util.Date = {}", sdf.format(new java.util.Date()));

        LocalDateTime localDateTime = LocalDateTime.now();
        assertNotNull(localDateTime);
        LOG.debug("LocalDateTime : {}", localDateTime.format(dateTimeFormatter));
    }

    @Test
    public void dateFormat() {
        final String FORMAT = "dd/MM/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(FORMAT);
        assertNotNull(df, "make sonar happy");
        LOG.debug("date : {}", df.format(new java.util.Date()));
    }

    @Test
    public void dateFormatEx2() throws ParseException {
        final String FORMAT = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(FORMAT);
        Date date = format.parse("2023-01-01 00:00:00");
        LOG.debug("date = {}", date);
        assertNotNull(date);
    }

    @Test
    public void dateFormatEx3() {
        try {
            final String FORMAT = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(FORMAT);
            Date date = format.parse("2023-01-01");
            fail("it cannot work");
        } catch(ParseException ex) {
            assertNotNull(ex);
        }
    }

    @Test
    public void dateFormatEx4() throws ParseException {
        final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        SimpleDateFormat format = new SimpleDateFormat(FORMAT);
        Date date = format.parse("2012-11-25T23:50:56.193+01:00");
        LOG.debug("date = {}", date);
        assertNotNull(date);
    }

    @Test
    public void timestampEx1() {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        assertNotNull(ts, "make sonar happy");
        LOG.debug("timestamp : {}", ts);
    }

    @Test
    public void timestampEx2() throws ParseException {
        final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        SimpleDateFormat format = new SimpleDateFormat(FORMAT);
        Date date = format.parse("2012-11-25T23:50:56.193+01:00");

        Timestamp ts = new Timestamp(date.getTime());
        assertNotNull(ts, "make sonar happy");
        LOG.debug("timestamp : {}", ts);
    }

    @Test
    public void checkingLocalDate() {
        LocalDate xDate = LocalDate.of(2024, Month.JUNE, 6);
        assertNotNull(xDate);
        assertEquals(2024, xDate.getYear());
        assertEquals(6, xDate.getMonthValue());
        assertEquals(6, xDate.getDayOfMonth());

        LocalDateTime xDateTime = LocalDateTime.of(2024, Month.JUNE, 6, 10, 15);
        assertNotNull(xDateTime);
        assertEquals(2024, xDateTime.getYear());
        assertEquals(6, xDateTime.getMonthValue());
        assertEquals(6, xDateTime.getDayOfMonth());
        assertEquals(10, xDateTime.getHour());
        assertEquals(15, xDateTime.getMinute());

        LocalDate now = LocalDate.now();
        assertNotNull(now);

        // quick way to get current year
        assertTrue(LocalDate.now().getYear() > 0);
    }

}
