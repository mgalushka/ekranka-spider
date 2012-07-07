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

import java.util.ArrayList;
import java.util.List;

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

        String title = parseTitle(input);
        if(title == null) return  null;

        film.setTitle(title);

        NodeList td = new Parser(input).parse(new AndFilter(
                new TagNameFilter("td"),
                new HasChildFilter(new StringFilter("Средняя оценка:"), true)
        ));
        if(td != null){
            try {
                Node[] nodes = td.toNodeArray();
                Node tag = nodes[3];
                film.setDirector(parseDirector(tag));
                film.setRating(parseRating(tag));
                film.setYear(parseYear(tag));
                film.setDuration(parseDuration(tag));
                film.setCountries(parseCountries(tag));
                film.setGenres(parseGenres(tag));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.printf("================= ERROR PARSING TAG ==================");
            }
        }
        else{
            System.out.printf("================= ERROR NULL ==================");
        }

        return film;
    }

    private List<String> parseCountries(Node tag) {
        List<String> result = new ArrayList<String>();
        try {
            NodeList list = tag.getChildren().extractAllNodesThatMatch(new LinkStringFilter("country"));
            Node[] links = list.toNodeArray();
            for(Node l : links){
                try {
                    result.add(l.getFirstChild().getText());
                } catch (Exception e) {
                    System.out.printf("COUNTRIES ITERATION FAIL!");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<String> parseGenres(Node tag) {
        List<String> result = new ArrayList<String>();
        try {
            NodeList list = tag.getChildren().extractAllNodesThatMatch(new LinkStringFilter("genre"));
            Node[] links = list.toNodeArray();
            for(Node l : links){
                try {
                    result.add(l.getFirstChild().getText());
                } catch (Exception e) {
                    System.out.printf("GENRES ITERATION FAIL!");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String parseTitle(String input){
        try {
            return new Parser(input).parse(new TagNameFilter("h1")).toNodeArray()[0].getFirstChild().getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseDirector(Node tag){
        try {
            NodeList list = tag.getChildren().extractAllNodesThatMatch(new StringFilter("режиссер"));
            String text = list.toNodeArray()[0].getText();
            text = text.trim();
            text = text.substring(text.indexOf(":")+1).trim().replaceAll("\\.", "").trim();
            if("".equals(text)){
                // just next link node
                NodeList list2 = tag.getChildren().extractAllNodesThatMatch(new LinkStringFilter("director"));
                LinkTag l = (LinkTag) list2.toNodeArray()[0];
                return l.getFirstChild().getText();
            }
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer parseYear(Node tag){
        try {
            NodeList list = tag.getChildren().extractAllNodesThatMatch(new LinkStringFilter("year"));
            LinkTag l = (LinkTag) list.toNodeArray()[0];
            return Integer.parseInt(l.getFirstChild().getText());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer parseDuration(Node tag){
        try {
            NodeList list = tag.getChildren().extractAllNodesThatMatch(new StringFilter("мин"));
            String text = list.toNodeArray()[0].getText();
            text = text.substring(0, text.indexOf("м")).replaceAll("\\.", "").trim();
            text = text.trim();
            return Integer.parseInt(text);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Float parseRating(Node tag){
        try {
            ImageTag it = (ImageTag) ((TableColumn) tag).getChild(1).getFirstChild();
            if(it == null){
                String first = tag.getFirstChild().getText();
                if(first != null && first.contains("без оценки")){
                    System.out.printf("NO RATING SO FAR!\n");
                    return null;
                }
            }
            String rateStr = it.getAttribute("title");
            return Float.parseFloat(rateStr.substring(rateStr.indexOf(":")+1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
