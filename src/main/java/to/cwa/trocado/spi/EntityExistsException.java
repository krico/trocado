package to.cwa.trocado.spi;


import com.google.api.server.spi.ServiceException;

/**
 * @author krico
 * @since 09/02/16.
 */
public class EntityExistsException extends ServiceException {
    public EntityExistsException(Object entity, String message) {
        super(409, entity.getClass().getSimpleName() + ": " + message);
    }
}