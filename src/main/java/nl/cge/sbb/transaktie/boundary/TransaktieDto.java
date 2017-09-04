package nl.cge.sbb.transaktie.boundary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chris on 08-05-17.
 */
public class TransaktieDto {

    private LocalDate transaktiedatum;
    private String omschrijving;
    private BigDecimal bedrag;
    private String tegenrekening;
    private String rekening;
    private List<String> tags = new ArrayList<>();

    public LocalDate getTransaktiedatum() {
        return transaktiedatum;
    }

    public void setTransaktiedatum(LocalDate transaktiedatum) {
        this.transaktiedatum = transaktiedatum;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setTegenrekening(String tegenrekening) {
        this.tegenrekening = tegenrekening;
    }

    public String getTegenrekening() {
        return tegenrekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getRekening() {
        return rekening;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(this.tags);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }
}
