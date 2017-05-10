package nl.cge.sbb.transaktie.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chris on 03-04-17.
 */
public class Tag {

    private String naam;
    private Set<Transaktie> transakties = new HashSet<>();

    public Tag(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public void addTransaktie(Transaktie transaktie) {
        transakties.add(transaktie);
    }

    public void addTransakties(Collection<Transaktie> transakties2add) {
        this.transakties.addAll(transakties2add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return naam.equals(tag.naam);
    }

    @Override
    public int hashCode() {
        return naam.hashCode();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "naam='" + naam + '\'' +
                '}';
    }
}
