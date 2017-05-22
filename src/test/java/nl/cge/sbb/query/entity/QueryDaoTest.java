package nl.cge.sbb.query.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by chris on 22-05-17.
 */
public class QueryDaoTest {

    QueryDao cut;

    @Before
    public void setup() {
        cut = new QueryDao();
        cut.init();
    }

    @Test
    public void test() {
        assertEquals(0, cut.readAll().size());
        cut.save("MyFirstQuery", "myTag");
        assertEquals(1, cut.readAll().size());
        cut.save("MySecondQuery", "myTag");
        assertEquals(2, cut.readAll().size());
        cut.save("MyFirstQuery", "myTag");
        assertEquals(2, cut.readAll().size());
    }

    @After
    public void teardown() {
        cut.readAll().keySet().forEach(q -> cut.delete(q)); // delete all queries
        cut.close();
    }

}