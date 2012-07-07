package com.maximgalushka.ekranka.spider.parsers;

import com.maximgalushka.ekranka.spider.PageParser;
import com.maximgalushka.ekranka.spider.domain.Film;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 07.07.12
 */
public class FilmParser implements PageParser<Film> {

    @Override
    public Film parse(String input) throws ParserException {

        Film film = new Film();

        String title = new Parser(input).parse(new TagNameFilter("h1")).toNodeArray()[0].getFirstChild().getText();
        film.setTitle(title);

        NodeList td = new Parser(input).parse(new AndFilter(
                new TagNameFilter("td"), new HasChildFilter(new StringFilter("Средняя оценка:"), true)));
        //new TagNameFilter("h1")).toNodeArray()[0].getText();

        Node[] nodes = td.toNodeArray();
        Node tag = nodes[3];
        ImageTag it = (ImageTag) ((TableColumn) tag).getChild(1).getFirstChild();
        String rateStr = it.getAttribute("title");
        Float rate = Float.parseFloat(rateStr.substring(rateStr.indexOf(":")+1));
        film.setRating(rate);

        LinkTag l = (LinkTag) ((TableColumn) tag).getChild(4);
        film.setYear(Integer.parseInt(l.getFirstChild().getText()));

//        LinkTag d = (LinkTag) ((TableColumn) tag).getChild(24);
//        film.setDirector(d.getFirstChild().getText());

        return film;
    }
}
