package com.maximgalushka.ekranka.spider.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The basic domain model for any ekranka.ru film details</p>
 *
 * @author Maxim Galushka
 * @since 06.07.12
 */
@Entity(value = "films", noClassnameStored = true)
public final class Film implements Serializable, Comparable <Film> {

    private static final long serialVersionUID = 7276782241886775371L;

    @Id
    private ObjectId id;

    private String title;
    private String director;
    private Integer year;

    private Float rating;

    private String url;

    // minutes
    private Integer duration;

    @Property
    private List<String> countries = new ArrayList<String>();

    @Property
    private List<String> genres = new ArrayList<String>();

    public Film() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (countries != null ? !countries.equals(film.countries) : film.countries != null) return false;
        if (director != null ? !director.equals(film.director) : film.director != null) return false;
        if (duration != null ? !duration.equals(film.duration) : film.duration != null) return false;
        if (genres != null ? !genres.equals(film.genres) : film.genres != null) return false;
        if (rating != null ? !rating.equals(film.rating) : film.rating != null) return false;
        if (title != null ? !title.equals(film.title) : film.title != null) return false;
        if (year != null ? !year.equals(film.year) : film.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (countries != null ? countries.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Film [title='%s', director='%s', " +
                "year=%d, rating=%f, duration=%d, countries=%s, genres=%s, url=%s]\n",
                title, director, year, rating, duration, countries, genres, url);
    }

    @Override
    public int compareTo(Film o) {
        if(this.title == null) return -1;
        if(o == null || o.title == null) return 1;
        return this.title.compareTo(o.title);

//        if(this.rating == null) return -1;
//        if(o == null || o.rating == null) return 1;
//        return this.rating.compareTo(o.rating);
    }
}
