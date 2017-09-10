package nl.cge.sbb.aggregaties.boundary;

import nl.cge.sbb.aggregaties.entity.MaandTotaal;
import nl.cge.sbb.aggregaties.control.MaandTotaalAggregator;
import nl.cge.sbb.transaktie.control.FindTransaktiesService;
import nl.cge.sbb.transaktie.entity.Transaktie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
        MaandTotaalAggregator result = new MaandTotaalAggregator();
        transakties.forEach(t -> result.add(t));
        return result.finish();
    }
}
