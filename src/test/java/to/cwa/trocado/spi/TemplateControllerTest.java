package to.cwa.trocado.spi;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TemplateControllerTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private TemplateController controller = new TemplateController();
    private Closeable ofyContext;
    private Key<TemplateController.Template> key;

    @Before
    public void initialize() {
        helper.setUp();
        ObjectifyService.register(TemplateController.Template.class);
        ofyContext = ObjectifyService.begin();
    }

    @After
    public void cleanup() {
        if (ofyContext != null) ofyContext.close();
        helper.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        key = controller.save(new TemplateController.Template());
        assertNotNull(key);
    }

    @Test
    public void testGet() throws Exception {
        testSave();
        TemplateController.Template gotten = controller.get(key);
        assertNotNull(gotten);
    }

    @Test
    public void testQuery() throws Exception {
        testSave();
        testSave();
        List<TemplateController.Template> all = controller.query();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

    @Test
    public void testUpdate() throws Exception {
        testSave();
        controller.update(key, new TemplateController.Template());
    }

    @Test
    public void testDelete() throws Exception {
        testSave();
        controller.delete(key);
        controller.delete(key);
    }
}