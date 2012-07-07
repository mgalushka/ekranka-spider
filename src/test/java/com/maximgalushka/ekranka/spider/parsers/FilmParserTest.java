package com.maximgalushka.ekranka.spider.parsers;

import com.maximgalushka.ekranka.spider.domain.Film;
import com.maximgalushka.ekranka.spider.http.HttpCallbackHandler;
import com.maximgalushka.ekranka.spider.http.HttpHelper;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.util.List;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 06.07.12
 */
public class FilmParserTest {

    private static final String EKRANKA_HOSTNAME = "www.ekranka.ru";

    @Test
    public void testParse() throws Exception {

        final FilmParser fp = new FilmParser();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpHelper<Film> h2 = new HttpHelper<Film>(httpclient);

        final String url = "/film/564/";

        System.out.printf("Parsing: [%s]\n", url);
        Film film = h2.get(url,
                new HttpHost("www.ekranka.ru"),
                new HttpCallbackHandler<Film>() {


                    @Override
                    public Film process(String content) throws Exception {
                        Film result = fp.parse(content);
                        result.setUrl(String.format("http://%s%s", EKRANKA_HOSTNAME, url));
                        return result;
                    }
                }, "windows-1251");

        System.out.printf("Film= %s\n", film);

    }

    // good:
    // /film/2290/

    // no rating:
    // /film/2141/

    // all
    // /film/564/
}
