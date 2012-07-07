package com.maximgalushka.ekranka.spider.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The basic domain model for any ekranka film details</p>
 *
 * It is planned to make this also MongoDb object.
 *
 * @author Maxim Galushka
 * @since 06.07.12
 */
public class Film {

    private String title;
    private String director;
    private Integer year;

    private Float rating;

    // minutes
    private Integer duration;

    private List<String> countries = new ArrayList<String>();
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
                "year=%d, rating=%f, duration=%d, countries=%s, genres=%s]",
                title, director, year, rating, duration, countries, genres);
    }
}
