package nl.cge.sbb.transaktie.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by chris on 10-05-17.
 */
public class TransaktieBuilder {

    private String rekeningnummer = "NL60RABO0393127559";
    private LocalDate transaktiedatum = LocalDate.now();
    private BigDecimal bedrag = BigDecimal.TEN;
    private String omschrijving = "de omschrijving";

    private String tegenrekeningNummer;
    private String tagName;

    public static TransaktieBuilder instance() {
        return new TransaktieBuilder();
    }

    public TransaktieBuilder withTag(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public Transaktie build() {
        Transaktie transaktie = new Transaktie(rekeningnummer, transaktiedatum, bedrag, omschrijving);
        if (tegenrekeningNummer != null) {
            transaktie.setTegenrekening(new Rekening(this.tegenrekeningNummer));
        }
        if (tagName != null) {
            transaktie.addTag(new Tag(tagName));
        }
        return transaktie;
    }
}
