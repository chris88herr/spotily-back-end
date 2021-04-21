package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.models.Song;

import java.util.List;

public interface SongDao {
    public void createSong(Song song);
    public List<Song> getSongByName(String name);
    public void deleteSong(Song  song);
}
