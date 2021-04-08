package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.models.Genre;

import java.util.List;

public interface GenreDao {
    public List<Genre> getAllGenres();
    public Genre getGenreByName(String name);
    public Genre getGenreById(int id);
    public void updateGenre(Genre genre);
    public void deleteGenre(Genre id);

}
