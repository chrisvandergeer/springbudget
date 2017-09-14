package nl.cge.sbb.transaktie.boundary;

import nl.cge.sbb.arch.boundary.BoundaryResult;
import nl.cge.sbb.query.entity.QueryDao;
import nl.cge.sbb.transaktie.control.*;
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
        Transakties transakties = importTransaktieService.execute("transactions.txt");
        Map<String, String> queriesWithTag = queryDao.readAll();
        for (String query : queriesWithTag.keySet()) {
            findAndTag(query, queriesWithTag.get(query));
        }
        return BoundaryResult.success()
                .setMessage(String.format("Aantal transakties: %s", transakties.getWrappedTransakties().size()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TransaktieDto> find(
            @RequestParam(value = "search", required = false) String searchOrNull) {
        List<Transaktie> transakties = findTransaktiesService.find(Optional.ofNullable(searchOrNull));
        return mapper.map(transakties);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<TransaktieDto> findAndTag(
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
        return mapper.map(result);
    }


    private BoundaryResult<List<TransaktieDto>> createResult(List<Transaktie> result) {
        return BoundaryResult.successWithListResult(TransaktieDto.class)
                .setMessage(String.format("Aantal transakties: %s", result.size()))
                .setData(mapper.map(result));
    }

}
