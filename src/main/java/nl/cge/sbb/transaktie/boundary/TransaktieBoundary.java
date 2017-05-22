package nl.cge.sbb.transaktie.boundary;

import nl.cge.sbb.arch.boundary.BoundaryResult;
import nl.cge.sbb.query.entity.QueryDao;
import nl.cge.sbb.transaktie.control.FindTransaktiesService;
import nl.cge.sbb.transaktie.control.ImportTransaktieService;
import nl.cge.sbb.transaktie.control.MaakTagService;
import nl.cge.sbb.transaktie.control.TransaktieDtoMapper;
import nl.cge.sbb.tag.entity.Tag;
import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.Transakties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by chris on 08-05-17.
 */
@RestController
@RequestMapping("/transaktie")
public class TransaktieBoundary {

    @Autowired
    private TransaktieDtoMapper mapper;

    @Autowired
    private ImportTransaktieService importTransaktieService;

    @Autowired
    private FindTransaktiesService findTransaktiesService;

    @Autowired
    private MaakTagService maakTagService;

    @Autowired
    private QueryDao queryDao;

    @RequestMapping(value = "/importeer", method = RequestMethod.POST)
    public BoundaryResult<Void> importeer() {
        Transakties transakties = importTransaktieService.execute("csvinput.csv");
        Map<String, String> queriesWithTag = queryDao.readAll();
        for (String query : queriesWithTag.keySet()) {
            findAndTag(query, queriesWithTag.get(query));
        }
        return BoundaryResult.success()
                .setMessage(String.format("Aantal transakties: %s", transakties.getWrappedTransakties().size()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public BoundaryResult<List<TransaktieDto>> findAll(
            @RequestParam(value = "search", required = false) String searchOrNull) {

        return createResult(findTransaktiesService.find(Optional.ofNullable(searchOrNull)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public BoundaryResult<List<TransaktieDto>> findAndTag(
            @RequestParam(value = "search", required = true) String search,
            @RequestParam(value = "tag", required = true) String tagName) {
        List<Transaktie> result = findTransaktiesService.find(Optional.of(search));
        Tag tag = maakTagService.maakOfHaalTag(tagName);
        for (Transaktie t : result) {
            t.addTag(tag);
        }
        tag.addTransakties(result);
        if (!result.isEmpty()) {
            queryDao.save(search, tagName);
        }
        return createResult(result);
    }

    @RequestMapping(value = "/datum/{jaar}", method = RequestMethod.GET)
    public BoundaryResult<List<TransaktieDto>> findByJaar(
            @PathVariable("jaar") Integer jaar) {

        return createResult(findTransaktiesService.findByJaar(jaar));
    }

    @RequestMapping(value = "/datum/{jaar}/{maand}", method = RequestMethod.GET)
        public BoundaryResult<List<TransaktieDto>> findByMaand(
                @PathVariable("jaar") Integer jaar,
                @PathVariable("maand") Integer maand) {

        return createResult(findTransaktiesService.findByMaand(jaar, maand));
    }

    @RequestMapping(value = "/tegenrekening/{rekeningnummer}", method = RequestMethod.GET)
    public BoundaryResult<List<TransaktieDto>> findByRekeningnummer(
            @PathVariable("rekeningnummer") String rekeningnummer,
            @RequestParam(value = "search", required = false) String searchOrNull) {

        return createResult(findTransaktiesService.findByRekeningnummer(
                rekeningnummer, Optional.ofNullable(searchOrNull)));
    }

    private BoundaryResult<List<TransaktieDto>> createResult(List<Transaktie> result) {
        return BoundaryResult.successWithListResult(TransaktieDto.class)
                .setMessage(String.format("Aantal transakties: %s", result.size()))
                .setData(mapper.map(result));
    }

}
