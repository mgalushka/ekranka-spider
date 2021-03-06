package com.maximgalushka.ekranka.spider;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.maximgalushka.ekranka.spider.domain.Film;
import com.maximgalushka.ekranka.spider.mongo.MongoConnectionHelper;
import com.maximgalushka.ekranka.spider.render.HtmlRenderer;

import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

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
    Query<Film> c = ds.createQuery(Film.class);

    c.and(
      // c.criteria("duration").lessThanOrEq(40),
      // c.criteria("rating").greaterThanOrEq(4F),
      // c.criteria("year").greaterThanOrEq(2000)//,
      c.criteria("genres").equal("драма")
      //c.or(
      //    c.criteria("genres").equal("комедия")
      //    c.criteria("genres").equal("драма")
      //)
    );

    Set<Film> result = new LinkedHashSet<Film>(c.order("-rating").asList());
    HtmlRenderer renderer = new HtmlRenderer();
    System.out.printf("\n\n%s\n", renderer.renderHtml(result));
  }
}
