package com.chrisProjects.lyricsScraper.dao;

import com.chrisProjects.lyricsScraper.Utils.HibernateUtil;
import com.chrisProjects.lyricsScraper.models.Genre;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoIml implements GenreDao{

    @Override
    public void  createGenre(Genre genre) {
        if(genre==null){
            return ;
        }
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.save(genre);
        }
        catch (Exception e){
            System.out.println(e.getCause());
        }

           }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "From Genre";
            Query query = session.createQuery(hql);
            genres.addAll(query.list());
        } catch(Exception e ){
            System.out.println(e.getCause());
        }

        return genres;
    }

    @Override
    public Genre getGenreByName(String name) {
        Genre result=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Genre g WHERE  g.genreName = :name ";
            Query query = session.createQuery(hql);
            query.setParameter("name" ,name);
            result = (Genre)query.getSingleResult();

        }catch(Exception e){
            System.out.println(e.getCause());
        }
        finally {
            return result;
        }

    }

    @Override
    public Genre getGenreById(int id) {
        Genre result=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Genre g WHERE  g.genreId = :id ";
            Query query = session.createQuery(hql);
            query.setParameter("id" ,id);
            result = (Genre)query.getSingleResult();

        }catch(Exception e){
            System.out.println(e.getCause());
        }
        finally {
            return result;
        }
    }

    @Override
    public void updateGenre(Genre genre) {
        if(genre==null || genre.getGenreId()==0){
            return;
        }
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(genre);
            transaction.commit();
        }catch (Exception e){
            System.out.println("--"+e.getCause());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGenre(Genre genre) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.delete(genre);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
