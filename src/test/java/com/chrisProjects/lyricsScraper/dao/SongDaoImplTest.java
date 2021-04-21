package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.models.Artist;
import com.chrisProjects.lyricsScraper.models.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongDaoImplTest {

    private SongDaoImpl dao;
    private String testSongName;
    @BeforeEach
    public void init(){
        dao = new SongDaoImpl();
        testSongName = "Jump";
    }

    @Test
    void getSongByNameTest(){
        List<Song> test = dao.getSongByName(testSongName);
        System.out.println(testSongName.toUpperCase());
        assertNotNull(test);
        for(Song song : test){
            System.out.println(song.getArtist().getArtistName());
        }
        assertEquals(testSongName,  test.get(0).getSongName());
    }

    @Test
    void createSongTest() throws ParseException {
        Song song = new Song();
        Artist artist = new ArtistDaoImpl().getArtistByName("Mark Anthony");
        song.setArtist(artist);
        song.setSongLyrics("te_conosco_bien.txt");
        song.setSongName(testSongName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
        song.setPosted(dateWithoutTime);
        dao.createSong(song);
        List<Song> songs = dao.getSongByName(testSongName);
        assertNotNull(songs);
        assertEquals(testSongName, songs.get(0).getSongName());

    }

    @Test
    void deleteSongTest (){
        Song song = new Song(testSongName);
        SongDaoImpl dao = new SongDaoImpl();
        dao.deleteSong(song);
        List<Song> songs = dao.getSongByName(testSongName);
        assertNull(songs);
    }

}