package to.cwa.trocado.account.om;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import to.cwa.trocado.om.HasId;

/**
 * @author krico
 * @since 09/02/16.
 */
@Entity
public class Account implements HasId<Long> {
    @Id
    private Long id;
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
