package nl.cge.sbb.aggregaties;

import nl.cge.sbb.transaktie.control.FindTransaktiesService;
import nl.cge.sbb.transaktie.entity.Transaktie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by chris on 05-09-17.
 */

@RestController
@RequestMapping("/aggregatie")
public class MaandTotalenBoundary {

    @Autowired
    private FindTransaktiesService findTransaktiesService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MaandTotaal> find(@RequestParam(value = "search", required = false) String searchOrNull) {
        List<Transaktie> transakties = findTransaktiesService.find(Optional.of(searchOrNull));
        MaandTotaalResult result = new MaandTotaalResult();
        transakties.forEach(t -> result.add(t));
        result.finish();
        List<MaandTotaal> list = new ArrayList<>();
        Map<LocalDate, BigDecimal> totalen = result.getTotalen();
        for (LocalDate datum : totalen.keySet()) {
            list.add(new MaandTotaal(datum, totalen.get(datum)));
        }
        return list;
    }
}
