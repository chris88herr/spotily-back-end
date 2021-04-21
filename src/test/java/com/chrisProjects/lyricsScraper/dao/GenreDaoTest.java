package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.models.Genre;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreDaoTest {

    @Test
    void getGenreTest(){
        Genre test=null;
        GenreDaoIml dao = new GenreDaoIml();
        test = dao.getGenreByName("Salsa");
        assertEquals("Salsa", test.getGenreName());
    }

    @Test
    void getAllGenresTest(){
        GenreDaoIml genreDao = new GenreDaoIml();
        List<Genre> genres = genreDao.getAllGenres();
        assertNotNull(genres);
    }

    @Test
    void createGenreTest(){
        GenreDaoIml dao = new GenreDaoIml();
        Genre testGenre = new Genre("Pop2");
        dao.createGenre(testGenre);
        Genre testResult = dao.getGenreByName("Pop2");
        assertNotNull(testResult);
        assertEquals("Pop2", testResult.getGenreName());
    }

    @Test
    void updateGenreTest(){
        GenreDaoIml dao = new GenreDaoIml();
        dao.updateGenre(new Genre(14,"Pop"));
        Genre gemre = dao.getGenreByName("Pop");
        assertEquals("Pop", gemre.getGenreName());
    }

    @Test
    void getGenreByIdTest(){
        Genre pop = new GenreDaoIml().getGenreById(14);
        assertNotNull(pop);
        assertEquals("Pop", pop.getGenreName());
    }

}