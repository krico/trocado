package to.cwa.trocado.util;

import java.math.BigDecimal;

/**
 * @author krico
 * @since 21/05/16.
 */
public final class BigDecimalUtil {
    public static final int AMOUNT_PRECISION = 6;
    public static final BigDecimal AMOUNT_MULTIPLIER = BigDecimal.valueOf(Math.pow(10, AMOUNT_PRECISION));
    public static final long UNSET_AMOUNT = 0l;

    private BigDecimalUtil() {
    }

    public static long amount(BigDecimal amount) {
        if (amount == null) return UNSET_AMOUNT;
        return amount.multiply(AMOUNT_MULTIPLIER).longValue();
    }

    public static long amount(String amount) {
        if (amount == null) return UNSET_AMOUNT;
        return amount(new BigDecimal(amount));
    }

    public static long amount(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount)) return UNSET_AMOUNT;
        return amount(BigDecimal.valueOf(amount));
    }

    public static BigDecimal amount(long amount) {
        return BigDecimal.valueOf(amount, AMOUNT_PRECISION);
    }
}
