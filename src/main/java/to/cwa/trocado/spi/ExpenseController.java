package to.cwa.trocado.spi;

import com.google.api.server.spi.config.*;
import com.googlecode.objectify.Key;
import to.cwa.trocado.om.Expense;
import to.cwa.trocado.util.ControllerUtil;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * @author krico
 * @since 09/02/16.
 */
@Api(name = "expense", description = "Trocado - Expense", version = "v1", authLevel = AuthLevel.NONE,
        namespace = @ApiNamespace(ownerDomain = "cwa.to", ownerName = "trocado", packagePath = ""))
public class ExpenseController {

    @ApiMethod(name = "save", path = "expenses", httpMethod = ApiMethod.HttpMethod.POST)
    public Expense save(Expense entity) throws IllegalEntityException {
        ControllerUtil.checkIdIsNull(entity);
        ofy().save().entity(entity).now();
        return entity;
    }

    @ApiMethod(name = "get", path = "expenses/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Expense get(@Named("id") Long id) throws EntityNotFoundException {
        return ControllerUtil.checkFound(ofy().load().key(Key.create(Expense.class, id)).now());
    }

    @ApiMethod(name = "query", path = "expenses", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Expense> query() {
        return ofy().load().type(Expense.class).list();
    }

    @ApiMethod(name = "update", path = "expenses/{id}", httpMethod = ApiMethod.HttpMethod.PUT)
    public Expense update(@Named("id") Long id, Expense entity) throws EntityNotFoundException {
        //TODO: check it exists?
        entity.setId(id);
        ofy().save().entity(entity).now();
        return entity;
    }

    @ApiMethod(name = "delete", path = "expenses/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void delete(@Named("id") Long id) throws EntityNotFoundException {
        //TODO: check it exists?
        ofy().delete().key(Key.create(Expense.class, id)).now();
    }
}
