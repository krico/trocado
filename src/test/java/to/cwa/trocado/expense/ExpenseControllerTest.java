package to.cwa.trocado.expense;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import to.cwa.trocado.expense.om.Expense;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExpenseControllerTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private ExpenseController controller = new ExpenseController();
    private Closeable ofyContext;
    private Expense last;

    @Before
    public void initialize() {
        helper.setUp();
        ObjectifyService.register(Expense.class);
        ofyContext = ObjectifyService.begin();
    }

    @After
    public void cleanup() {
        if (ofyContext != null) ofyContext.close();
        helper.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        last = controller.save(new Expense());
        assertNotNull(last);
        assertNotNull(last.getId());
    }

    @Test
    public void testGet() throws Exception {
        testSave();
        Expense gotten = controller.get(last.getId());
        assertEquals(gotten.getId(), last.getId());
    }

    @Test
    public void testQuery() throws Exception {
        testSave();
        List<Expense> expenses = controller.query();
        assertNotNull(expenses);
        assertEquals(1, expenses.size());
    }

    @Test
    public void testUpdate() throws Exception {
        testSave();
        Expense saved = controller.update(last.getId(), last);
        assertNotNull(saved);
    }

    @Test
    public void testDelete() throws Exception {
        testSave();
        controller.delete(last.getId());
    }
}