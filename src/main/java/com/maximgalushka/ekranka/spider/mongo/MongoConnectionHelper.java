package com.maximgalushka.ekranka.spider.mongo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.mongodb.Mongo;

import java.net.UnknownHostException;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 07.07.12
 */
public final class MongoConnectionHelper {

  static {
    MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
  }

  private static final MongoConnectionHelper helper =
    new MongoConnectionHelper();

  private Datastore cachedDatastore;

  private MongoConnectionHelper() {
  }

  public static MongoConnectionHelper getInstance() {
    return helper;
  }

  /**
   * This connection will never gonna change - so
   * anyone who will ever read these lines and want to contribute -
   * just go ahead and shot - go directly to hosting and change
   * the db schema whatever you like!
   *
   * @return Datastore connected to Mongo
   * @throws UnknownHostException if error actually shouldn't happen
   */
  public synchronized Datastore getConnection() throws UnknownHostException {
    if (cachedDatastore == null) {
      Mongo m = new Mongo("ds037262.mongolab.com", 37262);
      this.cachedDatastore = new Morphia().createDatastore(
        m,
        "ekranka_aws",
        "ekranka",
        "ekranka".toCharArray()
      );
    }
    return this.cachedDatastore;
  }


}
