package to.cwa.trocado.expense.om;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author krico
 * @since 28/05/16.
 */
@Entity
public class ImportKey {
    @Id
    private String id;

    private Key<Expense> expenseKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Key<Expense> getExpenseKey() {
        return expenseKey;
    }

    public void setExpenseKey(Key<Expense> expenseKey) {
        this.expenseKey = expenseKey;
    }
}
