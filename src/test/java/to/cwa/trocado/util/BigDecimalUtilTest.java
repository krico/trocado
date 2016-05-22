package to.cwa.trocado.util;

import org.junit.Test;
import to.cwa.trocado.TestHelper;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class BigDecimalUtilTest {

    @Test
    public void wellDefined() throws Exception {
        TestHelper.assertUtilityClassWellDefined(BigDecimalUtil.class);
    }

    @Test
    public void testAmountBigDecimal() throws Exception {
        assertEquals(BigDecimalUtil.UNSET_AMOUNT, BigDecimalUtil.amount((BigDecimal) null));
        assertEquals(1l, BigDecimalUtil.amount(new BigDecimal("0.000001")));
        assertEquals(1000001l, BigDecimalUtil.amount(new BigDecimal("1.000001")));
        assertEquals(1000000l, BigDecimalUtil.amount(new BigDecimal("1")));
        assertEquals(-1000000l, BigDecimalUtil.amount(new BigDecimal("-1")));
        assertEquals(-1000001l, BigDecimalUtil.amount(new BigDecimal("-1.000001")));
    }

    @Test
    public void testAmountString() throws Exception {
        assertEquals(BigDecimalUtil.UNSET_AMOUNT, BigDecimalUtil.amount((String) null));
        assertEquals(1l, BigDecimalUtil.amount("0.000001"));
        assertEquals(1000001l, BigDecimalUtil.amount("1.000001"));
        assertEquals(1000000l, BigDecimalUtil.amount("1"));
        assertEquals(-1000000l, BigDecimalUtil.amount("-1"));
        assertEquals(-1000001l, BigDecimalUtil.amount("-1.000001"));
    }

    @Test
    public void testAmountDouble() throws Exception {
        assertEquals(1l, BigDecimalUtil.amount(0.000001d));
        assertEquals(1000001l, BigDecimalUtil.amount(1.000001d));
        assertEquals(1000000l, BigDecimalUtil.amount(1d));
        assertEquals(-1000000l, BigDecimalUtil.amount(-1d));
        assertEquals(-1000001l, BigDecimalUtil.amount(-1.000001d));
    }

    @Test
    public void testAmountLong() throws Exception {
        assertEquals(BigDecimalUtil.UNSET_AMOUNT, BigDecimalUtil.amount((String) null));
        assertEquals(new BigDecimal("0.000001"), BigDecimalUtil.amount(1l));
        assertEquals(new BigDecimal("1.000001"), BigDecimalUtil.amount(1000001l));
        assertEquals(new BigDecimal("-1.000001"), BigDecimalUtil.amount(-1000001l));
    }
}