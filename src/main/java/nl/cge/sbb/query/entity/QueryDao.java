package nl.cge.sbb.query.entity;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by chris on 22-05-17.
 */
@Repository
public class QueryDao {

    private DB database;
    HTreeMap<String, String> queryAndTagDb;

    @PostConstruct
    protected void init() {
        database = DBMaker.fileDB("queries.db").fileMmapEnable().make();
        queryAndTagDb = database.hashMap("queryAndTag", Serializer.STRING, Serializer.STRING).createOrOpen();
    }
    
    public void save(String query, String tag) {
        queryAndTagDb.put(query, tag);
        database.commit();
    }

    public boolean delete(String query) {
        Optional<String> optionalQuery = queryAndTagDb.keySet().stream().filter(q -> q.equals(query)).findAny();
        if (optionalQuery.isPresent()) {
            queryAndTagDb.remove(optionalQuery.get());
            database.commit();
            return true;
        }
        return false;
    }

    public Map<String, String> readAll() {
        Map<String, String> result = new HashMap<String, String>();
        for (Object keyObject : queryAndTagDb.keySet()) {
            String key = (String) keyObject;
            result.put(key, queryAndTagDb.get(key));
        }
        return result;
    }


    public void close() {
        if (!database.isClosed()) {
            database.close();
        }
    }
}
