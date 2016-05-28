package to.cwa.trocado.expense;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import to.cwa.trocado.expense.om.ChaseImportedExpense;
import to.cwa.trocado.expense.om.Expense;
import to.cwa.trocado.expense.om.ImportKey;
import to.cwa.trocado.util.DateUtil;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

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

        setImportKeys(expenses);

        return expenses;
    }

    private void setImportKeys(ArrayList<Expense> expenses) {
        //Because the chase import file is generated ordered by descending date
        //and because we want the importKey to be the same even if new entries
        //were added to the chase import file.
        //We create the key with an index that represents the reverse order.
        //On a list with N entries, entry[0] gets index N, entry[1] gets N-1 and so on.
        int size = expenses.size();
        for (int i = 0; i < size; ++i) {
            ChaseImportedExpense importedExpense = (ChaseImportedExpense) expenses.get(i);
            int keyIndex = size - i;

            //TODO: this should uniquely identify the user or group and the bank/account this is coming from
            String userIdentifier = "Chase-";

            String key = String.format("%s-%05d", DateUtil.formatUSDate(importedExpense.getDate()), keyIndex);
            importedExpense.setImportKey(key);
        }

    }

    List<Expense> save(List<Expense> parsed) {
        for (final Expense expense : parsed) {

            ofy().transact(new VoidWork() {
                @Override
                public void vrun() {
                    String importKeyName = ((ChaseImportedExpense) expense).getImportKey();

                    ImportKey importKey = ofy().load().type(ImportKey.class).id(importKeyName).now();

                    if (importKey == null) {
                        Key<Expense> expenseKey = ofy().factory().allocateId(Expense.class);
                        importKey = new ImportKey();
                        importKey.setId(importKeyName);
                        importKey.setExpenseKey(expenseKey);

                        expense.setId(importKey.getExpenseKey().getId());
                        ofy().save().entities(importKey, expense);
                    } else {
                        expense.setId(importKey.getExpenseKey().getId());
                        ofy().save().entities(expense);
                    }


                }
            });
        }
        return parsed;
    }
}
