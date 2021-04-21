package com.chrisProjects.lyricsScraper.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class SongLyricsServiceTest {
    private static SongLyricsService service;

    @BeforeAll
    public static void init(){
        System.setProperty("org.jboss.logging.provider", "");
        service = new SongLyricsService();
    }

    @Test
    void getLyricsTest(){
        String artist =  "drake";
        String song = "you got me";
        String lyrics = service.getSongLyrics(artist, song);
        assertNotNull(lyrics);

    }

    @Test
    void createSongTest(){
        String artist = "Bad Bunny";
        String songName = "Yo Perreo Sola";
        String fileName = "yo_perreo_sola.txt";
        service.createLyricsRecord(artist, songName,fileName);
    }
}