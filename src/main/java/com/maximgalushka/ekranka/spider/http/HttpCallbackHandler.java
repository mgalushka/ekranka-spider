package com.maximgalushka.ekranka.spider.http;

/**
 * <p>Used to process HTML page - usually for parsing desired results to domain objects</p>
 *
 * @author Maxim Galushka
 * @since 07/09/2011
 */
public interface HttpCallbackHandler <T> {

    public T process(String content) throws Exception;
}
