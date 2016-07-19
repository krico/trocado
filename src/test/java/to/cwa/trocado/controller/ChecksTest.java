package to.cwa.trocado.controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import to.cwa.trocado.TestHelper;
import to.cwa.trocado.om.HasId;

import static junit.framework.TestCase.assertSame;

public class ChecksTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isWellDefined() throws Exception {
        TestHelper.assertUtilityClassWellDefined(Checks.class);
    }

    @Test
    public void testCheckIdIsNullOk() throws Exception {
        Checks.checkIdIsNull(new TestEntity());
    }

    @Test
    public void testCheckIdIsNullFail() throws Exception {
        TestEntity entity = new TestEntity();
        entity.setId(19760715L);
        thrown.expect(IllegalEntityException.class);
        thrown.expectMessage("actual: " + entity.getId());
        Checks.checkIdIsNull(entity);
    }

    @Test
    public void testCheckFoundOk() throws Exception {
        TestEntity entity = new TestEntity();
        assertSame(entity, Checks.checkFound(entity));
    }

    @Test
    public void testCheckFoundFail() throws Exception {
        thrown.expect(EntityNotFoundException.class);
        Checks.checkFound(null);
    }

    private static class TestEntity implements HasId<Long> {
        private Long id;

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long id) {
            this.id = id;
        }
    }
}