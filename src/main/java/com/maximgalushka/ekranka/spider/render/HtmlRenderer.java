package com.maximgalushka.ekranka.spider.render;

import com.maximgalushka.ekranka.spider.domain.Film;

import java.util.Collection;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 25.07.12
 */
public class HtmlRenderer {

    public String renderHtml(Collection<Film> films){
        return renderContent(renderTable(films));
    }

    protected String renderContent(String content){
        return String.format(
                "<html><head><title>Ekranka best films</title></head><body>%s</body></html>", content);
    }

    protected String renderTable(Collection<Film> films){
        StringBuilder sb = new StringBuilder();
        for(Film f : films){
            sb.append(renderRow(f));
        }
        return String.format("<table border='1'>%s</table>", sb.toString());
    }

    protected String renderRow(Film film){
        return String.format("<tr>" +
                "<td><a href='%s'>%s</a></td>" +
                "<td>%s</td>" +
                "<td>%d</td>" +
                "<td>%f</td>" +
                "<td nowrap='true'>%d мин.</td>" +
                "<td>%s</td>" +
                "<td>%s</td>" +
                "</tr>",
                film.getUrl(),
                film.getTitle(),
                film.getDirector(),
                film.getYear(),
                film.getRating(),
                film.getDuration(),
                film.getCountries(),
                film.getGenres());
    }
}
