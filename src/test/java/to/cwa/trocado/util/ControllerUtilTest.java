package to.cwa.trocado.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import to.cwa.trocado.om.HasId;
import to.cwa.trocado.spi.EntityNotFoundException;
import to.cwa.trocado.spi.IllegalEntityException;

import static junit.framework.TestCase.assertSame;

public class ControllerUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCheckIdIsNullOk() throws Exception {
        ControllerUtil.checkIdIsNull(new TestEntity());
    }

    @Test
    public void testCheckIdIsNullFail() throws Exception {
        TestEntity entity = new TestEntity();
        entity.setId(19760715L);
        thrown.expect(IllegalEntityException.class);
        thrown.expectMessage("actual: " + entity.getId());
        ControllerUtil.checkIdIsNull(entity);
    }

    @Test
    public void testCheckFoundOk() throws Exception {
        TestEntity entity = new TestEntity();
        assertSame(entity, ControllerUtil.checkFound(entity));
    }

    @Test
    public void testCheckFoundFail() throws Exception {
        thrown.expect(EntityNotFoundException.class);
        ControllerUtil.checkFound(null);
    }

    private static class TestEntity implements HasId<Long> {
        private Long id;

        @Override
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}