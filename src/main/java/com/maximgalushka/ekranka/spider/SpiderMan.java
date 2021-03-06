package com.maximgalushka.ekranka.spider;

import com.google.code.morphia.Datastore;
import com.maximgalushka.ekranka.spider.domain.Film;
import com.maximgalushka.ekranka.spider.http.HttpCallbackHandler;
import com.maximgalushka.ekranka.spider.http.HttpHelper;
import com.maximgalushka.ekranka.spider.mongo.MongoConnectionHelper;
import com.maximgalushka.ekranka.spider.mongo.MongoSearch;
import com.maximgalushka.ekranka.spider.parsers.FilmParser;
import com.maximgalushka.ekranka.spider.parsers.LetterParser;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 06.07.12
 */
public class SpiderMan {

    public static final String EKRANKA_HOSTNAME = "www.ekranka.ru";

    private static List<String> fullAlphabet(){
        List<String> alphabet = new ArrayList<String>();
        for(char l = 'A'; l < 'Z' ;l++){
            alphabet.add(Character.toString(l));
        }
        for(char l = 'А'; l < 'Я' ;l++){
            alphabet.add(Character.toString(l));
        }
        for(int l = 0; l < 10 ;l++){
            alphabet.add(Integer.toString(l));
        }
        return alphabet;
    }

    public static void main(String[] args) throws Exception {

        //===============================================================
        // get all films for all letters
        final LetterParser lp = new LetterParser();

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpHelper<List<String>> h = new HttpHelper<List<String>>(httpclient);

        List<String> allFilms = new ArrayList<String>();

        for(String l : fullAlphabet()){
            String url = String.format("/films/%s/", l);
            System.out.printf("%s\n", url);
            List<String> result = h.get(url,
                    new HttpHost(EKRANKA_HOSTNAME),
                    new HttpCallbackHandler<List<String>>() {
                @Override
                public List<String> process(String content) throws Exception {
                    return lp.parse(content);
                }
            }, "windows-1251");
            allFilms.addAll(result);
            System.out.printf("%s\n", result);
        }

        System.out.printf("Count= %d\n", allFilms.size());

        final FilmParser fp = new FilmParser();

        //===============================================================
        // for each film URL - retrieve film details and store it to mongodb
        MongoConnectionHelper mch = MongoConnectionHelper.getInstance();

        HttpHelper<Film> h2 = new HttpHelper<Film>(httpclient);
        for(final String url : allFilms){
            System.out.printf("Parsing: [%s]\n", url);
            Film film = h2.get(url,
                    new HttpHost(EKRANKA_HOSTNAME),
                    new HttpCallbackHandler<Film>() {
                        @Override
                        public Film process(String content) throws Exception {
                            Film result = fp.parse(content);
                            if(result == null) return null;

                            result.setUrl(url);
                            return result;
                        }
                    }, "windows-1251");

            // storing remotely
            Datastore connection = mch.getConnection();
            MongoSearch search = new MongoSearch();
            if(film != null){
                if(!search.existed(film)){
                    System.out.printf("Saving film= %s\n", film);
                    connection.save(film);
                }
                System.out.printf("Film existed = %s\n", film);
            }
        }

    }
}
