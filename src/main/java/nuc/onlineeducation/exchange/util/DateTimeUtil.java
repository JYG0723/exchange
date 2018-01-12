package nuc.onlineeducation.exchange.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 18:26 2018/1/8.
 * 时间转换工具类。使用了jodaTime开源包来做
 */
@Log4j2
public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * (String formatStr) -> String to Date
     *
     * @param dateTimeStr
     * @param formatStr
     * @return new Date(dateTimeStr)
     */
    public static Date strToDate(String dateTimeStr, String formatStr) {
        // 根据写入的格式注册 格式化类
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        // 转换传入的时间字符串 字符串 -》 时间类
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        // 根据dateTime然后初始化一个含有时间dateTimeStr的Date
        return dateTime.toDate();
    }

    /**
     * (String formateStr) -> Date to String
     *
     * @param date      Date
     * @param formatStr format
     * @return date.toString
     */
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        // 通过date new一个Joda的DateTime
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    /**
     * 标准方式 String to Date
     *
     * @param dateTimeStr 字符串时间
     * @return new Date(strDateTime)
     */
    public static Date strToDate(String dateTimeStr) {
        // 根据写入的格式注册 格式化类
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        // 解析传入的字符串式的时间 -> 时间类
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * 标准方式
     *
     * @param date 日期类型的时间
     * @return date.toString
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

}
