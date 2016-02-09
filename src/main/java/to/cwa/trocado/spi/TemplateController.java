package to.cwa.trocado.spi;

import com.google.api.server.spi.config.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import to.cwa.trocado.om.HasId;
import to.cwa.trocado.util.ControllerUtil;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * This is an example controller to create new controllers...  Should disapear later.
 *
 * @author krico
 * @since 09/02/16.
 */
@Api(name = "template", description = "Trocado - Template", version = "v1", authLevel = AuthLevel.NONE,
        namespace = @ApiNamespace(ownerDomain = "cwa.to", ownerName = "trocado", packagePath = ""))
public class TemplateController {
    @Entity
    public static class Template implements HasId<Long> {
        @Id
        private Long id;
        private String data;

        @Override
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    @ApiMethod(name = "save", path = "templates", httpMethod = ApiMethod.HttpMethod.POST)
    public Key<Template> save(Template entity) throws IllegalEntityException {
        ControllerUtil.checkIdIsNull(entity);
        return ofy().save().entity(entity).now();
    }

    @ApiMethod(name = "get", path = "templates/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Template get(@Named("id") Key<Template> id) throws EntityNotFoundException {
        return ControllerUtil.checkFound(ofy().load().key(id).now());
    }

    @ApiMethod(name = "query", path = "templates", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Template> query() {
        return ofy().load().type(Template.class).list();
    }

    @ApiMethod(name = "update", path = "templates/{id}", httpMethod = ApiMethod.HttpMethod.PUT)
    public void update(@Named("id") Key<Template> id, Template entity) throws EntityNotFoundException {
        //TODO: check it exists?
        entity.setId(id.getId());
        ofy().save().entity(entity).now();
    }

    @ApiMethod(name = "delete", path = "templates/{id}", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void delete(@Named("id") Key<Template> id) throws EntityNotFoundException {
        //TODO: check it exists?
        ofy().delete().key(id).now();
    }

}
