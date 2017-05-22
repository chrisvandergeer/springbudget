package nl.cge.sbb.transaktie.control;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by chris on 18-05-17.
 */
public class TagMatcherTest {

    @Test
    public void test() {
        TagMatcher matcher = new TagMatcher(Optional.of(":boodschappen"));
        assertEquals("boodschappen", matcher.getTag().get());
        assertFalse(matcher.getQuery().isPresent());
    }

    @Test
    public void testGeenTag() {
        TagMatcher matcher = new TagMatcher(Optional.of("boodschappen"));
        assertFalse(matcher.getTag().isPresent());
        assertEquals("boodschappen", matcher.getQuery().get());
    }

    @Test
    public void testTagEnZoektekst() {
        TagMatcher matcher = new TagMatcher(Optional.of(":boodschappen qwerty"));
        assertEquals("boodschappen", matcher.getTag().get());
        assertEquals("qwerty", matcher.getQuery().get());
    }

    @Test
    public void testZoektekstEnTag() {
        TagMatcher matcher = new TagMatcher(Optional.of("qwerty :boodschappen"));
        assertEquals("boodschappen", matcher.getTag().get());
        assertEquals("qwerty", matcher.getQuery().get());
    }
}