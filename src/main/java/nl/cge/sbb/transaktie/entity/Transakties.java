package nl.cge.sbb.transaktie.entity;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by chris on 03-04-17.
 */
@Service
public class Transakties {

    private List<Transaktie> wrappedTransakties = new ArrayList<>();
    private Map<String, Rekening> rekeningen = new HashMap<>();
    private Map<String, Rekening> tegenrekeningen = new HashMap<>();

    public Rekening getOrCreateRekening(String rekeningnummer) {
        if (rekeningen.containsKey(rekeningnummer)) {
            return rekeningen.get(rekeningnummer);
        }
        Rekening rekening = new Rekening(rekeningnummer);
        rekeningen.put(rekeningnummer, rekening);
        return rekening;
    }

    public Rekening getOrCreateTegenrekening(String rekeningnummer) {
        if (tegenrekeningen.containsKey(rekeningnummer)) {
            return tegenrekeningen.get(rekeningnummer);
        }
        Rekening rekening = new Rekening(rekeningnummer);
        tegenrekeningen.put(rekeningnummer, rekening);
        return rekening;
    }

    public void addTransakties(List<Transaktie> transakties) {
        boolean b = this.wrappedTransakties.addAll(transakties);
    }

    public List<Transaktie> getWrappedTransakties() {
        return Collections.unmodifiableList(this.wrappedTransakties);
    }

    public void clear() {
        this.wrappedTransakties = new ArrayList<>();
    }

    public Stream<Transaktie> getStream() {
        return this.wrappedTransakties.stream();
    }
}
