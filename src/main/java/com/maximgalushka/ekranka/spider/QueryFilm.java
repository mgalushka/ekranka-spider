package com.maximgalushka.ekranka.spider;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Criteria;
import com.maximgalushka.ekranka.spider.domain.Film;
import com.maximgalushka.ekranka.spider.mongo.MongoConnectionHelper;
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

        MongoConnectionHelper mch = MongoConnectionHelper.getInstance();

        List<Film> result = mch.getConnection().find(Film.class).field("year").greaterThan(2008).asList();

        System.out.printf("Films > 2008:\n %s\n", result);

    }
}
