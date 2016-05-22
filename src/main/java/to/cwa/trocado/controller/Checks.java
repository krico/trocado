package to.cwa.trocado.controller;

import to.cwa.trocado.om.HasId;

/**
 * @author krico
 * @since 09/02/16.
 */
public final class Checks {
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
    private Checks() {
    }

    public static void checkIdIsNull(HasId<?> entity) throws IllegalEntityException {
        if (entity.getId() != null)
            throw new IllegalEntityException(entity, entity.getClass().getSimpleName() +
                    " ID expected: null (actual: " + entity.getId() + ")");
    }

    public static <T> T checkFound(T entity) throws EntityNotFoundException {
        if (entity == null) throw new EntityNotFoundException();
        return entity;
    }
}
