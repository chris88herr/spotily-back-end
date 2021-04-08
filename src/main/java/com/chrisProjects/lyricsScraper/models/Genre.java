package com.chrisProjects.lyricsScraper.models;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private int genreId;

    @Column(name = "genre_name")
    private String genreName;

    public Genre() { }

    public Genre(String genreName) {
        this.genreName = genreName;
    }
    public Genre(int id, String name){
        this.genreId = id;
        this.genreName = name;
    }


    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString(){
        return "Genre: " + this.getGenreName();
    }
}
