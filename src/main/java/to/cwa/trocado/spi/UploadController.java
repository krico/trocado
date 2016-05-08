package to.cwa.trocado.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.AuthLevel;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import to.cwa.trocado.om.upload.Upload;
import to.cwa.trocado.om.upload.UploadResult;

import java.util.UUID;

/**
 * @author krico
 * @since 07/05/16.
 */
@Api(name = "upload", description = "Trocado - Upload", version = "v1", authLevel = AuthLevel.NONE,
        namespace = @ApiNamespace(ownerDomain = "cwa.to", ownerName = "trocado", packagePath = ""))
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @ApiMethod(name = "upload", path = "upload", httpMethod = ApiMethod.HttpMethod.POST)
    public UploadResult upload(Upload upload) {
        log.info("{}", upload);
        byte[] data = Base64.decodeBase64(upload.getBase64Data());
        log.info("{}", new String(data));
        return new UploadResult(UUID.randomUUID().toString().replaceAll("-", ""), upload);
    }

}
