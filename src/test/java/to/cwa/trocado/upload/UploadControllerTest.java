package to.cwa.trocado.upload;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import to.cwa.trocado.upload.om.Upload;
import to.cwa.trocado.upload.om.UploadResult;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class UploadControllerTest {
    private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig(), new LocalMemcacheServiceTestConfig());

    private UploadController controller;

    @Before
    public void create() {
        helper.setUp();
        controller = new UploadController();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        Upload upload = new Upload();
        upload.setBase64Data(Base64.encodeBase64String("test".getBytes()));
        upload.setLastModified(System.currentTimeMillis());
        upload.setName("test.txt");
        upload.setSize(4);
        upload.setType("text/plain");

        UploadResult result = controller.save(upload);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(upload.getLastModified(), result.getLastModified());
        assertEquals(upload.getName(), result.getName());
        assertEquals(upload.getSize(), result.getSize());
        assertEquals(upload.getType(), result.getType());

        Upload fetched = controller.get(result.getId());
        assertNotNull(fetched);
        assertEquals(upload.getBase64Data(), fetched.getBase64Data());
        assertEquals(upload.getLastModified(), fetched.getLastModified());
        assertEquals(upload.getName(), fetched.getName());
        assertEquals(upload.getSize(), fetched.getSize());
        assertEquals(upload.getType(), fetched.getType());

    }
}