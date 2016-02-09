package to.cwa.trocado.spi;

import com.google.api.server.spi.ServiceException;

/**
 * @author krico
 * @since 09/02/16.
 */
public class EntityNotFoundException extends ServiceException {
    public EntityNotFoundException() {
        super(404, "Entity not found");
    }
}
