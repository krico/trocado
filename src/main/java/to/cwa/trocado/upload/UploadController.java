package to.cwa.trocado.upload;

import com.google.api.server.spi.config.*;
import com.google.appengine.api.memcache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import to.cwa.trocado.controller.Checks;
import to.cwa.trocado.controller.EntityNotFoundException;
import to.cwa.trocado.upload.om.Upload;
import to.cwa.trocado.upload.om.UploadResult;

import java.util.UUID;

/**
 * @author krico
 * @since 07/05/16.
 */
@Api(name = "upload", description = "Trocado - Upload", version = "v1", authLevel = AuthLevel.NONE,
        namespace = @ApiNamespace(ownerDomain = "cwa.to", ownerName = "trocado", packagePath = ""))
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @ApiMethod(name = "save", path = "uploads", httpMethod = ApiMethod.HttpMethod.POST)
    public UploadResult save(Upload entity) {
        final String id = makeId();
        memcache().put(id, entity);
        return new UploadResult(id, entity);
    }

    @ApiMethod(name = "get", path = "uploads/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Upload get(@Named("id") String id) throws EntityNotFoundException {
        return Checks.checkFound((Upload) memcache().get(id));
    }

    static MemcacheService memcache() {
        MemcacheService service = MemcacheServiceFactory.getMemcacheService();
        service.setErrorHandler(TheErrorHandler.INSTANCE);
        return service;
    }

    private static class TheErrorHandler implements ConsistentErrorHandler {
        private static final TheErrorHandler INSTANCE = new TheErrorHandler();

        @Override
        public void handleDeserializationError(InvalidValueException e) {
            log.warn("Failed to deserialize memcache entry", e);
        }

        @Override
        public void handleServiceError(MemcacheServiceException e) {
            log.warn("Service error in memcache", e);
        }
    }


    private String makeId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
