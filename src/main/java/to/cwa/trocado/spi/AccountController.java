package to.cwa.trocado.spi;

import com.google.api.server.spi.config.*;
import com.googlecode.objectify.Key;
import to.cwa.trocado.om.Account;
import to.cwa.trocado.util.ControllerUtil;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * @author krico
 * @since 09/02/16.
 */
@Api(name = "account", description = "Trocado - Account", version = "v1", authLevel = AuthLevel.NONE,
        namespace = @ApiNamespace(ownerDomain = "cwa.to", ownerName = "trocado", packagePath = ""))
public class AccountController {

    @ApiMethod(name = "save", path = "accounts", httpMethod = ApiMethod.HttpMethod.POST)
    public Account save(Account entity) throws IllegalEntityException {
        ControllerUtil.checkIdIsNull(entity);
        ofy().save().entity(entity).now();
        return entity;
    }

    @ApiMethod(name = "get", path = "accounts/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Account get(@Named("id") Long id) throws EntityNotFoundException {
        return ControllerUtil.checkFound(ofy().load().key(Key.create(Account.class, id)).now());
    }

    @ApiMethod(name = "query", path = "accounts", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Account> query() {
        return ofy().load().type(Account.class).list();
    }

    @ApiMethod(name = "update", path = "accounts/{id}", httpMethod = ApiMethod.HttpMethod.PUT)
    public Account update(@Named("id") Long id, Account entity) throws EntityNotFoundException {
        //TODO: check it exists?
        entity.setId(id);
        ofy().save().entity(entity).now();
        return entity;
    }

    @ApiMethod(name = "delete", path = "accounts/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void delete(@Named("id") Long id) throws EntityNotFoundException {
        //TODO: check it exists?
        ofy().delete().key(Key.create(Account.class, id)).now();
    }
}
