package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.Transakties;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by chris on 08-05-17.
 */
public class ImportTransaktieServiceTest {

    private Stream<Transaktie> stream;

    @Before
    public void setup() {
        ImportTransaktieService service = new ImportTransaktieService();
        service.transakties = new Transakties();
        stream = service.execute("csvinput.csv").getWrappedTransakties().stream();
    }

    @Test
    public void testExecute() {
        List<Transaktie> result = stream.collect(Collectors.toList());
        assertEquals(160, result.size());
        Transaktie transaktie = result.get(15);

        assertEquals("NL60RABO0393127559", transaktie.getRekening().getNummer());
        assertEquals(LocalDate.parse("20150928", DateTimeFormatter.ofPattern("yyyyMMdd")), transaktie.getTransaktiedatum());
        assertEquals(new BigDecimal("-35.00"), transaktie.getBedrag());
        assertEquals("NL86INGB0002445588", transaktie.getTegenrekening().getNummer());
        assertEquals("BELASTINGDIENST", transaktie.getTegenrekening().getNaam());
        assertEquals("07-HF-RL 3E MND TIJDVAK 22/06/15-22/09/15 MEER INFO WWW.BELASTINGDIENST.NL", transaktie.getOmschrijvingAsString());
    }

    @Test
    public void testTegenrekeningWordtGekopieerd() {
        List<Transaktie> transakties = stream
                .filter(transaktie -> "NL86INGB0002445588".equals(transaktie.getTegenrekening().getNummer()))
                .collect(Collectors.toList());
        assertEquals(12, transakties.size());
        assertTrue(transakties.get(0).getTegenrekening() == transakties.get(1).getTegenrekening());
        assertTrue(transakties.get(1).getTegenrekening() == transakties.get(2).getTegenrekening());
        assertEquals(12, transakties.get(1).getTegenrekening().getTransakties().size());
    }

    @Test
    public void testRekeningWordtGekopieerd() {
        List<Transaktie> transakties = stream
                .filter(t -> "NL60RABO0393127559".equals(t.getRekening().getNummer()))
                .collect(Collectors.toList());
        assertEquals(160, transakties.size());
        assertTrue(transakties.get(0).getRekening() == transakties.get(2).getRekening());
        assertEquals(160, transakties.get(0).getRekening().getTransakties().size());
    }

}