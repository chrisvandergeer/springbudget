package nl.cge.sbb.aggregaties;

import nl.cge.sbb.transaktie.entity.Transaktie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by chris on 05-09-17.
 */
public class MaandTotaalResult {

    private Map<LocalDate, BigDecimal> totalen = new TreeMap<>();

    public void add(Transaktie t) {
        LocalDate firstOfMonth = t.getTransaktiedatum().withDayOfMonth(1);
        if (!totalen.containsKey(firstOfMonth)) {
            totalen.put(firstOfMonth, BigDecimal.ZERO);
        }
        BigDecimal nieuwTotaal = totalen.get(firstOfMonth).add(t.getBedrag());
        totalen.put(firstOfMonth, nieuwTotaal);
    }

    public Map<LocalDate, BigDecimal> getTotalen() {
        return totalen;
    }

    public void finish() {
        if (getTotalen().size() > 0) {
            Optional<LocalDate> min = totalen.keySet().stream().min((a, b) -> a.compareTo(b));
            Optional<LocalDate> max = totalen.keySet().stream().max((a, b) -> a.compareTo(b));
            LocalDate current = min.get().withDayOfMonth(1);
            while (current.isBefore(max.get())) {
                current = current.plusMonths(1);
                if (!totalen.containsKey(current)) {
                    totalen.put(current, BigDecimal.ZERO);
                }
            }
        }
    }
}
