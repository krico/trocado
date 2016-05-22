package to.cwa.trocado.expense;

import org.junit.Before;
import org.junit.Test;
import to.cwa.trocado.expense.om.ChaseImportedExpense;
import to.cwa.trocado.expense.om.Expense;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static to.cwa.trocado.expense.om.ChaseImportedExpense.ChaseType.*;
import static to.cwa.trocado.util.DateUtil.parseUSDate;

public class ExpenseImporterTest {
    public static final String D1 = "FOO AG           PAYROLL                    PPD ID: 1234567890";
    public static final String D2 = "METRO-NORTH TVM & TOM NEW YORK NY            02/26";
    public static final String D3 = "CHECK 9999 ";
    public static final String D4 = "FEDWIRE CREDIT VIA: FOO AG BRANCH/111111111 B/O: 1/JOHNJOHN DOEDOE 6/CH/FOO/0555-00111111 REF: CHASE NYC/CTR/BNF=FOO BAR BAR OR BAR BAR FOO S STAMFORD, CT 06899999 1/AC-000000001111 RFB=ZD12345678901 123 OBI=RELOCATION TRANSFER BBI=/OC MT/USD3000,/ IMAD: 123456789098765432 TRN: 123456789098";
    private List<ChaseImportedExpense> expected = new ArrayList<>();

    @Before
    public void createExpected() throws ParseException {
        expected.add(new ChaseImportedExpense(D3, new BigDecimal("-120.00"), "9999", parseUSDate("03/08/2016"), CHECK));
        expected.add(new ChaseImportedExpense(D1, new BigDecimal("1234.56"), "", parseUSDate("02/29/2016"), CREDIT));
        expected.add(new ChaseImportedExpense(D2, new BigDecimal("-27.75"), "", parseUSDate("02/29/2016"), DEBIT));
        expected.add(new ChaseImportedExpense(D4, new BigDecimal("3000.00"), "", parseUSDate("02/18/2016"), CREDIT));
    }

    @Test
    public void testParseChase() throws Exception {
        InputStream in = getClass().getResourceAsStream("ExpenseBatch.example.chase.csv");
        ExpenseImporter expenseImporter = new ExpenseImporter();
        List<Expense> imported = expenseImporter.parse(new InputStreamReader(in));
        assertNotNull(imported);
        assertEquals(expected.size(), imported.size());
        Iterator<ChaseImportedExpense> expectedIt = expected.iterator();
        Iterator<Expense> importedIt = imported.iterator();
        while (expectedIt.hasNext()) {
            ChaseImportedExpense expectedExpense = expectedIt.next();
            Expense actualExpense = importedIt.next();

            assertEquals("toString", expectedExpense.toString(), actualExpense.toString());
            assertEquals("equals", expectedExpense, actualExpense);
        }
    }
}
