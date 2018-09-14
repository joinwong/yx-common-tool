package com.yx.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * 日期时间工具类
 * Created by join on 2018/9/14.
 */
public class DateTimeUtils {
    /**
     * 获取今天格式化时间 HH:mi:ss
     * @param format "HH:mm:ss"
     * @return
     */
    public static String getTodayDateTimeStr(final String format) {
        if(StringUtils.isEmpty(format))
            throw new IllegalArgumentException("format is empty");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 从毫秒时间戳中获取日期 2018-01-16 09:10:10
     * @param millisecond
     * @return
     */
    public static String getDateTimeStrByFormat(final long millisecond, final String format){
        if(StringUtils.isEmpty(format))
            throw new IllegalArgumentException("format is empty");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneOffset.of("+8")).format(formatter);
    }

    /**
     * 时间戳（毫秒）转换为yyyy-MM-dd HH:mm:ss字符串
     * @param timestamp
     * @return
     */
    public static String timestamp2DateTimeStr(final long timestamp){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),ZoneOffset.of("+8")).format(formatter);
    }

    /**
     * 将毫秒时间戳转换为Timestamp形式
     * @param millisecond
     * @return
     */
    public static Timestamp getSqlTimestamp(final long millisecond) {
        return new Timestamp(millisecond);
    }

    /**
     * 根据日期字符串yyyy-MM-dd HH:mm:ss 转换为 时间戳(毫秒)
     * @param date
     * @return
     */
    public static long dateStr2Timestamp(final String date) {
        if (StringUtils.isEmpty(date))
            throw new IllegalArgumentException("date is empty");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获得当天0点时间 毫秒
     * @return
     */
    public static long getTimestampMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获得当天24点时间 毫秒
     * @return
     */
    public static long getTimestampNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获得本周一0点时间 带毫秒
     * @return
     */
    public static long getTimestampWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTimeInMillis();
    }

    /**
     * 获得本周日24点时间（下周一0点） 带毫秒
     * @return
     */
    public static long getTimestampWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime().getTime() + (7 * 24 * 60 * 60 * 1000);
    }

    /**
     * 获得本月第一天0点时间 带毫秒
     * @return
     */
    public static long getTimestampMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    /**
     * 获得本月最后一天24点时间 (下月1号0点) 带毫秒
     * @return
     */
    public static long getTimestampMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTimeInMillis() ;
    }

    /**
     * 时间戳（毫秒）转换为周几字符串
     * @param timestamp
     * @return
     */
    public static String timestamp2WeekStr(final long timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");

        String weekDay = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),ZoneOffset.of("+8")).format(formatter);

        switch (weekDay) {
            case "Mon":
                return "星期一";
            case "Tue":
                return "星期二";
            case "Wed":
                return "星期三";
            case "Thu":
                return "星期四";
            case "Fri":
                return "星期五";
            case "Sat":
                return "星期六";
            case "Sun":
                return "星期日";
            default:
                return weekDay;
        }
    }
}
