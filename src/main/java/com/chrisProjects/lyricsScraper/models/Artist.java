package com.chrisProjects.lyricsScraper.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

@Entity(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private int artistId;

    @Column(name = "artist_name")
    String artistName;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "genre")
    Genre genre;

    public Artist() {    }

    public Artist(String artistName, Genre genre) {
        this.artistName = artistName;
        this.genre = genre;
    }

    public Artist( String artistName) {

        this.artistName = artistName;
    }

    public Artist(int artistId, String artistName, Genre genre) {
        this.artistId = artistId;
        this.genre = genre;
        this.artistName = artistName;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
