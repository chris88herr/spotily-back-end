package com.chrisProjects.lyricsScraper.services;

import com.chrisProjects.lyricsScraper.Utils.Utls;
import com.chrisProjects.lyricsScraper.WebScraper;
import com.chrisProjects.lyricsScraper.dao.ArtistDaoImpl;
import com.chrisProjects.lyricsScraper.dao.SongDaoImpl;
import com.chrisProjects.lyricsScraper.models.Artist;
import com.chrisProjects.lyricsScraper.models.Song;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SongLyricsService {

    private SongDaoImpl songDao;
    private ArtistDaoImpl artistDao;

    public SongLyricsService() {
        songDao = new SongDaoImpl();
        artistDao = new ArtistDaoImpl();
    }

    public String getSongLyrics(String artistName, String songName){
        String lyrics = null;
        String artistNameFixed = artistName.trim().replace("-", " ");
        songName = songName.trim();
        int indexOfParenthesis = songName.indexOf("(");
        if(indexOfParenthesis>0){
            songName = songName.substring(0, indexOfParenthesis).trim();
        }
        int indexOfDash = songName.indexOf("-");
        if(indexOfDash>0){
            songName = songName.substring(0, indexOfDash).trim();
        }

        List<Song> songs = songDao.getSongByName(songName);
        Song songResult = null;
        if(songs != null) {
            for (Song song : songs) {
//                System.out.println(song + "\t" + song.getArtist().getArtistName() + "\t" + artistNameFixed);
                if (song.getArtist().getArtistName().toUpperCase().equals(artistNameFixed.toUpperCase())) {
                    songResult = song;
                }
            }
        }
        if(songResult!=null){
            lyrics = songResult.getSongLyrics();
            return lyrics;
        }
        else //database doesnt have the record, let the web scraper do its thing
        {
            WebScraper singleSongWebScraper = new WebScraper(artistName, songName);
            singleSongWebScraper.runScrapper();
            lyrics = singleSongWebScraper.getSingleSongLyrics();
        }
        return lyrics;
    }

    public void createLyricsRecord(String artistName, String songName, String fileName){
        Artist artist = artistDao.getArtistByName(artistName.replace("-", " "));
        if(artist==null){
            artist = new Artist(artistName.replace("-", " "));
            artistDao.createArtist(artist);
        }
        Song song = new Song(songName);
        song.setArtist(artist);
        song.setSongLyrics(fileName);
        songDao.createSong(song);
    }

}
