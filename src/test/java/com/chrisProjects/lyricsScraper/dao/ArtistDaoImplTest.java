package com.chrisProjects.lyricsScraper.dao;

import antlr.collections.AST;
import com.chrisProjects.lyricsScraper.models.Artist;
import com.chrisProjects.lyricsScraper.models.Genre;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistDaoImplTest {

    @Test
    void createArtistTest(){
        String artistName = "The beattles";
        Artist artistTest = new Artist(artistName);
        ArtistDao dao = new ArtistDaoImpl();
        dao.createArtist(artistTest);
        Artist result = dao.getArtistByName(artistName);
        assertNotNull(result);
        assertEquals(artistName, result.getArtistName());
    }

    @Test
    void getAllArtistsTest(){
        List<Artist> artists = new ArtistDaoImpl().getAllArtists();
        assertNotNull(artists);
    }

    @Test
    void getArtistByNameTest(){
        String artistName = "Beyonce";
        Artist result = new ArtistDaoImpl().getArtistByName(artistName);
        assertEquals(artistName, result.getArtistName());
    }

    @Test
    void getArtistByIdTest(){
        Artist result = new ArtistDaoImpl().getArtistById(20);
        assertNotNull(result);
        assertEquals("Justin Beiber", result.getArtistName());
    }

    @Test
    void updateArtistTest(){
        ArtistDaoImpl dao = new ArtistDaoImpl();
        Artist update = dao.getArtistById(20);
        update.setArtistName("Justin Beiver");
        dao.updateArtist(update);
        Artist testResult = dao.getArtistByName("Justin Beiver");
        assertNotNull(testResult);
        assertEquals("Justin Beiver", testResult.getArtistName());
    }


    @Test
    void deleteArtistTest(){
        ArtistDaoImpl dao = new ArtistDaoImpl();
        dao.deleteArtist(new Artist("Justin Beiber"));
        Artist result = dao.getArtistByName("Justin Beiber");
       // System.out.println(result.getArtistName());
        assertNull(result);

    }




}