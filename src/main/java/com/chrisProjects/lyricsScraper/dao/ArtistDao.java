package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.models.Artist;
import com.chrisProjects.lyricsScraper.models.Genre;

import java.util.List;

public interface ArtistDao {
    public void createArtist(Artist artist);
    public List<Artist> getAllArtists();
    public Artist getArtistByName(String name);
    public Artist getArtistById(int id);
    public void updateArtist(Artist artist);
    public void deleteArtist(Artist  artist);
}
