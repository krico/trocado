package to.cwa.trocado;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContextEvent;

public class TrocadoContextListenerTest {
    private TrocadoContextListener listener = new TrocadoContextListener();

    @Test
    public void testContextInitialized() throws Exception {
        ServletContextEvent sce = Mockito.mock(ServletContextEvent.class);
        listener.contextInitialized(sce);
    }

    @Test
    public void testContextDestroyed() throws Exception {
        testContextInitialized(); //initialize to destroy
        ServletContextEvent sce = Mockito.mock(ServletContextEvent.class);
        listener.contextDestroyed(sce);
    }
}