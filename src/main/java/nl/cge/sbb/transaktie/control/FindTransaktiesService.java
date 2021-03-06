package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.Transakties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream<Transaktie> transaktieStream = transakties.getWrappedTransakties()
                .stream()
                .filter(t -> t.hasTag(tag));
        if (query.isPresent()) {
            int i = 0;
            for (String q : query.get().split(" ")) {
                System.out.println(++i + " Query: " + q);
                transaktieStream = transaktieStream.filter(t -> t.getAsString().contains(q));
            }
        }
        return transaktieStream.collect(Collectors.toList());
    }

}
