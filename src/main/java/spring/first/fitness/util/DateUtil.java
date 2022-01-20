package spring.first.fitness.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateUtil {

    private DateUtil() { throw new IllegalStateException("Utility class"); }

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm";

    public static boolean isValidDate(String date) {
        return isValidDate(date, DEFAULT_DATE_PATTERN);
    }

    public static boolean isValidDate(String date, String pattern) {
        try {
            new SimpleDateFormat(pattern).parse(date);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
