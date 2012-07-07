package com.maximgalushka.ekranka.spider;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Criteria;
import com.maximgalushka.ekranka.spider.domain.Film;
import com.mongodb.Mongo;

import java.net.UnknownHostException;
import java.util.List;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 07.07.12
 */
public class QueryFilm {

    public static void main(String[] args) throws UnknownHostException {

        // TODO: gererify this
        Mongo m = new Mongo("ds031597.mongolab.com", 31597);
        Datastore ds = new Morphia().createDatastore(m, "ekranka", "ekranka", "ekranka".toCharArray());

//        Criteria c = new C
        List<Film> result = ds.find(Film.class).field("year").greaterThan(2008).asList();

        System.out.printf("Films > 2008:\n %s\n", result);

    }
}
