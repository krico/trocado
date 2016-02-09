package to.cwa.trocado;

import com.googlecode.objectify.ObjectifyService;
import to.cwa.trocado.om.Account;

/**
 * Lazy loading singleton that initializes the entire system
 *
 * @author krico
 * @since 09/02/16.
 */
public class Trocado {
    private Trocado() {
        ObjectifyService.register(Account.class);
    }

    public static Trocado instance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final Trocado INSTANCE = new Trocado();
    }
}
