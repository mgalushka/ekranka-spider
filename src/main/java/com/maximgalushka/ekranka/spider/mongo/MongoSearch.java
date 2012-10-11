package com.maximgalushka.ekranka.spider.mongo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.maximgalushka.ekranka.spider.domain.Film;

import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 11.10.12
 */
public class MongoSearch {

    private MongoConnectionHelper helper = MongoConnectionHelper.getInstance();

    public boolean existed(Film find) throws UnknownHostException {
        Datastore ds = helper.getConnection();

        Query<Film> c = ds.createQuery(Film.class);
        c.criteria("title").equal(find.getTitle());

        ArrayList<Film> result = new ArrayList<Film>(c.asList());
        return !result.isEmpty();
    }
}
