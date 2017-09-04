package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.arch.control.Mapper;
import nl.cge.sbb.tag.entity.Tag;
import nl.cge.sbb.transaktie.boundary.TransaktieDto;
import nl.cge.sbb.transaktie.entity.Transaktie;
import org.springframework.stereotype.Component;

/**
 * Created by chris on 10-05-17.
 */
@Component
public class TransaktieDtoMapper extends Mapper<Transaktie, TransaktieDto> {

    @Override
    public TransaktieDto map(Transaktie transaktie) {
        TransaktieDto dto = new TransaktieDto();
        dto.setBedrag(transaktie.getBedrag());
        dto.setOmschrijving(transaktie.getOmschrijvingAsString());
        dto.setRekening(transaktie.getRekening().getAsString());
        dto.setTegenrekening(transaktie.getTegenrekening().getAsString());
        dto.setTransaktiedatum(transaktie.getTransaktiedatum());
        for (Tag tag : transaktie.getTags()) {
            dto.addTag(tag.getNaam());
        }
        return dto;
    }
}
