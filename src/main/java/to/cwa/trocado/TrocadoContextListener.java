package to.cwa.trocado;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author krico
 * @since 09/02/16.
 */
public class TrocadoContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Trocado.instance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
