package com.chrisProjects.lyricsScraper.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

import org.hibernate.annotations.CascadeType;


@Entity(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private int songId;

    @Column(name = "song_name")
    String songName;

    @Column(name="posted")
    Date posted;

    @OneToOne
    @JoinColumn(name = "artist")
    @Cascade(CascadeType.ALL)
    Artist artist;

    @Column(name = "song_lyrics")
    String songLyrics;

    public Song() { }

    public Song(String songName, Date posted, String songLyrics) {
        this.songName = songName;
        this.posted = posted;
        this.songLyrics = songLyrics;
    }

    public Song(String songName) {
        this.songName = songName;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getSongLyrics() {
        return songLyrics;
    }

    public void setSongLyrics(String songLyrics) {
        this.songLyrics = songLyrics;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", songName='" + songName;
    }
}
