package to.cwa.trocado.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author krico
 * @since 21/05/16.
 */
public final class DateUtil {
    private static final FastDateFormat US_DATE = FastDateFormat.getInstance("MM/dd/yyyy", TimeZone.getTimeZone("UTC"), Locale.ROOT);

    private DateUtil() {
    }

    public static String formatUSDate(Date date) {
        return US_DATE.format(date);
    }

    /**
     * @param date mm/dd/yyyy
     * @return a Date representing this month/day/year
     */
    public static Date parseUSDate(String date) throws ParseException {
        return US_DATE.parse(date);
    }
}
