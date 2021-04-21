package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.Utils.HibernateUtil;
import com.chrisProjects.lyricsScraper.models.Artist;
import com.chrisProjects.lyricsScraper.models.Genre;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ArtistDaoImpl implements ArtistDao {
    @Override
    public void createArtist(Artist artist) {
        if(artist==null){
            return ;
        }
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.save(artist);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return;
        }

    }

    @Override
    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "From Artist";
            Query query = session.createQuery(hql);
            artists.addAll(query.list());
        } catch(Exception e ){
            System.out.println(e.getCause());
        }

        return artists;
    }

    @Override
    public Artist getArtistByName(String name) {
        Artist result=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM artists a WHERE  a.artistName = :name ";
            Query query = session.createQuery(hql);
            query.setParameter("name" ,name);
            result = (Artist)query.getSingleResult();

        }catch(Exception e){
            System.out.println(e.getCause());
        }
        finally {
            return result;
        }
    }

    @Override
    public Artist getArtistById(int id) {
        Artist result=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM artists a WHERE  a.artistId = :id ";
            Query query = session.createQuery(hql);
            query.setParameter("id" ,id);
            result = (Artist)query.getSingleResult();

        }catch(Exception e){
            System.out.println(e.getCause());
        }
        finally {
            return result;
        }
    }

    @Override
    public void updateArtist(Artist artist) {
        if(artist==null || artist.getArtistId()==0){
            return;
        }
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(artist);
            transaction.commit();
        }catch (Exception e){
            System.out.println("--"+e.getCause());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteArtist(Artist artist) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            String hql = "Delete artists where artistName = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", artist.getArtistName());
            query.executeUpdate();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
