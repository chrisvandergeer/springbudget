package nl.cge.sbb.transaktie.boundary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 03-09-17.
 */
public class SearchResult {

    private List<TransaktieDto> data = new ArrayList<>();

    public SearchResult(List<TransaktieDto> transakties) {
        this.data = transakties;
    }

    public List<TransaktieDto> getData() {
        return data;
    }
}
