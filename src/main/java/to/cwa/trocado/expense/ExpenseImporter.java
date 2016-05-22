package to.cwa.trocado.expense;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import to.cwa.trocado.expense.om.ChaseImportedExpense;
import to.cwa.trocado.expense.om.Expense;
import to.cwa.trocado.util.DateUtil;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author krico
 * @since 18/05/16.
 */
public class ExpenseImporter {
    private static final Logger log = LoggerFactory.getLogger(ExpenseImporter.class);

    private interface ChaseColumns {
        int Type = 0;
        int Date = 1;
        int Description = 2;
        int Amount = 3;
        int CheckOrPlaySlipNumber = 4;
    }

    List<Expense> parse(Reader reader) throws IOException, ParseException {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(reader)) {
            for (String[] row : csvReader) {
                if ("Type".equals(row[ChaseColumns.Type])) {
                    continue; // header row
                }
                ChaseImportedExpense expense = new ChaseImportedExpense();
                expense.setChaseType(ChaseImportedExpense.ChaseType.valueOf(row[ChaseColumns.Type]));
                expense.setDate(DateUtil.parseUSDate(row[ChaseColumns.Date]));
                expense.setDescription(row[ChaseColumns.Description]);
                expense.setAmount(new BigDecimal(row[ChaseColumns.Amount]));
                expense.setCheckOrPlaySlipNumber(row[ChaseColumns.CheckOrPlaySlipNumber]);
                expenses.add(expense);
            }
        }
        return expenses;
    }
}
