package to.cwa.trocado;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TrocadoTest {

    @Test
    public void testInstance() throws Exception {
        assertNotNull(Trocado.instance());
    }
}