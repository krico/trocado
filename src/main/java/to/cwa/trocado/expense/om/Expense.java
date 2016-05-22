package to.cwa.trocado.expense.om;

import com.google.appengine.api.datastore.Category;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;
import to.cwa.trocado.om.HasId;
import to.cwa.trocado.util.BigDecimalUtil;
import to.cwa.trocado.util.ObjectsUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author krico
 * @since 10/02/16.
 */
@Entity
public class Expense implements HasId<Long> {
    @Id
    private Long id;
    /**
     * Creation date of this expense (first time it was saved)
     */
    private Date created;
    /**
     * Last time this expense was modified
     */
    private Date modified;
    /**
     * when this expense happened
     */
    private Date date;
    private String description;
    private long amount;
    private Category origin;

    public Expense() {
        this(Origins.User);
    }

    protected Expense(Category origin, String description, BigDecimal amount, Date date) {
        this(origin);
        this.description = description;
        this.amount = BigDecimalUtil.amount(amount);
        this.date = date;
    }

    protected Expense(Category origin) {
        this.origin = origin;
    }

    @SuppressWarnings("UnusedDeclaration")
    @OnSave
    void updateDates() {
        modified = new Date();
        if (created == null) created = modified;
        if (date == null) date = created;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return BigDecimalUtil.amount(amount);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = BigDecimalUtil.amount(amount);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getOrigin() {
        return origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;

        Expense expense = (Expense) o;

        if (amount != expense.amount) return false;
        if (created != null ? !created.equals(expense.created) : expense.created != null) return false;
        if (date != null ? !date.equals(expense.date) : expense.date != null) return false;
        if (description != null ? !description.equals(expense.description) : expense.description != null) return false;
        if (id != null ? !id.equals(expense.id) : expense.id != null) return false;
        if (modified != null ? !modified.equals(expense.modified) : expense.modified != null) return false;
        if (origin != null ? !origin.equals(expense.origin) : expense.origin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", created=" + created +
                ", modified=" + modified +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", origin=" + ObjectsUtil.toString(origin) +
                '}';
    }

}
