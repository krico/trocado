package to.cwa.trocado.util;

import to.cwa.trocado.om.HasId;
import to.cwa.trocado.spi.EntityNotFoundException;
import to.cwa.trocado.spi.IllegalEntityException;

/**
 * @author krico
 * @since 09/02/16.
 */
public final class ControllerUtil {
    /*
        Corresponding HTTP Status Code
        com.google.api.server.spi.response.BadRequestException	HTTP 400
        com.google.api.server.spi.response.UnauthorizedException	HTTP 401
        com.google.api.server.spi.response.ForbiddenException	HTTP 403
        com.google.api.server.spi.response.NotFoundException	HTTP 404
        com.google.api.server.spi.response.ConflictException	HTTP 409
        com.google.api.server.spi.response.InternalServerErrorException	HTTP 500
        com.google.api.server.spi.response.ServiceUnavailableException	HTTP 503
     */
    private ControllerUtil() {
    }

    public static void checkIdIsNull(HasId<?> entity) throws IllegalEntityException {
        if (entity.getId() != null)
            throw new IllegalEntityException(entity, "ID expected: null (actual: " + entity.getId() + ")");
    }

    public static <T> T checkFound(T entity) throws EntityNotFoundException {
        if (entity == null) throw new EntityNotFoundException();
        return entity;
    }
}
