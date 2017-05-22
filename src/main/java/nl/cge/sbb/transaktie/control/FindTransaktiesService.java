package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.Transakties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by chris on 08-05-17.
 */
@Service
public class FindTransaktiesService {

    @Autowired
    private Transakties transakties;

    public List<Transaktie> find(Optional<String> searchString) {
        TagMatcher tagMatcher = new TagMatcher(searchString);
        Optional<String> tag = tagMatcher.getTag();
        Optional<String> query = tagMatcher.getQuery();
        return transakties.getWrappedTransakties()
            .stream()
            .filter(t -> t.getOmschrijvingAsString().contains(query.orElse("")))
            .filter(t -> t.hasTag(tag))
            .collect(Collectors.toList());
    }

    public List<Transaktie> findByRekeningnummer(String rekeningnummer, Optional<String> searchString) {
        return transakties.getWrappedTransakties().stream()
                .filter(t -> rekeningnummer.equals(t.getTegenrekening().getNummer()))
                .filter(t -> t.getOmschrijvingAsString().contains(searchString.orElse("")))
                .collect(Collectors.toList());
    }

    public List<Transaktie> findByJaar(Integer jaar) {
        System.out.println("Jaar: " + jaar);
        return transakties.getWrappedTransakties().stream()
                .filter(t -> t.getJaar().equals(jaar))
                .collect(Collectors.toList());
    }

    public List<Transaktie> findByMaand(Integer jaar, Integer maand) {
        return transakties.getWrappedTransakties().stream()
                .filter(t -> t.getJaar().equals(jaar))
                .filter(t -> t.getMaand().equals(maand))
                .collect(Collectors.toList());
    }

}
