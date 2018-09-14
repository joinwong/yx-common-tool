package com.yx.util;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by join on 2018/9/14.
 */
public class DateTimeUtilsTest extends TestCase {
    long mills = 1536908705000L; //2018-09-14 15:05:05
    String dateTime = "2018-09-14 15:05:05";

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetTodayDateTimeStr() throws Exception {
        String str = DateTimeUtils.getTodayDateTimeStr("yyyy-mm-dd");

        Assert.assertNotNull(str);
        Assert.assertNotNull(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")),str);
    }

    public void testGetDateTimeStrByFormat() throws Exception {
        String dateTime = DateTimeUtils.getDateTimeStrByFormat(mills,"yyyy-MM-dd HH:mm:ss");
        String year = DateTimeUtils.getDateTimeStrByFormat(mills,"yyyy");
        String month = DateTimeUtils.getDateTimeStrByFormat(mills,"MM");
        String date = DateTimeUtils.getDateTimeStrByFormat(mills,"dd");
        String time = DateTimeUtils.getDateTimeStrByFormat(mills,"HH:mm:ss");

        Assert.assertNotNull(dateTime);
        Assert.assertEquals("2018-09-14 15:05:05",dateTime);
        Assert.assertNotNull(year);
        Assert.assertEquals("2018",year);
        Assert.assertNotNull(month);
        Assert.assertEquals("09",month);
        Assert.assertNotNull(date);
        Assert.assertEquals("14",date);
        Assert.assertNotNull(time);
        Assert.assertEquals("15:05:05",time);
    }

    public void testGetDateStrByFormat() throws Exception {
        String dateTime = DateTimeUtils.timestamp2DateTimeStr(mills);

        Assert.assertNotNull(dateTime);
        Assert.assertEquals("2018-09-14 15:05:05",dateTime);
    }

    public void testGetSqlTimestamp() throws Exception {
        Timestamp ts = DateTimeUtils.getSqlTimestamp(mills);

        Assert.assertNotNull(ts);
        Assert.assertEquals(mills,ts.getTime());
    }

    public void testDateStr2Timestamp() throws Exception {
        long timestamp = DateTimeUtils.dateStr2Timestamp(dateTime);

        Assert.assertNotNull(timestamp);
        Assert.assertEquals(mills,timestamp);
    }

    public void testGetTimestampMorning() throws Exception {
        long morning = 1536854400000L; //2018-09-14 00:00:00
        long timestamp = DateTimeUtils.getTimestampMorning();

        Assert.assertNotNull(timestamp);
        Assert.assertEquals(morning,timestamp);
    }

    public void testGetTimestampNight() throws Exception {
        long morning = 1536940800000L; //2018-09-15 00:00:00
        long timestamp = DateTimeUtils.getTimestampNight();
        //System.out.println(timestamp);

        Assert.assertNotNull(timestamp);
        Assert.assertEquals(morning,timestamp);
    }

    public void testGetTimestampWeekmorning() throws Exception {
        long morning = 1536508800L; //2018-09-10 00:00:00.xxx
        long timestamp = DateTimeUtils.getTimestampWeekmorning() / 1000;
        System.out.println(timestamp);

        Assert.assertNotNull(timestamp);
        Assert.assertEquals(morning,timestamp);
    }

    public void testGetTimestampWeeknight() throws Exception {
        long morning = 1537113600L; //2018-09-17 00:00:00
        long timestamp = DateTimeUtils.getTimestampWeeknight() / 1000;
        System.out.println(timestamp);

        Assert.assertNotNull(timestamp);
        Assert.assertEquals(morning,timestamp);
    }

    public void testGetTimestampMonthmorning() throws Exception {
        long morning = 1535731200L; //2018-09-01 00:00:00
        long timestamp = DateTimeUtils.getTimestampMonthmorning() / 1000;
        System.out.println(timestamp);

        Assert.assertNotNull(timestamp);
        Assert.assertEquals(morning,timestamp);
    }

    public void testGetTimestampMonthnight() throws Exception {
        long morning = 1538323200L; //2018-10-01 00:00:00
        long timestamp = DateTimeUtils.getTimestampMonthnight() / 1000;
        //System.out.println(timestamp);

        Assert.assertNotNull(timestamp);
        Assert.assertEquals(morning,timestamp);
    }

    public void testTimestamp2WeekStr() throws Exception {
        String weekStr = DateTimeUtils.timestamp2WeekStr(mills);

        System.out.println(weekStr);

        Assert.assertNotNull(weekStr);
        Assert.assertEquals("星期五",weekStr);
    }

}