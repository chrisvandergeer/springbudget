package nl.cge.sbb.query.boundary;

import nl.cge.sbb.query.entity.QueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by chris on 14-09-17.
 */
@RestController
@RequestMapping("/query")
public class QueryBoundary {

    @Autowired
    private QueryDao queryDao;

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, String> find() {
        return queryDao.readAll();
    }


}
