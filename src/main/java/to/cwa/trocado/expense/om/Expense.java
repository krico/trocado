package to.cwa.trocado.expense.om;

import com.google.appengine.api.datastore.Category;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import to.cwa.trocado.account.om.Account;
import to.cwa.trocado.om.HasId;
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
    @Index
    private Date created;
    /**
     * Last time this expense was modified
     */
    @Index
    private Date modified;
    /**
     * when this expense happened
     */
    @Index
    private Date date;
    private String description;
    private BigDecimal amount;
    @Index
    private Category origin;
    /**
     * The account to which this expense belongs
     */
    @Index
    @Load
    private Ref<Account> account;

    public Expense() {
        this(Origins.User);
    }

    protected Expense(Category origin, String description, BigDecimal amount, Date date) {
        this(origin);
        this.description = description;
        this.amount = amount;
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

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Account getAccount() {
        return ObjectsUtil.get(account);
    }

    public void setAccount(Account account) {
        this.account = Ref.create(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;

        Expense expense = (Expense) o;

        if (id != null ? !id.equals(expense.id) : expense.id != null) return false;
        if (created != null ? !created.equals(expense.created) : expense.created != null) return false;
        if (modified != null ? !modified.equals(expense.modified) : expense.modified != null) return false;
        if (date != null ? !date.equals(expense.date) : expense.date != null) return false;
        if (description != null ? !description.equals(expense.description) : expense.description != null) return false;
        if (amount != null ? !amount.equals(expense.amount) : expense.amount != null) return false;
        return origin != null ? origin.equals(expense.origin) : expense.origin == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
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
