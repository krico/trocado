package to.cwa.trocado.om;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author krico
 * @since 10/02/16.
 */
@Entity
public class Expense implements HasId<Long> {
    @Id
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
