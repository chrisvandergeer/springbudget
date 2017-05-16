package nl.cge.sbb.transaktie.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by geerc01 on 31-3-2017.
 */
public class Transaktie {

    private Rekening rekening = new Rekening();
    private LocalDate transaktiedatum;
    private BigDecimal bedrag;
    private Rekening tegenrekening = new Rekening();
    private List<String> omschrijving = new ArrayList<>();

    private Set<Tag> tags = new HashSet<>();

    public Transaktie(String rekeningnummer, LocalDate transaktiedatum, BigDecimal bedrag, String... omschrijvingsregels) {
        this.rekening.setNummer(rekeningnummer);
        this.transaktiedatum = transaktiedatum;
        this.bedrag = bedrag;
        Arrays.stream(omschrijvingsregels).forEach(t -> omschrijving.add(t));
    }

    public List<String> getOmschrijving() {
        return Collections.unmodifiableList(omschrijving);
    }

    public String getOmschrijvingAsString() {
        StringBuilder result = new StringBuilder();
        omschrijving.stream().forEach(regel -> result.append(regel).append(" "));
        return result.toString().trim();
    }

    public LocalDate getTransaktiedatum() {
        return transaktiedatum;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public Rekening getTegenrekening() {
        return tegenrekening;
    }

    public void setTegenrekening(Rekening tegenrekening) {
        this.tegenrekening = tegenrekening;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }

    public void addOmschrijvingsregel(String regel) {
        omschrijving.add(regel);
    }

    public Integer getJaar() {
        return transaktiedatum.getYear();
    }

    public Integer getMaand() {
        return transaktiedatum.getMonthValue();
    }

    public boolean hasTag(String tagName) {
        return tags.stream().anyMatch(t -> tagName.equals(t.getNaam()));
    }

    public Transaktie addTag(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Tag getTag(String tagNaam) {
        return this.tags.stream()
                .filter(t -> tagNaam.equals(t.getNaam()))
                .findFirst()
                .get();
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(this.tags);
    }


    public void removeTags() {
        tags.clear();
    }

    @Override
    public String toString() {
        return "Transaktie{" +
                "rekeningnummer='" + rekening.getNummer() + '\'' +
                ", transaktiedatum=" + transaktiedatum +
                ", bedrag=" + bedrag +
                ", tegenrekeningnummer='" + tegenrekening.getNummer() + '\'' +
                ", tegenrekeningnaam='" + tegenrekening.getNaam() + '\'' +
                ", omschrijving=" + getOmschrijvingAsString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaktie)) return false;

        Transaktie that = (Transaktie) o;

        if (!rekening.getNummer().equals(that.rekening.getNummer())) return false;
        if (!transaktiedatum.equals(that.transaktiedatum)) return false;
        if (!bedrag.equals(that.bedrag)) return false;
        if (tegenrekening.getNummer() != null ? !tegenrekening.getNummer().equals(that.tegenrekening.getNummer()) : that.tegenrekening.getNummer() != null)
            return false;
        if (tegenrekening.getNaam() != null ? !tegenrekening.getNaam().equals(that.tegenrekening.getNaam()) : that.tegenrekening.getNaam() != null)
            return false;
        return omschrijving.equals(that.omschrijving);

    }

    @Override
    public int hashCode() {
        int result = rekening.getNummer().hashCode();
        result = 31 * result + transaktiedatum.hashCode();
        result = 31 * result + bedrag.hashCode();
        return result;
    }

}
