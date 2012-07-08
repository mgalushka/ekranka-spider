package com.maximgalushka.ekranka.spider;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.maximgalushka.ekranka.spider.domain.Film;
import com.maximgalushka.ekranka.spider.mongo.MongoConnectionHelper;

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

        MongoConnectionHelper mch = MongoConnectionHelper.getInstance();

        Datastore ds = mch.getConnection();
        Query<Film> q = ds.createQuery(Film.class).filter("duration <", 50).filter("rating >=", 4).order("rating");
        List<Film> result = q.asList();
        System.out.printf("Films:\n %s\n", result);

//        List r = mch.getConnection().getMongo().getDB("").getCollection("films").distinct("director");
//        System.out.printf("Directors: %s\n", r);


    }
}
