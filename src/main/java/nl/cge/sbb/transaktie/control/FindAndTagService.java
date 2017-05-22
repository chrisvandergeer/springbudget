package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.tag.entity.Tag;
import nl.cge.sbb.transaktie.entity.Transaktie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by chris on 10-05-17.
 */
@Service
public class FindAndTagService {

    @Autowired
    private FindTransaktiesService findTransaktiesService;

    @Autowired
    private MaakTagService maakTagService;

    public List<Transaktie> findAndTag(String search, String tagName) {
        Tag tag = maakTagService.maakOfHaalTag(tagName);
        List<Transaktie> transakties = findTransaktiesService.find(Optional.of(search));
        tag.addTransakties(transakties);
        transakties.forEach(tran -> tran.addTag(tag));
        return transakties;
    }


}
