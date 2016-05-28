package to.cwa.trocado.expense;

import com.google.api.server.spi.config.*;
import com.googlecode.objectify.Key;
import to.cwa.trocado.controller.Checks;
import to.cwa.trocado.controller.EntityNotFoundException;
import to.cwa.trocado.controller.IllegalEntityException;
import to.cwa.trocado.controller.OperationFailedException;
import to.cwa.trocado.controller.transformer.BigDecimalTransformer;
import to.cwa.trocado.expense.om.Expense;
import to.cwa.trocado.upload.UploadController;
import to.cwa.trocado.upload.om.UploadResult;

import java.io.Reader;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * @author krico
 * @since 09/02/16.
 */
@Api(name = "expense", description = "Trocado - Expense", version = "v1", authLevel = AuthLevel.NONE,
        transformers = {BigDecimalTransformer.class},
        namespace = @ApiNamespace(ownerDomain = "cwa.to", ownerName = "trocado", packagePath = ""))
public class ExpenseController {

    @ApiMethod(name = "save", path = "expenses", httpMethod = ApiMethod.HttpMethod.POST)
    public Expense save(Expense entity) throws IllegalEntityException {
        Checks.checkIdIsNull(entity);
        ofy().save().entity(entity).now();
        return entity;
    }

    @ApiMethod(name = "get", path = "expenses/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Expense get(@Named("id") Long id) throws EntityNotFoundException {
        return Checks.checkFound(ofy().load().key(Key.create(Expense.class, id)).now());
    }

    @ApiMethod(name = "query", path = "expenses", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Expense> query() {
        return ofy().load().type(Expense.class).order("-date").list();
    }

    @ApiMethod(name = "update", path = "expenses/{id}", httpMethod = ApiMethod.HttpMethod.PUT)
    public Expense update(@Named("id") Long id, Expense entity) throws EntityNotFoundException {
        //TODO: check it exists?
        entity.setId(id);
        ofy().save().entity(entity).now();
        return entity;
    }

    @ApiMethod(name = "batch", path = "expenses", httpMethod = ApiMethod.HttpMethod.PUT)
    public List<Expense> batch(UploadResult uploadResult) throws EntityNotFoundException, OperationFailedException {
        String uploadId = Checks.checkFound(uploadResult.getId());
        UploadController uploadController = new UploadController();
        Reader reader = uploadController.get(uploadId).openReader();
        ExpenseImporter importer = new ExpenseImporter();
        List<Expense> imported;
        try {
            imported = importer.parse(reader);
            importer.save(imported);
        } catch (Exception e) {
            throw new OperationFailedException("Failed to import uploaded data", e);
        }
        return imported;
    }

    @ApiMethod(name = "delete", path = "expenses/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void delete(@Named("id") Long id) throws EntityNotFoundException {
        //TODO: check it exists?
        ofy().delete().key(Key.create(Expense.class, id)).now();
    }
}
