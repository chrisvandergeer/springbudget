package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.transaktie.entity.Rekening;
import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.Transakties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sun.javafx.util.Utils.stripQuotes;
import static nl.cge.sbb.arch.exceptions.ApplicationException.wrap;


/**
 * Created by geerc01 on 31-3-2017.
 */
@Service
public class ImportTransaktieService {

    public static final String DEBET = "D";

    @Autowired
    Transakties transakties;

    public Transakties execute(String filePath) {
        transakties.clear();
        try {
            List<Transaktie> collect = Files.lines(Paths.get(filePath))
                    .map(mapNaarTransaktie)
                    .collect(Collectors.toList());
            transakties.addTransakties(collect);
            return transakties;
        } catch (IOException e) {
            throw wrap(e);
        }
    }

    private Function<String, Transaktie> mapNaarTransaktie = (regel) -> {
        String[] split = regel.split(",");
        String rekeningnummer = stripQuotes(split[0]);
        LocalDate transaktiedatum = LocalDate.parse(stripQuotes(split[2]), DateTimeFormatter.ofPattern("yyyyMMdd"));
        Transaktie transaktie = new Transaktie(rekeningnummer, transaktiedatum, getBedrag(split), getOmschrijving(split));
        setRekening(split, transaktie);
        setTegenrekening(split, transaktie);
        return transaktie;
    };

    private void setRekening(String[] split, Transaktie transaktie) {
        String rekeningnummer = stripQuotes(split[0]);
        Rekening rekening = transakties.getOrCreateRekening(rekeningnummer);
        rekening.addTransaktie(transaktie);
        transaktie.setRekening(rekening);
    }

    private void setTegenrekening(String[] split, Transaktie transaktie) {
        String rekeningnummer = stripQuotes(split[5]);
        String rekeningnaam = stripQuotes(split[6]);
        Rekening rekening = transakties.getOrCreateTegenrekening(rekeningnummer);
        rekening.setNaam(rekeningnaam);
        rekening.addTransaktie(transaktie);
        transaktie.setTegenrekening(rekening);
    }

    private BigDecimal getBedrag(String[] regel) {
        boolean af = DEBET.equals(stripQuotes(regel[3]));
        BigDecimal bedrag = new BigDecimal(stripQuotes(regel[4]));
        return af ? bedrag.multiply(BigDecimal.valueOf(-1)) : bedrag;
    }

    private String[] getOmschrijving(String[] regel) {
        return Arrays.stream(Arrays.copyOfRange(regel, 10, 13))
                    .map(field -> stripQuotes(field))
                    .collect(Collectors.toList())
                    .toArray(new String[]{});
    }

}
