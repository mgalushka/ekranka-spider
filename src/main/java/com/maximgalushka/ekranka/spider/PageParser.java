package com.maximgalushka.ekranka.spider;

import org.htmlparser.util.ParserException;


/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 06.07.12
 */
public interface PageParser <T> {

    T parse(String input) throws ParserException;
}
