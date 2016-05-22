package to.cwa.trocado.util;

import org.junit.Test;
import to.cwa.trocado.TestHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static junit.framework.TestCase.assertEquals;

public class DateUtilTest {

    @Test
    public void wellDefined() throws Exception {
        TestHelper.assertUtilityClassWellDefined(DateUtil.class);
    }

    @Test
    public void formatUSDate() {
        assertEquals("07/15/1976", DateUtil.formatUSDate(mkDay().getTime()));
    }

    @Test
    public void parseUSDate() throws Exception {
        Date parsed = DateUtil.parseUSDate("07/15/1976");
        GregorianCalendar calendar = mkDay();
        Date expected = calendar.getTime();
        assertEquals(expected, parsed);
    }

    private GregorianCalendar mkDay() {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.set(1976, Calendar.JULY, 15);
        return calendar;
    }

}