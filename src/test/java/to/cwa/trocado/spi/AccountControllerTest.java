package to.cwa.trocado.spi;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import to.cwa.trocado.om.Account;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountControllerTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private AccountController controller = new AccountController();
    private Closeable ofyContext;
    private Account last;

    @Before
    public void initialize() {
        helper.setUp();
        ObjectifyService.register(Account.class);
        ofyContext = ObjectifyService.begin();
    }

    @After
    public void cleanup() {
        if (ofyContext != null) ofyContext.close();
        helper.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        last = controller.save(new Account());
        assertNotNull(last);
        assertNotNull(last.getId());
    }

    @Test
    public void testGet() throws Exception {
        testSave();
        Account gotten = controller.get(last.getId());
        assertEquals(gotten.getId(), last.getId());
    }

    @Test
    public void testQuery() throws Exception {
        testSave();
        List<Account> accounts = controller.query();
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
    }

    @Test
    public void testUpdate() throws Exception {
        testSave();
        Account saved = controller.update(last.getId(), last);
        assertNotNull(saved);
    }

    @Test
    public void testDelete() throws Exception {
        testSave();
        controller.delete(last.getId());
    }
}