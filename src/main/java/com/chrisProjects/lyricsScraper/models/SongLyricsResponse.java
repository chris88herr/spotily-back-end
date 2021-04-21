package com.chrisProjects.lyricsScraper.models;

public class SongLyricsResponse {
    private String artist;
    private String song;
    private String lyrics;

    public SongLyricsResponse(String artist, String song, String lyrics) {
        this.artist = artist;
        this.song = song;
        this.lyrics = lyrics;
    }


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
