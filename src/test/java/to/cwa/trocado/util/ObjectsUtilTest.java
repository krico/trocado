package to.cwa.trocado.util;

import com.google.appengine.api.datastore.Category;
import org.junit.Test;
import to.cwa.trocado.TestHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ObjectsUtilTest {

    @Test
    public void wellDefined() throws Exception {
        TestHelper.assertUtilityClassWellDefined(ObjectsUtil.class);
    }

    public void toStringCategory() throws Exception {
        assertNull(ObjectsUtil.toString(null));
        assertEquals("foo", ObjectsUtil.toString(new Category("foo")));
    }
}