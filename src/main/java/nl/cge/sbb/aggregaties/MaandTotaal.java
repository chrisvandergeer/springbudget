package nl.cge.sbb.aggregaties;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by chris on 07-09-17.
 */
public class MaandTotaal {

    private final LocalDate datum;
    private final BigDecimal totaal;

    public MaandTotaal(LocalDate datum, BigDecimal totaal) {
        this.datum = datum;
        this.totaal = totaal;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public BigDecimal getTotaal() {
        return totaal;
    }
}
