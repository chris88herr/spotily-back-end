package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.Utils.HibernateUtil;
import com.chrisProjects.lyricsScraper.models.Song;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SongDaoImpl implements SongDao {
    @Override
    public void createSong(Song song) {
        if( song==null
                || song.getArtist()==null
                || song.getSongLyrics()==null )
            return;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.save(song);
        }catch(Exception e){
            System.out.println(e.getCause());
        }
    }

    @Override
    public List<Song> getSongByName(String name) {
        if (name==null | name.length()<1) return null;
        List<Song> result = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "From songs s WHERE upper(s.songName) = :song_name";
            Query query = session.createQuery(hql);
            query.setParameter("song_name", name.toUpperCase());
            result=query.getResultList();
        }catch(Exception e){
            System.out.println(e.getCause());
        }
        finally {
            return result;
        }

    }

    @Override
    public void deleteSong(Song song) {
        if(song==null){
            return;
        }
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            String hql = "Delete FROM songs s where upper(s.songName) = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", song.getSongName().toUpperCase());
            query.executeUpdate();
            tx.commit();
        }catch(Exception e){
            System.out.println(e.getCause());
        }
    }
}
