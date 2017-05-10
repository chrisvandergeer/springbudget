package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.transaktie.entity.Tag;
import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.Transakties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by chris on 10-05-17.
 */
@Service
public class MaakTagService {

    @Autowired
    private Transakties transakties;

    public Tag maakOfHaalTag(String tagName) {
        Optional<Transaktie> transaktieMetTag = transakties.getStream()
                .filter(t -> t.hasTag(tagName))
                .findAny();
        return transaktieMetTag.isPresent() ? transaktieMetTag.get().getTag(tagName) : new Tag(tagName);

    }
}
