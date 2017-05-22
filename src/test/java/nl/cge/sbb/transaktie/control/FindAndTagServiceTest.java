package nl.cge.sbb.transaktie.control;

import nl.cge.sbb.tag.entity.Tag;
import nl.cge.sbb.transaktie.entity.Transaktie;
import nl.cge.sbb.transaktie.entity.TransaktieBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by chris on 10-05-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class FindAndTagServiceTest {

    @Mock
    private FindTransaktiesService findTransaktiesService;

    @Mock
    private MaakTagService maakTagService;

    @InjectMocks
    private FindAndTagService cut;

    @Test
    public void test() {
        // GIVEN
        when(maakTagService.maakOfHaalTag("myTag"))
                .thenReturn(new Tag("myTag"));

        when(findTransaktiesService.find(Optional.of("mySearchText")))
                .thenReturn(Arrays.asList(
                        TransaktieBuilder.instance().build(),
                        TransaktieBuilder.instance().withTag("anotherTag").build(),
                        TransaktieBuilder.instance().withTag("myTag").build()
                ));

        // WHEN
        List<Transaktie> result = cut.findAndTag("mySearchText", "myTag");

        // THEN
        assertTrue(result.get(0).hasTag("myTag"));
        assertEquals(1, result.get(0).getTags().size());

        assertTrue(result.get(1).hasTag("myTag"));
        assertEquals(2, result.get(1).getTags().size());

        assertTrue(result.get(2).hasTag("myTag"));
        assertEquals(1, result.get(2).getTags().size());
    }


}