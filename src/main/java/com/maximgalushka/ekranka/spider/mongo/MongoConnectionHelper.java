package com.maximgalushka.ekranka.spider.mongo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import java.net.UnknownHostException;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 07.07.12
 */
public class MongoConnectionHelper {

    private static final MongoConnectionHelper helper = new MongoConnectionHelper();

    private Datastore cachedDatastore;

    private MongoConnectionHelper(){}

    public static MongoConnectionHelper getInstance(){
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
        if(cachedDatastore == null){
            Mongo m = new Mongo("ds031597.mongolab.com", 31597);
            this.cachedDatastore = new Morphia().createDatastore(m, "ekranka", "ekranka", "ekranka".toCharArray());
        }
        return this.cachedDatastore;
    }


}
