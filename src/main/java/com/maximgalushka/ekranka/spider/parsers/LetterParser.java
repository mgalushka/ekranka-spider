package com.maximgalushka.ekranka.spider.parsers;

import com.maximgalushka.ekranka.spider.PageParser;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 06.07.12
 */
public class LetterParser implements PageParser <List<String>> {

    @Override
    public List<String> parse(String input) throws ParserException {
        List<String> result = new ArrayList<String>();
        Node[] nodes = urlsList(input).toNodeArray();
        for(Node n : nodes){
            if(n instanceof LinkTag){
                String link = ((LinkTag) n).getLink();
                if(link.contains("/film/")){
                    result.add(link);
                }
            }
        }
        return result;
    }

    private NodeList urlsList(String htmlText) throws ParserException {
        return new Parser(htmlText).parse(new AndFilter(
                        new TagNameFilter("a"),
                        new HasParentFilter(new HasAttributeFilter("class", "td2params"), true)
        ));
    }
}
