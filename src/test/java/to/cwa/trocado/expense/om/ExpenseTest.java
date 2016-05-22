package to.cwa.trocado.expense.om;

import org.junit.Test;
import to.cwa.trocado.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ExpenseTest {

    @Test
    public void testGetSetAmount() throws Exception {
        Expense expense = new Expense();
        Random random = new Random(19760715);
        for (int i = 0; i < 1000; ++i) {
            BigDecimal bd = BigDecimalUtil.amount(random.nextLong());
            expense.setAmount(bd);
            assertEquals(bd, expense.getAmount());
        }
    }

    @Test
    public void testAmountConstructor() throws Exception {
        Expense expense = new Expense(Origins.User, "desc", BigDecimal.TEN, new Date());
        assertEquals("desc", expense.getDescription());
        BigDecimal expected = BigDecimal.TEN.setScale(BigDecimalUtil.AMOUNT_PRECISION, BigDecimal.ROUND_DOWN);
        assertEquals(expected, expense.getAmount());
    }
}