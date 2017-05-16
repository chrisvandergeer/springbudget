package nl.cge.sbb.tag.control;

import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.Transakties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chris on 16-05-17.
 */
@Service
public class DeleteAllTagsService {

    @Autowired
    private Transakties transakties;

    public int delete() {
        int aantalTags = 0;
        for (Transaktie transaktie : transakties.getWrappedTransakties()) {
            aantalTags += transaktie.getTags().size();
            transaktie.removeTags();
        }
        return aantalTags;
    }
}
