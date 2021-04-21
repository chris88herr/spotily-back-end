package com.chrisProjects.lyricsScraper.models;

public class SongLyricsRequest {
    private String artist;
    private String songName;

    public SongLyricsRequest() {    }

    public SongLyricsRequest(String artist, String songName) {
        this.artist = artist;
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
