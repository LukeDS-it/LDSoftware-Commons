package it.ldsoftware.primavera.util;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

/**
 * Created by luca on 11/04/16.
 * Useful methods for dates
 */
public class CalendarUtil {

    public static Calendar endOfDay(Date date) {
        Calendar c = fromDate(date);
        return endOfDay(c);
    }

    public static Calendar endOfDay(Calendar calendar) {
        calendar.set(HOUR, 23);
        calendar.set(MINUTE, 59);
        calendar.set(SECOND, 59);
        calendar.set(MILLISECOND, 999);
        return calendar;
    }

    public static Calendar fromDate(Date date) {
        Calendar c = now();
        c.setTime(date);
        return c;
    }

    public static Calendar now() {
        return Calendar.getInstance();
    }
}
