package to.cwa.trocado.spi;

import com.google.api.server.spi.ServiceException;

/**
 * @author krico
 * @since 09/02/16.
 */
public class IllegalEntityException extends ServiceException {
    public IllegalEntityException(Object entity, String message) {
        super(400, entity.getClass().getSimpleName() + ": " + message);
    }
}
