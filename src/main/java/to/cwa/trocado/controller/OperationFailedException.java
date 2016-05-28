package to.cwa.trocado.controller;

import com.google.api.server.spi.ServiceException;

/**
 * @author krico
 * @since 28/05/16.
 */
public class OperationFailedException extends ServiceException {
    public OperationFailedException(String statusMessage, Throwable cause) {
        super(500, statusMessage, cause);
    }
}
