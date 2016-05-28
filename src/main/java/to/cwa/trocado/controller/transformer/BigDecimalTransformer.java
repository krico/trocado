package to.cwa.trocado.controller.transformer;

import com.google.api.server.spi.config.Transformer;

import java.math.BigDecimal;

/**
 * @author krico
 * @since 25/05/16.
 */
public class BigDecimalTransformer implements Transformer<BigDecimal, Double> {
    @Override
    public Double transformTo(BigDecimal bigDecimal) {
        if (bigDecimal == null) return null;
        return bigDecimal.doubleValue();
    }

    @Override
    public BigDecimal transformFrom(Double aDouble) {
        if (aDouble == null) return null;
        return BigDecimal.valueOf(aDouble);
    }
}
