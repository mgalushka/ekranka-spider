package com.maximgalushka.ekranka.spider.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>Utility which executes HTTP requests
 * and delegate retrieved content processing to some callback handler</p>
 *
 * @author Maxim Galushka
 * @since 07/09/2011
 */
public class HttpHelper <T>{

    private static final Logger log = Logger.getLogger(HttpHelper.class);

    private HttpClient httpClient;

    public HttpHelper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public T get(String url, HttpHost host, HttpCallbackHandler<T> callback, String encoding)
            throws Exception {

        log.debug(String.format("Prepare to execute get request: [%s]", url));
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(host, get);
        HttpEntity entity = response.getEntity();

        String content = IOUtils.convertStreamToString(entity.getContent(), encoding);
        log.trace(String.format("Retrieved content: %s", content));

        return callback.process(content);
    }

    public T get(String url, HttpHost host, HttpCallbackHandler<T> callback) throws Exception {
        return get(url, host, callback, "utf-8");
    }

    public T download(String url, HttpHost host, File to, HttpCallbackHandler<T> callback)
            throws Exception {

        log.debug(String.format("Prepare to download file: [%s]", url));
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(host, get);
        HttpEntity entity = response.getEntity();

        log.debug(String.format("Downloading file: [%s] to: [%s]", url, to.getPath()));

        //Create file
        OutputStream os = new FileOutputStream(to);
        InputStream is = entity.getContent();
        byte[] buf = new byte[4096];
        int read;
        while ((read = is.read(buf)) != -1) {
            os.write(buf, 0, read);
        }

        os.close();

        return callback.process(to.getPath());
    }
}
