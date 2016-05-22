package to.cwa.trocado.expense.om;

import com.googlecode.objectify.annotation.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author krico
 * @since 18/05/16.
 */
@Entity
public class ChaseImportedExpense extends Expense {
    public static enum ChaseType {
        CREDIT, DEBIT, CHECK
    }

    private String checkOrPlaySlipNumber;
    private ChaseType chaseType;

    public ChaseImportedExpense() {
        super(Origins.Import);
    }

    public ChaseImportedExpense(String description, BigDecimal amount, String checkOrPlaySlipNumber, Date date, ChaseType type) {
        super(Origins.Import, description, amount, date);
        this.checkOrPlaySlipNumber = checkOrPlaySlipNumber;
        this.chaseType = type;
    }

    public String getCheckOrPlaySlipNumber() {
        return checkOrPlaySlipNumber;
    }

    public void setCheckOrPlaySlipNumber(String checkOrPlaySlipNumber) {
        this.checkOrPlaySlipNumber = checkOrPlaySlipNumber;
    }


    public ChaseType getChaseType() {

        return chaseType;
    }

    public void setChaseType(ChaseType chaseType) {
        this.chaseType = chaseType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChaseImportedExpense)) return false;
        if (!super.equals(o)) return false;

        ChaseImportedExpense that = (ChaseImportedExpense) o;

        if (chaseType != that.chaseType) return false;
        if (checkOrPlaySlipNumber != null ? !checkOrPlaySlipNumber.equals(that.checkOrPlaySlipNumber) : that.checkOrPlaySlipNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checkOrPlaySlipNumber != null ? checkOrPlaySlipNumber.hashCode() : 0);
        result = 31 * result + (chaseType != null ? chaseType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChaseImportedExpense{" +
                "checkOrPlaySlipNumber='" + checkOrPlaySlipNumber + '\'' +
                ", chaseType=" + chaseType +
                '}' + super.toString();
    }
}
