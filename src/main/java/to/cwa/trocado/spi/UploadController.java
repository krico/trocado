package to.cwa.trocado.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.AuthLevel;
import org.apache.commons.codec.binary.Base64;
import to.cwa.trocado.om.upload.Upload;

import java.io.IOException;

/**
 * @author krico
 * @since 07/05/16.
 */
@Api(name = "upload", description = "Trocado - Upload", version = "v1", authLevel = AuthLevel.NONE,
        namespace = @ApiNamespace(ownerDomain = "cwa.to", ownerName = "trocado", packagePath = ""))
public class UploadController {
    @ApiMethod(name = "upload", path = "upload", httpMethod = ApiMethod.HttpMethod.POST)
    public void upload(Upload upload) {
        System.out.println(upload);
        try {
            System.out.write(Base64.decodeBase64(upload.getBase64Data()));
        } catch (IOException e) {
            throw new RuntimeException("Booh", e);
        }
    }

}
