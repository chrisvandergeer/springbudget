package nl.cge.sbb.transaktie.boundary;

import java.math.BigDecimal;

/**
 * Created by chris on 08-05-17.
 */
public class TransaktieDto {

    private String transaktiedatum;
    private String omschrijving;
    private BigDecimal bedrag;
    private String tegenrekening;
    private String rekening;

    public void setTransaktiedatum(String transaktiedatum) {
        this.transaktiedatum = transaktiedatum;
    }

    public String getTransaktiedatum() {
        return transaktiedatum;
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
}
