package to.cwa.trocado;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.BigDecimalLongTranslatorFactory;
import to.cwa.trocado.account.om.Account;
import to.cwa.trocado.expense.om.ChaseImportedExpense;
import to.cwa.trocado.expense.om.Expense;
import to.cwa.trocado.expense.om.ImportKey;

/**
 * Lazy loading singleton that initializes the entire system
 *
 * @author krico
 * @since 09/02/16.
 */
public class Trocado {
    private Trocado() {
        initializeObjectify();
    }

    static void initializeObjectify() {
        ObjectifyService.factory().getTranslators().add(new BigDecimalLongTranslatorFactory());
        ObjectifyService.register(Account.class);
        ObjectifyService.register(Expense.class);
        ObjectifyService.register(ChaseImportedExpense.class);
        ObjectifyService.register(ImportKey.class);
    }

    public static Trocado instance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final Trocado INSTANCE = new Trocado();
    }
}
